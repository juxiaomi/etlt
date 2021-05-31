package org.etlt.load;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
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
        this.dataSource = (DataSource) context.getResource(setting.getDatasource());
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
                throw new EtltException("preLoad error:" + getName(), e);
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
            throw new EtltException("executing loader error: " + getName(), e);
        } finally {
            close(connection);
        }
    }

    @Override
    public void doFinish() {
    }

    protected void mappingTypes(Object object) {
    }

    protected Connection getConnection() throws SQLException {
        return this.dataSource.getConnection();
    }

}
