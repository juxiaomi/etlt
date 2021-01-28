package org.etlt.load;

import org.etlt.SettingCheck;
import org.etlt.SettingValidationException;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class LoadSetting implements SettingCheck {
    private String name;

    private boolean usingBanner;

    private String ds;

    private List<ColumnSetting> columns;

    private String delim = ",";

    private String target;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isUsingBanner() {
        return usingBanner;
    }

    public void setUsingBanner(boolean usingBanner) {
        this.usingBanner = usingBanner;
    }

    public String getDs() {
        return ds;
    }

    public void setDs(String ds) {
        this.ds = ds;
    }

    public List<ColumnSetting> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnSetting> columns) {
        this.columns = columns;
    }

    public String getDelim() {
        return delim;
    }

    public void setDelim(String delim) {
        this.delim = delim;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    @Override
    public void check() {
        if(StringUtils.isBlank(getName()))
            throw new SettingValidationException("missing name.");
        if(StringUtils.isBlank(getTarget()))
            throw new SettingValidationException("missing target: " + getName());
        if(getColumns().size() == 0)
            throw new SettingValidationException("missing column definitions: " + getName());
        if(StringUtils.isBlank(getDs()))
            throw new SettingValidationException("missing ds: " + getName());
    }
}
