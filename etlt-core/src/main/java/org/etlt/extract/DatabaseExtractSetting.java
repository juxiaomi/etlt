package org.etlt.extract;

public class DatabaseExtractSetting extends ExtractorSetting{

    private boolean autoResolve = true;

    private String dql;

    private String datasourceRef;

    private DbDsSetting dataSource;

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

    public String getDatasourceRef() {
        return datasourceRef;
    }

    public void setDatasourceRef(String datasourceRef) {
        this.datasourceRef = datasourceRef;
    }

    public DbDsSetting getDataSource() {
        return dataSource;
    }

    public void setDataSource(DbDsSetting dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void check(){
        super.check();
        if(!autoResolve){
            if(getColumns().size() == 0){
                throw new IllegalArgumentException("missing column definition.");
            }
        }
        assertNotNull("datasourceRef, datasource", datasourceRef, dataSource);
    }
}
