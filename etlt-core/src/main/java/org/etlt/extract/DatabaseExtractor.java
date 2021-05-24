package org.etlt.extract;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.etlt.EtltException;
import org.etlt.job.JobContext;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseExtractor extends Extractor {
    final DatabaseExtractSetting setting;

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
            if (this.setting.getDataSource() == null) {
                Object ref = context.getParameter(this.setting.getDatasourceRef());
                ObjectMapper mapper = new ObjectMapper();
                this.setting.setDataSource(mapper.convertValue(ref, DbDsSetting.class));
            }
            DbDsSetting dbDsSetting = this.setting.getDataSource();
            Class.forName(dbDsSetting.getClassName());
            this.connection = DriverManager.getConnection(dbDsSetting.getUrl(), dbDsSetting.getUser(), dbDsSetting.getPassword());
            this.statement = this.connection.prepareStatement(this.setting.getDql());
            resultSet = this.statement.executeQuery();
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int columnCount = resultSetMetaData.getColumnCount();
            if (this.setting.isAutoResolve() && getColumns().size() == 0) {
                for (int i = 0; i < columnCount; i++) {
                    getColumns().add(resultSetMetaData.getColumnLabel(i + 1));
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new EtltException("Extractor init error: " + getName(), e);
        }
    }


    @Override
    public void extract(JobContext context) {
        try {
            if (resultSet.next()) {
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
                                        DatabaseUtil.getObject(resultSet, i+1,
                                                resultSetMetaData.getColumnType(i+1))
                                    );
                    }
                    Entity entity = new Entity(index++, rowData);
                    context.setEntity(this.setting.getName(), entity);
                }
            } else {
                context.removeEntity(this.setting.getName());
                doFinish();
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
