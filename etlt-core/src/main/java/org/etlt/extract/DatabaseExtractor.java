package org.etlt.extract;

import org.etlt.EtltRuntimeException;
import org.etlt.job.JobContext;

import javax.sql.DataSource;
import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseExtractor extends Extractor {
    final DatabaseExtractSetting setting;

    private DataSource dataSource;

    private Connection connection;

    private PreparedStatement statement;

    private ResultSet resultSet;

    public DatabaseExtractor(DatabaseExtractSetting setting) {
        this.setting = setting;
        this.setName(setting.getName());
    }

    @Override
    public void init(JobContext context) {
        try {
            if (this.setting.getDatasource() != null) {
                this.dataSource = context.getResource(this.setting.getDatasource());
                this.connection = this.dataSource.getConnection();
                this.statement = this.connection.prepareStatement(this.setting.getDql());
                resultSet = this.statement.executeQuery();
                ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
                int columnCount = resultSetMetaData.getColumnCount();
                if (this.setting.isAutoResolve() && getColumns().size() == 0) {
                    for (int i = 0; i < columnCount; i++) {
                        getColumns().add(resultSetMetaData.getColumnLabel(i + 1));
                    }
                }
            }
        } catch (SQLException e) {
            throw new EtltRuntimeException("Extractor init error: " + getName(), e);
        }
    }


    @Override
    public void extract(JobContext context) {
        try {
            if (this.connection == null || this.connection.isClosed()) {
                this.connection = this.dataSource.getConnection();
                this.statement = this.connection.prepareStatement(this.setting.getDql());
                resultSet = this.statement.executeQuery();
            }
            synchronized (this) {
                if (this.resultSet.next()) {
                    if (this.skip < this.setting.getSkip()) {
                        this.skip++;
                        this.index++;
                        extract(context);
                    } else {
                        Map<String, Object> rowData = new HashMap<>();
                        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
                        for (int i = 0; i < getColumns().size(); i++) {
                            if (getColumns().contains(resultSetMetaData.getColumnLabel(i + 1)))
                                rowData.put(getColumns().get(i),
                                        DatabaseUtil.getObject(resultSet, i + 1,
                                                resultSetMetaData.getColumnType(i + 1))
                                );
                        }
                        Entity entity = new Entity(index++, rowData);
                        context.setEntity(this.setting.getName(), entity);
                    }
                } else {
                    context.removeEntity(this.setting.getName());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public List<String> getColumns() {
        return this.setting.getColumns();
    }

    @Override
    public void doFinish() {
        close(resultSet, statement, connection);
    }

}
