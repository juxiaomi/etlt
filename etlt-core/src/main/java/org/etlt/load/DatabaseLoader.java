package org.etlt.load;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.etlt.EtltException;
import org.etlt.expression.ExpressionCompiler;
import org.etlt.extract.DbDsSetting;
import org.etlt.extract.Extractor;
import org.etlt.job.JobContext;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            String ds = setting.getExtractors().get(0);
            Extractor extractor = context.getExtractor(ds);
            ExpressionCompiler expressionCompiler = new ExpressionCompiler();
            int batch = 0;
            connection.setAutoCommit(false);
            for (extractor.extract(context); context.isExist(ds); extractor.extract(context)) {
                for (int i = 0; i< columns.size(); i++) {
                    ColumnSetting column = columns.get(i);
                    Object result = expressionCompiler.evaluate(column.getExpression(), context);
                    this.statement.setObject(i+1, result);

                }
                this.statement.addBatch();
                batch++;
                if(batch % getSetting().getBatch() == 0) {
                    this.statement.executeBatch();
                    batch = 0;
                }
            }
            if(batch != 0) {
                this.statement.executeBatch();
            }
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            throw new EtltException("executing loader error: " + getName(), e);
        }
    }

    @Override
    public void doFinish() {
        close(statement, connection);
    }

    protected void mappingTypes(Object object){

    }

}
