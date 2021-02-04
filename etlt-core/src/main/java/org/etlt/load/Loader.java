package org.etlt.load;

import org.etlt.extract.DatabaseExtractSetting;
import org.etlt.extract.DatabaseExtractor;
import org.etlt.extract.FileExtractSetting;
import org.etlt.extract.FileExtractor;
import org.etlt.job.JobContext;

public abstract class Loader {

    private String name;

    /**
     * load the data to target
     * @param context
     */
    public abstract void load(JobContext context);

    public abstract void doFinish();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Loader createLoader(LoadSetting setting){
        if(setting instanceof FileLoaderSetting){
            return new FileLoader((FileLoaderSetting)setting);
        }else if(setting instanceof DatabaseLoaderSetting){
            return new DatabaseLoader((DatabaseLoaderSetting) setting);
        }
        throw new IllegalArgumentException("unsupported loader setting: " + setting.getName());
    }
}
