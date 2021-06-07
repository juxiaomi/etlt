package org.etlt.load;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.etlt.EtltException;
import org.etlt.EtltRuntimeException;
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
import java.util.concurrent.CountDownLatch;

public class DatabaseLoader extends Loader {

    private DataSource dataSource;

//    private Connection connection;

    private CountDownLatch countDownLatch;

    public DatabaseLoader(DatabaseLoaderSetting setting) {
        super(setting);
        setName(setting.getName());
    }

    protected void init(JobContext context) {
        DatabaseLoaderSetting setting = getSetting();
        this.dataSource = context.getResource(setting.getDatasource());
        this.countDownLatch = new CountDownLatch(setting.getParallel());
        resolveColumns(context);
    }

    @Override
    public void preLoad(JobContext context) {
        init(context);
        DatabaseLoaderSetting setting = getSetting();
        if (!StringUtils.isBlank(setting.getPreDml())) {
            try (PreparedStatement statement = this.getConnection().prepareStatement(setting.getPreDml())) {
                statement.execute();
            } catch (SQLException e) {
                throw new EtltRuntimeException("preLoad error:" + getName(), e);
            }
        }
    }

    public void load(JobContext context){
        DatabaseLoaderSetting setting = getSetting();
        ThreadGroup threadGroup = new ThreadGroup(getName());
        for(int i = 0; i < setting.getParallel(); i++){
            JobContext newContext = context.copy();
            Thread worker = new Thread(threadGroup, ()->load0(newContext, getConnection()),getName() + "-worker-" + i);
            worker.start();

        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new EtltRuntimeException(e);
        }
    }
    /**
     * @param context
     */
    private void load0(JobContext context, Connection connection) {
        Extractor extractor = null;
        try {
            DatabaseLoaderSetting setting = getSetting();
            List<ColumnSetting> columns = setting.getColumns();
            String ds = setting.getExtractors().get(0);
            extractor = context.getExtractor(ds);
            ExpressionCompiler expressionCompiler = new ExpressionCompiler();
            int batch = 0;
            boolean autoCommit = connection.getAutoCommit();
            if (autoCommit)
                connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement(setting.getDml());
            for (extractor.extract(context); context.isExist(ds); extractor.extract(context)) {
                for (int i = 0; i < columns.size(); i++) {
                    ColumnSetting column = columns.get(i);
                    Object result = expressionCompiler.evaluate(column.getExpression(), context);
                    DatabaseUtil.setObject(statement, i + 1, result);
                }
                statement.addBatch();
                batch++;
                if (batch % getSetting().getBatch() == 0) {
                    statement.executeBatch();
                    connection.commit();
                    batch = 0;
                }
            }
            if (batch != 0) {
                statement.executeBatch();
                connection.commit();
                connection.setAutoCommit(autoCommit);
            }
        } catch (SQLException e) {
            try {
                if (connection != null && !connection.getAutoCommit())
                    connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new EtltRuntimeException("executing loader error: " + getName(), e);
        } finally {
            this.countDownLatch.countDown();
            close(connection);
            if(extractor != null)
                extractor.doFinish();
            System.out.println(Thread.currentThread() + " quit." + System.currentTimeMillis());
        }
    }

    @Override
    public void doFinish() {
    }

    protected void mappingTypes(Object object) {
    }

    protected Connection getConnection()  {
        try {
            return this.dataSource.getConnection();
        } catch (SQLException e) {
            throw new EtltRuntimeException(e);
        }
    }

}
