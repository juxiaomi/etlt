package org.etlt.validate;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.etlt.SettingCheck;
import org.etlt.load.LoaderSetting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BundleValidatorSetting implements SettingCheck {

    private Map<String, Object> properties;

    private List<Map> validators;

    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

    public List<Map> getValidators() {
        return validators;
    }

    public void setValidators(List<Map> validators) {
        this.validators = validators;
    }

    @Override
    public void check() {
    }

    public List<ValidatorSetting> createValidatorSetting() {
        ObjectMapper mapper = new ObjectMapper();
        List<ValidatorSetting> validatorSettings = new ArrayList<>();
        this.validators.forEach((ext) -> {
            Map setting = new HashMap();
            setting.putAll(this.properties);
            setting.putAll(ext);
            validatorSettings.add(
                    mapper.convertValue(setting, ValidatorSetting.class));
        });
        return validatorSettings;
    }
}
