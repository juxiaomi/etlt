package org.etlt.job;

import org.etlt.SettingCheck;
import org.etlt.SettingValidationException;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

public class JobSetting implements SettingCheck {

    private String name;

    private int parallel = 1;

    private List<String> extractors;

    private List<String> loaders;

    private List<String> validators;

    private String mapping;

    private Map<String, Object> parameters;

    private List<ResourceSetting> resources;

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

    public List<String> getValidators() {
        return validators;
    }

    public String getMapping() {
        return mapping;
    }

    public void setMapping(String mapping) {
        this.mapping = mapping;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }

    public void setResources(List<ResourceSetting> settings){
        this.resources = settings;
    }

    public int getParallel() {
        return parallel;
    }

    public void setParallel(int parallel) {
        this.parallel = parallel;
    }

    public List<ResourceSetting> getResources(){
        return this.resources;
    }

    @Override
    public void check() {
        if(StringUtils.isBlank(getName()))
            throw new SettingValidationException("missing name.");
        if(getParallel() < 1)
            throw new SettingValidationException("parallel must greater than 0: " + getParallel());
    }
}
