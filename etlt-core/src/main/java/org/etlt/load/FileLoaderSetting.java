package org.etlt.load;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.apache.commons.lang3.StringUtils;
import org.etlt.SettingCheck;
import org.etlt.SettingValidationException;

import java.util.List;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({@JsonSubTypes.Type(value = FileLoaderSetting.class, name = "FILE"),
        @JsonSubTypes.Type(value = DatabaseLoaderSetting.class, name = "DATA_BASE")})
public class FileLoaderSetting extends LoadSetting {
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
