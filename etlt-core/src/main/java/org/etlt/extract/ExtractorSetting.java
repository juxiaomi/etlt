package org.etlt.extract;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.etlt.SettingCheck;
import org.etlt.SettingValidationException;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

//@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({@JsonSubTypes.Type(value = FileExtractSetting.class, name = "FILE"),
        @JsonSubTypes.Type(value = DatabaseExtractSetting.class, name = "DATA_BASE")})
public class ExtractorSetting implements SettingCheck {

    enum Type{
        FILE, DATA_BASE;
    }

    private String name;

    private int skip;

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

    @Override
    public void check() {
        if(StringUtils.isBlank(getName()))
            throw new SettingValidationException("extractor name missing." );
        if(StringUtils.isBlank(getName()))
            throw new SettingValidationException("datasource definition missing: " + getName() );
    }
}
