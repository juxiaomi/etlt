package org.etlt.validate;

import org.etlt.SettingReader;
import org.etlt.job.JobContext;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ValidatorFactory {
    private static final ValidatorFactory VALIDATOR_FACTORY = new ValidatorFactory();

    private ValidatorFactory(){}

    public static ValidatorFactory getInstance(){
        return VALIDATOR_FACTORY;
    }

    private final SettingReader reader = new SettingReader();
    public List<Validator> resolveInstances(String[] settingFiles, JobContext context) throws IOException {
        List<Validator> validators = new ArrayList<>();
        for (String ext : settingFiles) {
            validators.add(resolveInstance(ext, context));
        }
        return validators;
    }

    public Validator resolveInstance(String settingFile, JobContext context) throws IOException {
        ValidatorSetting validatorSetting = this.reader.read(new File(context.getContextRoot(), settingFile), ValidatorSetting.class);
        return createValidator(validatorSetting);
    }

    public List<Validator> resolveBundleInstances(String[] settingFiles, JobContext context) throws IOException {
        List<Validator> validators = new ArrayList<>();
        for (String ext : settingFiles) {
            validators.addAll(resolveBundleInstance(ext, context));
        }
        return validators;
    }

    public List<Validator> resolveBundleInstance(String settingFile, JobContext context) throws IOException {
        BundleValidatorSetting bundleValidatorSetting = this.reader.read(new File(context.getContextRoot(), settingFile), BundleValidatorSetting.class);
        List<ValidatorSetting> validatorSettings = bundleValidatorSetting.createValidatorSetting();
        List<Validator> extractors = new ArrayList<>(validatorSettings.size());
        validatorSettings.forEach((setting) -> {
            extractors.add(createValidator(setting));
        });
        return extractors;
    }

    public Validator createValidator(ValidatorSetting setting){
        if (setting instanceof FileValidatorSetting) {
            return new FileValidator((FileValidatorSetting) setting);
        }
        throw new IllegalArgumentException("unsupported validator setting: " + setting.getName());
    }
}
