package org.etlt.load;

import org.etlt.extract.DbDsSetting;

/**
 *
 */
public class DatabaseLoaderSetting extends LoaderSetting{
    private String preDml;

    private String dml;

    private String datasource;

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

    public String getDatasource() {
        return datasource;
    }

    public void setDatasource(String dataSource) {
        this.datasource = dataSource;
    }

    @Override
    public void check(){
        super.check();
        assertNotNull("dml", this.dml);
        assertNotNull("datasource", this.datasource);
    }
}
