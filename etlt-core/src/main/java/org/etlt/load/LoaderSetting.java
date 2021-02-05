package org.etlt.load;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.etlt.SettingCheck;
import org.etlt.SettingValidationException;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({@JsonSubTypes.Type(value = FileLoaderSetting.class, name = "FILE"),
        @JsonSubTypes.Type(value = DatabaseLoaderSetting.class, name = "DATA_BASE")})
public class LoaderSetting implements SettingCheck {
    private String name;

    private String extractor;

    private boolean autoResolve = false;

    private List<ColumnSetting> columns = new ArrayList<ColumnSetting>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExtractor() {
        return extractor;
    }

    public void setExtractor(String extractor) {
        this.extractor = extractor;
    }

    public boolean isAutoResolve() {
        return autoResolve;
    }

    public void setAutoResolve(boolean autoResolve) {
        this.autoResolve = autoResolve;
    }

    public List<ColumnSetting> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnSetting> columns) {
        this.columns = columns;
    }

    @Override
    public void check() {
        if(StringUtils.isBlank(getName()))
            throw new SettingValidationException("missing name.");
        if(!isAutoResolve() &&  getColumns().isEmpty())
            throw new SettingValidationException("missing column definitions: " + getName());
        if(StringUtils.isBlank(getExtractor()))
            throw new SettingValidationException("missing ds: " + getName());
    }
}
