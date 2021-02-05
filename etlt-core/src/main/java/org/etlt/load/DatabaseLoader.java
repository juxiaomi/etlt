package org.etlt.load;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.etlt.EtltException;
import org.etlt.expression.ExpressionCompiler;
import org.etlt.extract.DbDsSetting;
import org.etlt.extract.Extractor;
import org.etlt.job.JobContext;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class DatabaseLoader extends Loader {

    private Connection connection;

    private PreparedStatement statement;

    public DatabaseLoader(DatabaseLoaderSetting setting) {
        super(setting);
        setName(setting.getName());
    }

    protected void init(JobContext context) throws SQLException, ClassNotFoundException {
        DatabaseLoaderSetting setting = getSetting();
        if(setting.getDataSource() == null){
            Object ref = context.getParameter(setting.getDatasourceRef());
            ObjectMapper mapper = new ObjectMapper();
            setting.setDataSource(mapper.convertValue(ref, DbDsSetting.class));
        }
        resolveColumns(context);
        DbDsSetting dbDsSetting = setting.getDataSource();
        Class.forName(dbDsSetting.getClassName());
        this.connection = DriverManager.getConnection(dbDsSetting.getUrl(), dbDsSetting.getUser(), dbDsSetting.getPassword());
        this.statement = connection.prepareStatement(setting.getDml());
    }

    /**
     * @param context
     */
    @Override
    public void load(JobContext context) {
        try {
            init(context);
            DatabaseLoaderSetting setting = getSetting();
            List<ColumnSetting> columns = setting.getColumns();
            String ds = setting.getDs();
            Extractor extractor = context.getExtractor(ds);
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
            throw new EtltException("executing loader error: " + getName(), e);
        }
    }

    @Override
    public void doFinish() {
        close(statement, connection);
    }

}
