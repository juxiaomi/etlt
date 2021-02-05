package org.etlt.load;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
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

    protected void init(JobContext context) {
        try {
            DatabaseLoaderSetting setting = getSetting();
            if (ObjectUtils.isEmpty(setting.getDataSource())) {
                Object ref = context.getParameter(setting.getDatasourceRef());
                ObjectMapper mapper = new ObjectMapper();
                setting.setDataSource(mapper.convertValue(ref, DbDsSetting.class));
            }
            resolveColumns(context);
            DbDsSetting dbDsSetting = setting.getDataSource();
            Class.forName(dbDsSetting.getClassName());
            this.connection = DriverManager.getConnection(dbDsSetting.getUrl(), dbDsSetting.getUser(), dbDsSetting.getPassword());
            this.statement = connection.prepareStatement(setting.getDml());
        }catch (SQLException | ClassNotFoundException e){
            throw new EtltException("executing loader error: " + getName(), e);
        }
    }

    @Override
    public void preLoad(JobContext context) {
        init(context);
        DatabaseLoaderSetting setting = getSetting();
        if(!StringUtils.isBlank(setting.getPreDml())){
            try(PreparedStatement statement = this.connection.prepareStatement(setting.getPreDml())){
                statement.execute();
            } catch (SQLException e) {
                throw new EtltException("preLoad error:" + getName() , e);
            }
        }
    }

    /**
     * @param context
     */
    @Override
    public void load(JobContext context) {
        try {

            DatabaseLoaderSetting setting = getSetting();
            List<ColumnSetting> columns = setting.getColumns();
            String ds = setting.getExtractor();
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
        } catch (SQLException e) {
            throw new EtltException("executing loader error: " + getName(), e);
        }
    }

    @Override
    public void doFinish() {
        close(statement, connection);
    }

}
