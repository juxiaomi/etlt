package org.etlt.load;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.apache.commons.lang3.StringUtils;
import org.etlt.SettingValidationException;
import org.etlt.extract.ExtractorSetting;

import java.security.cert.Extension;
import java.util.List;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({@JsonSubTypes.Type(value = FileLoaderSetting.class, name = "FILE"),
        @JsonSubTypes.Type(value = DatabaseLoaderSetting.class, name = "DATA_BASE")})
public class FileLoaderSetting extends LoaderSetting {
    private boolean usingBanner;

    private List<ColumnSetting> columns;

    private String delim = ",";

    private String encoding = ExtractorSetting.UTF_8;

    private String target;

    public boolean isUsingBanner() {
        return usingBanner;
    }

    public void setUsingBanner(boolean usingBanner) {
        this.usingBanner = usingBanner;
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
        super.check();
        if(StringUtils.isBlank(getTarget()))
            throw new SettingValidationException("missing target: " + getName());
        if(getColumns().isEmpty())
            throw new SettingValidationException("missing column definitions: " + getName());
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }
}
