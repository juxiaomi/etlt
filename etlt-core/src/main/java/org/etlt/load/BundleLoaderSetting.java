package org.etlt.load;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.etlt.SettingCheck;
import org.etlt.SettingValidationException;
import org.etlt.extract.ExtractorSetting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BundleLoaderSetting implements SettingCheck {

    private Map<String, Object> properties;

    private List<Map> loaders;

    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

    public List<Map> getLoaders() {
        return loaders;
    }

    public void setLoaders(List<Map> loaders) {
        this.loaders = loaders;
    }

    @Override
    public void check() {
    }

    public List<LoaderSetting> createLoaderSetting() {
        ObjectMapper mapper = new ObjectMapper();
        List<LoaderSetting> loaderSettings = new ArrayList<>();
        this.loaders.forEach((ext) -> {
            Map setting = new HashMap();
            setting.putAll(this.properties);
            setting.putAll(ext);
            loaderSettings.add(
                    mapper.convertValue(setting, LoaderSetting.class));
        });
        return loaderSettings;
    }
}
