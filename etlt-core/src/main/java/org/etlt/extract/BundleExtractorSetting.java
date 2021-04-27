package org.etlt.extract;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.etlt.SettingCheck;
import org.etlt.SettingValidationException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BundleExtractorSetting implements SettingCheck {

    public static final String UTF_8 = "utf8";

    private Map<String, Object> properties;

    private List<Map> extractors;

    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

    public List<Map> getExtractors() {
        return extractors;
    }

    public void setExtractors(List<Map> extractors) {
        this.extractors = extractors;
    }

    @Override
    public void check() {
    }

    public List<ExtractorSetting> createExtractorSetting() {
        ObjectMapper mapper = new ObjectMapper();
        List<ExtractorSetting> extractorSettings = new ArrayList<>();
        this.extractors.forEach((ext) -> {
            Map setting = new HashMap();
            setting.putAll(this.properties);
            setting.putAll(ext);
            extractorSettings.add(
                    mapper.convertValue(setting, ExtractorSetting.class));
        });
        return extractorSettings;
    }
}
