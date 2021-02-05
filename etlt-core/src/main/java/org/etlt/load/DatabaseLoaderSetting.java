package org.etlt.load;

import org.etlt.extract.DbDsSetting;

/**
 *
 */
public class DatabaseLoaderSetting extends LoaderSetting{
    private String preDml;

    private String dml;

    private String datasourceRef;

    private DbDsSetting dataSource;

    public String getPreDml() {
        return preDml;
    }

    public void setPreDml(String preDml) {
        this.preDml = preDml;
    }

    public String getDml() {
        return dml;
    }

    public void setDml(String dml) {
        this.dml = dml;
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
        assertNotNull("dml", this.dml);
        assertNotNull("dataSource, datasourceRef", this.datasourceRef, this.dataSource);
    }
}
