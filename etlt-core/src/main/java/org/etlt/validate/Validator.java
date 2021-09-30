package org.etlt.validate;

import org.etlt.job.JobContext;
/**
 *
 *
 *
 *
 *
 *
 **/
public abstract class Validator implements Comparable<Validator>{

    private final ValidatorSetting setting;

    protected Validator(ValidatorSetting setting) {
        this.setting = setting;
    }

    protected <T extends ValidatorSetting> T getSetting(){
        return (T) setting;
    }

    public abstract void validate(JobContext context);

    @Override
    public int compareTo(Validator validator) {
        return this.getName().compareTo(validator.getName());
    }

    public String getName(){
        return this.setting.getName();
    }
}
