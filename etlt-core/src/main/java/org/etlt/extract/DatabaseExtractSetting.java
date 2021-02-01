package org.etlt.extract;

public class DatabaseExtractSetting extends ExtractorSetting{
    private String dql;

    private DbDsSetting dataSource;

    public String getDql() {
        return dql;
    }

    public void setDql(String dql) {
        this.dql = dql;
    }

    public DbDsSetting getDataSource() {
        return dataSource;
    }

    public void setDataSource(DbDsSetting dataSource) {
        this.dataSource = dataSource;
    }
}
