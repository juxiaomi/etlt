package org.etlt.load;

import org.etlt.util.SystemUtil;

/**
 *
 */
public class DatabaseLoaderSetting extends LoaderSetting{
    private String preDml;

    private String dml;

    private String datasource;

    private int parallel = 1;

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

    public int getParallel() {
        return parallel;
    }

    public void setParallel(int parallel) {
        this.parallel = parallel;
    }

    @Override
    public void check(){
        super.check();
        assertNotNull("dml", this.dml);
        assertNotNull("datasource", this.datasource);
        assertCondition("parallel must be greater than 0.", getParallel() > 0 );
        assertCondition("parallel must be less than cpu size.", getParallel() <= SystemUtil.getCpuCount() );
    }
}
