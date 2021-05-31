package org.etlt.job;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.apache.commons.lang3.StringUtils;
import org.etlt.SettingCheck;
import org.etlt.SettingValidationException;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY, visible = true,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = DatasourceResourceSetting.class, name = "DATA_BASE")})
public abstract class ResourceSetting implements SettingCheck {

    @Override
    public void check() {
        if (StringUtils.isBlank(getName())) {
            throw new SettingValidationException("name is missing.");
        }
    }

    private String name;

    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
