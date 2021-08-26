package org.etlt.validate;

import org.etlt.job.JobContext;

public abstract class Validator implements Comparable<Validator>{

    private final ValidatorSetting setting;

    protected Validator(ValidatorSetting setting) {
        this.setting = setting;
    }

    protected <T extends ValidatorSetting> T getSetting(){
        return (T) setting;
    }

    public abstract void validate(JobContext context);

    public static Validator createValidator(ValidatorSetting setting){
        if (setting instanceof FileValidatorSetting) {
            return new FileValidator((FileValidatorSetting) setting);
        }
        throw new IllegalArgumentException("unsupported validator setting: " + setting.getName());
    }

    @Override
    public int compareTo(Validator validator) {
        return this.getName().compareTo(validator.getName());
    }

    public String getName(){
        return this.setting.getName();
    }
}
