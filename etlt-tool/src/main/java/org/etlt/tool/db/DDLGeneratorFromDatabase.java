package org.etlt.tool.db;

import com.github.drinkjava2.jdialects.Dialect;
import com.github.drinkjava2.jdialects.TableModelUtils;
import com.github.drinkjava2.jdialects.model.TableModel;

import org.etlt.tool.db.setting.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.StringJoiner;

public class DDLGeneratorFromDatabase extends SettingEnable {

    private static final String DB_SETTING = "db-setting.json";

    private static final String SUPPORTED_DB_SETTING = "databases.json";

    private DbSetting dbSetting;

    private SupportedDbSetting supportedDbSetting;

    protected DDLGeneratorFromDatabase(String configDir) {
        super(configDir);
    }

    public static void main(String[] args) throws Exception {
        if(args.length < 1)
            throw new IllegalArgumentException("config directory is needed to start this program.");
        String config = args[0];
        DDLGeneratorFromDatabase ddlGeneratorFromDatabase = new DDLGeneratorFromDatabase(config);
        ddlGeneratorFromDatabase.init();

        Connection connection = ddlGeneratorFromDatabase.createConnection();
        Dialect dialect = Dialect.guessDialect(connection);
        // Reserved words are allowed
        Dialect.setGlobalAllowReservedWords(true);
        TableModel[] tableModels =
                TableModelUtils.db2Models(connection, dialect);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("-- there are ")
                .append(tableModels.length).append(" tables in this database: ")
                .append(ddlGeneratorFromDatabase.dbSetting.getUrl());
        StringJoiner stringJoiner = new StringJoiner("\n");
        for (TableModel tableModel : tableModels) {
            String[] sqls = dialect.toDropAndCreateDDL(tableModel);
            for (String sql : sqls){
                stringJoiner.add(sql + ";");
            }
        }
        System.out.println(stringBuilder.toString());
        System.out.println(stringJoiner.toString());
    }

    /**
     *
     */
    public void init() {
        try {
            this.dbSetting = readSetting(DB_SETTING, DbSetting.class);
            this.supportedDbSetting = readSetting(SUPPORTED_DB_SETTING, SupportedDbSetting.class);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public Connection createConnection() throws ClassNotFoundException, SQLException {
        Class.forName(this.supportedDbSetting.getDriver(dbSetting.getType()));
        String url = dbSetting.getUrl();
        String username = dbSetting.getUser();
        String password = dbSetting.getPassword();
        return DriverManager.getConnection(url, username, password);
    }
}
