package org.etlt.load;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.etlt.EtltException;
import org.etlt.expression.ExpressionCompiler;
import org.etlt.extract.DatabaseUtil;
import org.etlt.extract.DbDsSetting;
import org.etlt.extract.Extractor;
import org.etlt.job.JobContext;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class DatabaseLoader extends Loader {

    private DataSource dataSource;


    public DatabaseLoader(DatabaseLoaderSetting setting) {
        super(setting);
        setName(setting.getName());
    }

    protected void init(JobContext context) {
        DatabaseLoaderSetting setting = getSetting();
        if (ObjectUtils.isEmpty(setting.getDataSource())) {
            Object ref = context.getParameter(setting.getDatasourceRef());
            ObjectMapper mapper = new ObjectMapper();
            setting.setDataSource(mapper.convertValue(ref, DbDsSetting.class));
        }
        resolveColumns(context);
        DbDsSetting dbDsSetting = setting.getDataSource();
        try {
            initDatasource(dbDsSetting);
        } catch (SQLException e) {
            throw new IllegalStateException("datasource init error", e);
        }
    }

    @Override
    public void preLoad(JobContext context) {
        init(context);
        DatabaseLoaderSetting setting = getSetting();
        if(!StringUtils.isBlank(setting.getPreDml())){
            try(PreparedStatement statement = this.getConnection().prepareStatement(setting.getPreDml())){
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
        Connection connection = null;
        try {
            DatabaseLoaderSetting setting = getSetting();
            List<ColumnSetting> columns = setting.getColumns();
            String ds = setting.getExtractors().get(0);
            Extractor extractor = context.getExtractor(ds);
            ExpressionCompiler expressionCompiler = new ExpressionCompiler();
            int batch = 0;
            connection = getConnection();
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement(setting.getDml());
            for (extractor.extract(context); context.isExist(ds); extractor.extract(context)) {
                for (int i = 0; i< columns.size(); i++) {
                    ColumnSetting column = columns.get(i);
                    Object result = expressionCompiler.evaluate(column.getExpression(), context);
//                    this.statement.setObject(i+1, result);
                    DatabaseUtil.setObject(statement, i+1, result);
                }
                statement.addBatch();
                batch++;
                if(batch % getSetting().getBatch() == 0) {
                    statement.executeBatch();
                    connection.commit();
                    batch = 0;
                }
            }
            if(batch != 0) {
                statement.executeBatch();
                connection.commit();
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            try {
                if(connection != null && !connection.getAutoCommit())
                    connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new EtltException("executing loader error: " + getName(), e);
        }finally {
            close(connection);
        }
    }

    @Override
    public void doFinish() {
        if(this.dataSource != null){
            if(this.dataSource instanceof org.apache.tomcat.jdbc.pool.DataSource){
                ((org.apache.tomcat.jdbc.pool.DataSource) this.dataSource).close();
            }
        }
    }

    protected void mappingTypes(Object object){
    }

    protected void initDatasource(DbDsSetting dbDsSetting) throws SQLException {
        PoolProperties p = new PoolProperties();
        p.setUrl(dbDsSetting.getUrl());
        p.setDriverClassName(dbDsSetting.getClassName());
        p.setUsername(dbDsSetting.getUser());
        p.setPassword(dbDsSetting.getPassword());
        p.setJmxEnabled(true);
        p.setTestWhileIdle(true);
        p.setTestOnBorrow(true);
        p.setValidationQuery(dbDsSetting.getValidationQuery());
        p.setTestOnReturn(false);
        p.setValidationInterval(30000);
        p.setTimeBetweenEvictionRunsMillis(30000);
        p.setMaxActive(100);
        p.setInitialSize(10);
        p.setMaxWait(10000);
        p.setRemoveAbandonedTimeout(60);
        p.setMinEvictableIdleTimeMillis(30000);
        p.setMinIdle(10);
        p.setLogAbandoned(true);
        p.setRemoveAbandoned(true);
//        p.setJdbcInterceptors(
//                "org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;"+
//                        "org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer");

        org.apache.tomcat.jdbc.pool.DataSource dataSource = new org.apache.tomcat.jdbc.pool.DataSource();
        dataSource.setPoolProperties(p);
        dataSource.createPool();
        this.dataSource = dataSource;
    }

    protected Connection getConnection() throws SQLException {
        return this.dataSource.getConnection();
    }

}
