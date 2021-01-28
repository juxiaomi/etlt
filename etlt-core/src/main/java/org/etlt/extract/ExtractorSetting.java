package org.etlt.extract;

import org.etlt.SettingCheck;
import org.etlt.SettingValidationException;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExtractorSetting implements SettingCheck {

    private String name;

    private int skip;

    private List<String> columns;

    private String delim = ",";

    private String dataSource;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSkip() {
        return skip;
    }

    public void setSkip(int skip) {
        this.skip = skip;
    }

    public List<String> getColumns() {
        return columns;
    }

    public void setColumns(List<String> columns) {
        this.columns = columns;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getDelim() {
        return delim;
    }

    public void setDelim(String delim) {
        this.delim = delim;
    }

    @Override
    public void check() {
        if(StringUtils.isBlank(getName()))
            throw new SettingValidationException("extractor name missing." );
        if(getColumns().size() == 0)
            throw new SettingValidationException("extractor columns definition missing: " + getName() );
        if(StringUtils.isBlank(getName()))
            throw new SettingValidationException("datasource definition missing: " + getName() );
    }
}
