package org.etlt.extract;

import org.etlt.job.JobContext;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseExtractor extends Extractor {
    final DatabaseExtractSetting setting;

    private Connection connection;

    private PreparedStatement statement;

    private ResultSet resultSet;

    private int skip = 0;

    public DatabaseExtractor(DatabaseExtractSetting setting) {
        this.setting = setting;
        this.setName(setting.getName());
        try {
            init();
        } catch (SQLException | ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }

    protected void init() throws SQLException, ClassNotFoundException {
        DbDsSetting dbDsSetting = this.setting.getDataSource();
        Class.forName(dbDsSetting.getClassName());
        this.connection = DriverManager.getConnection(dbDsSetting.getUrl(), dbDsSetting.getUser(), dbDsSetting.getPassword());
    }

    @Override
    public void extract(JobContext context) {
        try {
            this.statement = this.connection.prepareStatement(this.setting.getDql());
            if (resultSet == null)
                resultSet = this.statement.executeQuery();
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int columnCount = resultSetMetaData.getColumnCount();
            if (resultSet.next()) {
                if (this.skip < this.setting.getSkip()) {
                    this.skip++;
                    extract(context);
                } else {
                    Map<String, Object> rowData = new HashMap<>();
                    for (int i = 1; i <= columnCount; i++) {
                        rowData.put(resultSetMetaData.getColumnLabel(i), resultSet.getObject(i));
                    }
                    context.setEntity(this.setting.getName(), rowData);
                }
            }else {
                context.removeEntity(this.setting.getName());
                doFinish();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doFinish() {
        if (this.resultSet != null) {
            try {
                this.resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (this.statement != null) {
            try {
                this.statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (this.connection != null) {
            try {
                this.connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
