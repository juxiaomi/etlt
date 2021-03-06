package org.etlt.extract;

import org.etlt.SettingValidationException;

import java.util.List;

public class FileExtractSetting extends ExtractorSetting{

    private String delim = ",";

    private String dataSource;

    private String encoding = ExtractorSetting.UTF_8;

    public String getDelim() {
        return delim;
    }

    public void setDelim(String delim) {
        this.delim = delim;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void check(){
        super.check();
        if(getColumns().size() == 0)
            throw new SettingValidationException("extractor columns definition missing: " + getName() );
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }
}
