//package org.etlt.tool.db;
//
//import com.csii.ecif.tool.utils.StringUtils;
//import com.github.drinkjava2.jdialects.Type;
//import com.github.drinkjava2.jdialects.model.ColumnModel;
//import com.github.drinkjava2.jdialects.model.TableModel;
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.xssf.usermodel.XSSFRow;
//import org.apache.poi.xssf.usermodel.XSSFSheet;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.*;
//
//public class ExcelParser {
//
//	public static final String LABLE_NAME = "NAME";
//	public static final String LABLE_TYPE = "TYPE";
//	public static final String LABLE_COMMENT = "COMMENT";
//	public static final String LABLE_DEFAULTVALUE = "DEFAULTVALUE";
//	public static final String LABLE_ISNULL = "ISNULL";
//	public static final String LABLE_ISPK = "ISPK";
//
//	public static final String TYPE_DECIMAL = "DECIMAL";
//	public static final String TYPE_NUMERIC = "NUMERIC";
//	public static final String TYPE_VARCHAR = "VARCHAR";
//	public static final String TYPE_CHAR = "CHAR";
//
//	public static final String LABEL_INDEX = "INDEX";
//	public static final String LABEL_UNIQUE_INDEX = "UNIQUE_INDEX";
//
//	public List<TableModel> parse(InputStream source) throws IOException {
//		XSSFWorkbook book = null;
//		try {
////			book = new HSSFWorkbook(new POIFSFileSystem(source));
//			book = new XSSFWorkbook(source);
//			List<TableModel> models = new ArrayList<TableModel>();
//
//			int count = book.getNumberOfSheets();
//			for (int i = 0; i < count; i++) {
//				XSSFSheet sheet = book.getSheetAt(i);
//				// get first row
//				List<String> labels = getLables(sheet.getRow(0));
//
//				TableModel model = new TableModel(sheet.getSheetName());
//				int j = 1;
//				for (;; j++) {
//					XSSFRow row = sheet.getRow(j);
//					if (row == null) {
//						break;
//					}
//					Map<String, String> values = getValues(row, labels);
//					ColumnModel column = new ColumnModel(values.get(LABLE_NAME));
//					parseType(column, values.get(LABLE_TYPE));
//					column.setPkey("Y".equalsIgnoreCase(values.get(LABLE_ISPK)));
//					column.setDefaultValue(values.get(LABLE_DEFAULTVALUE));
//					column.setComment(values.get(LABLE_COMMENT));
//					model.addColumn(column);
//				}
//				int rowIndex = createIndex(model, sheet, j);
//				createIndex(model, sheet, rowIndex);
//				models.add(model);
//			}
//			return models;
//
//		} finally {
//			if (book != null)
//				book.close();
//		}
//	}
//
//	protected List<String> getLables(XSSFRow row) {
//		Iterator<Cell> cellIterator = row.iterator();
//		List<String> names = new ArrayList<String>();
//		for (; cellIterator.hasNext();) {
//			Cell cell = cellIterator.next();
//			names.add(cell.getStringCellValue());
//		}
//		return names;
//	}
//
//	protected Map<String, String> getValues(XSSFRow row, List<String> labels) {
//		Map<String, String> values = new HashMap<String, String>();
//		Iterator<Cell> _cellIterator = row.iterator();
//		for (; _cellIterator.hasNext();) {
//			Cell cell = _cellIterator.next();
//			int index = cell.getColumnIndex();
//			String value = cell.getStringCellValue().trim();
//			if (!StringUtils.isBlank(value))
//				values.put(labels.get(index), value);
//		}
//		return values;
//	}
//
//	protected int createIndex(TableModel table, XSSFSheet sheet, int index) {
//		int currentIndex = index + 1;
//		XSSFRow firstRow = sheet.getRow(currentIndex++);
//		if (firstRow != null) {
//			Cell cell = firstRow.getCell(0);
//			String indexType = cell.getStringCellValue();
//
//			for (;; currentIndex++) {
//				XSSFRow row = sheet.getRow(currentIndex);
//				if (row == null) {
//					break;
//				}
//
//				String name = row.getCell(0).getStringCellValue().trim();
//				List<String> names = new ArrayList<String>();
//				for (int i = 1;; i++) {
//					Cell ncell = row.getCell(i);
//					if (ncell == null)
//						break;
//					names.add(ncell.getStringCellValue().trim());
//				}
//				if (indexType.equals(LABEL_INDEX))
//					table.index(name).columns(names.toArray(new String[] {}));
//				else if (indexType.equals(LABEL_UNIQUE_INDEX))
//					table.unique(name).columns(names.toArray(new String[] {}));
//			}
//		}
//		return currentIndex;
//	}
//
//	protected void createUniqueIndex(TableModel table, XSSFRow row) {
//
//	}
//
//	private void parseType(ColumnModel column, String typeDesc) {
//		int lindex = typeDesc.indexOf('(');
//		if (lindex == -1) {
//			column.setColumnType(Type.valueOf(typeDesc.trim().toUpperCase()));
////			System.out.println(column.getColumnName());
//		} else {
//			int rindex = typeDesc.indexOf(')');
//			if (rindex == -1 || rindex < lindex) {
//				throw new IllegalArgumentException("illegal type desc: " + typeDesc);
//			}
//			String tname = typeDesc.substring(0, lindex).toUpperCase();
//			column.setColumnType(Type.valueOf(tname.toUpperCase()));
//			if (tname.equals(TYPE_VARCHAR)) {
//				int length = Integer.parseInt(typeDesc.substring(lindex + 1, rindex));
//				column.VARCHAR(length);
//			} else if (tname.equals(TYPE_CHAR)) {
//				int length = Integer.parseInt(typeDesc.substring(lindex + 1, rindex));
//				column.CHAR(length);
//			} else if (tname.equals(TYPE_DECIMAL)) {
//				int cindex = typeDesc.indexOf(',');
//				int length1 = Integer.parseInt(typeDesc.substring(lindex + 1, cindex));
//				int length2 = Integer.parseInt(typeDesc.substring(cindex + 1, rindex));
//				column.DECIMAL(length1, length2);
//			}else if (tname.equals(TYPE_NUMERIC)) {
//				int cindex = typeDesc.indexOf(',');
//				int length1 = Integer.parseInt(typeDesc.substring(lindex + 1, cindex));
//				int length2 = Integer.parseInt(typeDesc.substring(cindex + 1, rindex));
//				column.NUMERIC(length1, length2);
//			}
//		}
//	}
//
//}
