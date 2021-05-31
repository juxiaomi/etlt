package org.etlt.job;

import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.etlt.EtltRuntimeException;

import java.sql.SQLException;

public class ResourceFactory {

    public static final String DATA_SOURCE = "DATA_BASE";

    public Object createResource(ResourceSetting resourceSetting){
        if(resourceSetting.getType().equals(DATA_SOURCE)){
            return createDatasource((DatasourceResourceSetting) resourceSetting);
        }
        throw new EtltRuntimeException("unsupported resource type: " + resourceSetting.getType());
    }

    private Object createDatasource(DatasourceResourceSetting datasourceResourceSetting){
        try {
            PoolProperties p = new PoolProperties();
            p.setUrl(datasourceResourceSetting.getUrl());
            p.setDriverClassName(datasourceResourceSetting.getClassName());
            p.setUsername(datasourceResourceSetting.getUser());
            p.setPassword(datasourceResourceSetting.getPassword());
            p.setJmxEnabled(true);
            p.setTestWhileIdle(true);
            p.setTestOnBorrow(true);
            p.setValidationQuery(datasourceResourceSetting.getValidationQuery());
            p.setTestOnReturn(false);
            p.setValidationInterval(30000);
            p.setTimeBetweenEvictionRunsMillis(30000);
            p.setInitialSize(datasourceResourceSetting.getInitialSize());
            p.setMinIdle(datasourceResourceSetting.getMinIdle());
            p.setMaxIdle(datasourceResourceSetting.getMaxIdle());
            p.setMaxActive(datasourceResourceSetting.getMaxActive());
            p.setMaxWait(10000);
            p.setRemoveAbandonedTimeout(60);
            p.setMinEvictableIdleTimeMillis(30000);
            p.setLogAbandoned(true);
            p.setJdbcInterceptors(
                    "org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;"
                            + "org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer;"
                            + "org.apache.tomcat.jdbc.pool.interceptor.ResetAbandonedTimer");

            org.apache.tomcat.jdbc.pool.DataSource dataSource = new org.apache.tomcat.jdbc.pool.DataSource();
            dataSource.setPoolProperties(p);
            dataSource.createPool();
            return dataSource;
        }catch (SQLException e){
            throw new EtltRuntimeException(e);
        }
    }
}
