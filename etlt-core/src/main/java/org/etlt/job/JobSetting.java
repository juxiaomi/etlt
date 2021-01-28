package org.etlt.job;

import org.etlt.SettingCheck;
import org.etlt.SettingValidationException;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class JobSetting implements SettingCheck {

    private String name;

    private List<String> extractors;

    private List<String> loaders;

    private String mapping;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getExtractors() {
        return extractors;
    }

    public void setExtractors(List<String> extractors) {
        this.extractors = extractors;
    }

    public List<String> getLoaders() {
        return loaders;
    }

    public void setLoaders(List<String> loaders) {
        this.loaders = loaders;
    }

    public String getMapping() {
        return mapping;
    }

    public void setMapping(String mapping) {
        this.mapping = mapping;
    }

    @Override
    public void check() {
        if(StringUtils.isBlank(getName()))
            throw new SettingValidationException("missing name.");
    }
}