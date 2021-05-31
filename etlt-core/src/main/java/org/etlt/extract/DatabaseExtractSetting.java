package org.etlt.extract;

public class DatabaseExtractSetting extends ExtractorSetting{

    private boolean autoResolve = true;

    private String dql;

    private String datasource;

    public boolean isAutoResolve() {
        return autoResolve;
    }

    public void setAutoResolve(boolean autoResolve) {
        this.autoResolve = autoResolve;
    }

    public String getDql() {
        return dql;
    }

    public void setDql(String dql) {
        this.dql = dql;
    }

    public String getDatasource() {
        return datasource;
    }

    public void setDatasource(String dataSource) {
        this.datasource = dataSource;
    }

    @Override
    public void check(){
        super.check();
        if(!autoResolve){
            if(getColumns().size() == 0){
                throw new IllegalArgumentException("missing column definition.");
            }
        }
        assertNotNull("datasource", datasource);
    }
}
