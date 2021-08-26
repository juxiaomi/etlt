package org.etlt.validate;

import org.apache.commons.lang3.StringUtils;
import org.etlt.SettingValidationException;

public class FileValidatorSetting extends ValidatorSetting{

    private String source;

    private String target;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    @Override
    public void check() {
        super.check();
        if (StringUtils.isBlank(this.source)) {
            throw new SettingValidationException("missing source.");
        }
        if (StringUtils.isBlank(this.target)) {
            throw new SettingValidationException("missing target.");
        }
    }
}
