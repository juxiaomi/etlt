//package org.etlt.tool.db;
//
//import com.github.drinkjava2.jdialects.Dialect;
//import com.github.drinkjava2.jdialects.model.TableModel;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.List;
//
//
//public class DDLGenerator extends SettingEnable{
//
//
//	private static final String DB_SETTING = "db-setting.json";
//
//	private static final String DDL_SETTING = "ddl.json";
//
//	private static final String SUPPORTED_DB_SETTING = "databases.json";
//
//	private db.setting.DdlSetting ddlSetting;
//
//	private db.setting.DbSetting dbSetting;
//
//	private db.setting.SupportedDbSetting supportedDbSetting;
//
//
//	/**
//	 * arg0: setting directory
//	 * @param args
//	 * @throws Exception
//	 */
//	public static void main(String[] args) throws Exception {
//		if(args.length < 1){
//			throw new IllegalArgumentException("please set config directory.");
//		}
//		String configDir = args[0];
//		DDLGenerator generator = new DDLGenerator(configDir);
//		if(generator.ddlSetting.isWriteDdl()) {
//			generator.generateSql();
//		}
//		if(generator.ddlSetting.isExecute()) {
//			generator.createTables();
//		}
//	}
//
//
//	/**
//	 * 生成ddl语句
//	 */
//	public void generateSql() throws IOException{
//		ExcelParser parser = new ExcelParser();
//		List<TableModel> tables = parser.parse(new FileInputStream(new File(
//				getConfigDir(), this.ddlSetting.getDescFile())));
//
//		Dialect dialect = Dialect.guessDialectByType(this.dbSetting.getType());
//		FileWriter fileWriter = null;
//		try {
//			fileWriter = new FileWriter(new File(getConfigDir(), this.ddlSetting.getDdlFile()));
//			for (TableModel table : tables) {
//				String[] dropAndCreateDDLs = dialect.toDropAndCreateDDL(table);
//				for (String sql : dropAndCreateDDLs) {
//					fileWriter.write(sql + ";\n");
//				}
//			}
//			System.out.println(tables.size() + " tables ddl generated.");
//		}finally {
//			if(fileWriter != null) {
//				try {
//					fileWriter.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//
//	}
//
//	public DDLGenerator(String configDirectory){
//		super(configDirectory);
//		if(!getConfigDir().exists() || !getConfigDir().isDirectory())
//			throw new IllegalArgumentException("config directory not exists or is not a directory: " + getConfigDir());
//		init();
//	}
//
//	/**
//	 * 检查内容：<br>
//	 * 1:是否有excel描述文件
//	 */
//	public void init() {
//		try {
//			this.ddlSetting = readSetting(DDL_SETTING, DdlSetting.class);
//			this.dbSetting = readSetting(DB_SETTING, DbSetting.class);
//			this.supportedDbSetting = readSetting(SUPPORTED_DB_SETTING, SupportedDbSetting.class);
//		} catch (IOException e) {
//			throw new IllegalArgumentException(e);
//		}
//	}
//
//
//	/**
//	 * 直接创建表
//	 * @throws IOException
//	 * @throws SQLException
//	 */
//	public void createTables() throws Exception {
//		ExcelParser parser = new ExcelParser();
//		List<TableModel> tables = parser.parse(new FileInputStream(new File(
//				getConfigDir(), this.ddlSetting.getDescFile())));
//		Connection connection = createConnection();
//		Dialect dialect = Dialect.guessDialect(connection);
//		Statement statement = connection.createStatement();
//		for (TableModel table : tables) {
//			String[] dropAndCreateDDLs = dialect.toDropAndCreateDDL(table);
//			for (String sql : dropAndCreateDDLs) {
//				statement.executeUpdate(sql);
//			}
////			System.out.println("table [" + table.getTableName() + "] executed.");
//		}
//		System.out.println(tables.size() + " tables executed.");
//	}
//
//	public Connection createConnection() throws IOException, ClassNotFoundException, SQLException {
//		Class.forName(this.supportedDbSetting.getDriver(dbSetting.getType()));
//		String url = dbSetting.getUrl();
//		String username = dbSetting.getUser();
//		String password = dbSetting.getPassword();
//		return DriverManager.getConnection(url, username, password);
//	}
//
//}
