/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by
 * applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 */
package com.github.drinkjava2.jdialects;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.github.drinkjava2.jdialects.model.ColumnModel;
import com.github.drinkjava2.jdialects.model.FKeyModel;
import com.github.drinkjava2.jdialects.model.IndexModel;
import com.github.drinkjava2.jdialects.model.TableModel;

/**
 * The tool to convert database structure(meta data) to TableModels
 * 
 * @author Yong Zhu
 * @since 1.0.6
 */
public abstract class TableModelUtilsOfDb {
	private static final String TABLE_NAME = "TABLE_NAME";

	/**
	 * get schema of database instance
	 * @param dialect
	 * @param metaData
	 * @return
	 * @throws SQLException
	 */
	private static String getSchema(Dialect dialect,DatabaseMetaData metaData ) throws SQLException {
		return
		dialect.isOracleFamily() || dialect.isDB2Family()
				? metaData.getUserName() : null;
	}
	/**
	 * Convert JDBC connected database structure to TableModels, note: <br/>
	 * 1)This method does not close connection <br/>
	 * 2)This method does not support sequence, foreign keys, primary keys..., but
	 * will improve later.
	 */
	public static TableModel[] db2Models(Connection con, Dialect dialect) {// NOSONAR
		List<TableModel> tableModels = new ArrayList<TableModel>();
		SQLException sqlException = null;
		ResultSet rs = null;
		PreparedStatement pst = null;

		try {
			DatabaseMetaData meta = con.getMetaData();
			String catalog = con.getCatalog();
			// get Tables
			rs = meta.getTables(catalog,
					getSchema(dialect, meta),
					null,
					new String[] { "TABLE" });
			while (rs.next()) {
				String tableName = rs.getString(TABLE_NAME);
				ResultSet indexRs = null;
				if (!StrUtils.isEmpty(tableName)) {
					// get indexes
					indexRs = meta.getIndexInfo(catalog,
							con.getSchema(),
							tableName,false, false
							);
					TableModel model = new TableModel(tableName);
					tableModels.add(model);
					IndexCollection indexCollection = new IndexCollection();
					while(indexRs.next()){
						String cname = indexRs.getString("COLUMN_NAME");
						String iname = indexRs.getString("INDEX_NAME");
						short position = indexRs.getShort("ORDINAL_POSITION");
						boolean unique = !indexRs.getBoolean("NON_UNIQUE");
						IndexDesc indexDesc = new IndexDesc(iname, position, cname, unique);
						indexCollection.addIndexDesc(indexDesc);
					}
					indexCollection.generateIndexes();
					indexCollection.indexModels.forEach((indexModel -> model.addIndex(indexModel)));
					indexRs.close();
					String comment = rs.getString("REMARKS");
					if (!StrUtils.isEmpty(comment))
						model.setComment(comment);
				}
			}

			rs.close();

			// Build Columns
			for (TableModel model : tableModels) {
				String tableName = model.getTableName();
				rs = meta.getColumns(null, null, tableName, null);
				while (rs.next()) {// NOSONAR
					String colName = rs.getString("COLUMN_NAME");
					ColumnModel col = new ColumnModel(colName);
					model.addColumn(col);

					int javaSqlType = rs.getInt("DATA_TYPE");
					try {
						col.setColumnType(TypeUtils.javaSqlTypeToDialectType(javaSqlType));
					} catch (Exception e1) {
						throw new DialectException("jDialect does not supported java.sql.types value " + javaSqlType + " for table " + tableName + ", column " + colName,
								e1);
					}
					col.setLength(rs.getInt("COLUMN_SIZE"));
					col.setNullable(rs.getInt("NULLABLE") > 0);
					col.setPrecision(rs.getInt("DECIMAL_DIGITS"));
					col.setLengths(new Integer[] { col.getLength(), col.getPrecision(), col.getPrecision() });

					try {
						if (((Boolean) (true)).equals(rs.getBoolean("IS_AUTOINCREMENT")))
							col.identityId();
					} catch (Exception e) {
					}

					try {
						if ("YES".equalsIgnoreCase(rs.getString("IS_AUTOINCREMENT")))
							col.identityId();
					} catch (Exception e) {
					}
				}
				rs.close();
			}

			// Get Primary Keys for each model
			for (TableModel model : tableModels) {
				rs = meta.getPrimaryKeys(catalog, null, model.getTableName());
				while (rs.next())
					model.getColumnByColName(rs.getString("COLUMN_NAME")).setPkey(true);
				rs.close();
			}

			// Get Foreign Keys for each model
			for (TableModel model : tableModels) {
				ResultSet foreignKeyResultSet = meta.getImportedKeys(catalog, null, model.getTableName());
				while (foreignKeyResultSet.next()) {
					String fkname = foreignKeyResultSet.getString("FK_NAME");
					int keyseq = foreignKeyResultSet.getInt("KEY_SEQ");
					String fkColumnName = foreignKeyResultSet.getString("FKCOLUMN_NAME");
					String pkTablenName = foreignKeyResultSet.getString("PKTABLE_NAME");
					String pkColumnName = foreignKeyResultSet.getString("PKCOLUMN_NAME");
					FKeyModel fkeyModel = model.getFkey(fkname);

					if (keyseq == 1) {
						model.fkey(fkname).columns(fkColumnName).refs(pkTablenName, pkColumnName);
					} else {
						fkeyModel.getColumnNames().add(fkColumnName);
						String[] newRefs = ArrayUtils.appendStrArray(fkeyModel.getRefTableAndColumns(), pkColumnName);
						fkeyModel.setRefTableAndColumns(newRefs);
					}
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
			sqlException = e;
		} finally {
			if (pst != null)
				try {
					pst.close();
				} catch (SQLException e1) {
					if (sqlException != null)
						sqlException.setNextException(e1);
					else
						sqlException = e1;
				}
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException e2) {
				if (sqlException != null)
					sqlException.setNextException(e2);
				else
					sqlException = e2;
			}
		}
		if (sqlException != null)
			throw new DialectException(sqlException);
		return tableModels.toArray(new TableModel[tableModels.size()]);
	}

	static class IndexCollection{
		List<IndexDesc> indexDescs = new ArrayList<>();

		List<IndexModel> indexModels = new ArrayList<>();

		void addIndexDesc(IndexDesc indexDesc){
			this.indexDescs.add(indexDesc);
		}

		void generateIndexes(){
			indexDescs.forEach((indexDesc -> {
				List<IndexModel> results =
					indexModels.stream().filter((indexModel ->
						indexModel.getName().equals(indexDesc.indexName))).collect(Collectors.toList());
				Optional<IndexModel> result = results.stream().findFirst();
				if(result.isPresent()){
					result.get().addColumn(indexDesc.column, indexDesc.position);
				}else if(indexDesc.indexName != null){
					IndexModel indexModel = new IndexModel(indexDesc.indexName);
					indexModel.addColumn(indexDesc.column, indexDesc.position);
					indexModel.setUnique(indexDesc.unique);
					this.indexModels.add(indexModel);
				}
			}));
		}
	}

	static class IndexDesc{
		String indexName;

		short position;

		String column;

		boolean unique;

		IndexDesc(String indexName, short position, String column, boolean unique){
			this.indexName = indexName;
			this.position = position;
			this.column = column;
			this.unique = unique;
		}
	}

}
