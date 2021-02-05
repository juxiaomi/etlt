package org.etlt.extract;

import org.etlt.job.JobContext;

import java.util.List;

public abstract class Extractor {
    private String name;

    /**
     * extract data and set it to context
     * @param context
     */
    public abstract void extract(JobContext context);

    public abstract List<String> getColumns();

    /**
     * clean resources
     */
    public abstract void doFinish();

    public void close(AutoCloseable... resources) {
        for (AutoCloseable resource : resources) {
            try {
                if (resource != null)
                    resource.close();
            } catch (Exception e) {
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Extractor createExtractor(ExtractorSetting setting, JobContext context){
        if(setting instanceof FileExtractSetting){
            FileExtractor extractor = new FileExtractor((FileExtractSetting)setting);
            extractor.init(context);
            return  extractor;
        }else if(setting instanceof DatabaseExtractSetting){
            DatabaseExtractor extractor = new DatabaseExtractor((DatabaseExtractSetting) setting);
            extractor.init(context);
            return  extractor;
        }
        throw new IllegalArgumentException("unsupported extractor setting: " + setting.getName());
    }

    public void init(JobContext context){}

}
