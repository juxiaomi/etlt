package org.etlt.validate;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.apache.commons.lang3.StringUtils;
import org.etlt.SettingCheck;
import org.etlt.SettingValidationException;
import org.etlt.load.ColumnSetting;
import org.etlt.load.DatabaseLoaderSetting;
import org.etlt.load.FileLoaderSetting;

import java.util.ArrayList;
import java.util.List;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({@JsonSubTypes.Type(value = FileValidatorSetting.class, name = "FILE")})
public class ValidatorSetting implements SettingCheck {

    private String name;

    private String result;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public void check() {
        if (StringUtils.isBlank(this.name)) {
            throw new SettingValidationException("missing name.");
        }
    }
}
