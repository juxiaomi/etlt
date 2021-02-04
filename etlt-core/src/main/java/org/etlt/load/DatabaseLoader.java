package org.etlt.load;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.etlt.EtltException;
import org.etlt.expression.ExpressionCompiler;
import org.etlt.extract.DbDsSetting;
import org.etlt.extract.Extractor;
import org.etlt.job.JobContext;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class DatabaseLoader extends Loader {

    private Connection connection;

    private PreparedStatement statement;

    private final DatabaseLoaderSetting setting;

    public DatabaseLoader(DatabaseLoaderSetting setting) {
        this.setting = setting;
        setName(setting.getName());
    }

    protected void init(JobContext context) throws SQLException, ClassNotFoundException {
        if(this.setting.getDataSource() == null){
            Object ref = context.getParameter(this.setting.getDatasourceRef());
            ObjectMapper mapper = new ObjectMapper();
            this.setting.setDataSource(mapper.convertValue(ref, DbDsSetting.class));
        }
        DbDsSetting dbDsSetting = this.setting.getDataSource();
        Class.forName(dbDsSetting.getClassName());
        this.connection = DriverManager.getConnection(dbDsSetting.getUrl(), dbDsSetting.getUser(), dbDsSetting.getPassword());
        this.statement = connection.prepareStatement(this.setting.getDml());
    }

    /**
     * @param context
     */
    @Override
    public void load(JobContext context) {
        try {
            init(context);
            List<ColumnSetting> columns = this.setting.getColumns();
            String ds = this.setting.getDs();
            Extractor extractor = context.getExtractor(ds);
            if (extractor == null) {
                throw new EtltException("extractor not found: " + ds);
            }
            ExpressionCompiler expressionCompiler = new ExpressionCompiler();
            for (extractor.extract(context); context.isExist(ds); extractor.extract(context)) {
                for (int i = 0; i< columns.size(); i++) {
                    ColumnSetting column = columns.get(i);
                    Object result = expressionCompiler.evaluate(column.getExpression(), context);
                    this.statement.setObject(i+1, result);
                }
                //--todo: should optimized to batch operation
                this.statement.execute();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doFinish() {
    }

}
