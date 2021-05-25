package org.etlt.load;

import org.etlt.extract.Extractor;
import org.etlt.job.JobContext;

import java.util.ArrayList;
import java.util.List;

public abstract class Loader implements Comparable<Loader>{

    private String name;

    private final LoaderSetting setting;

    protected Loader(LoaderSetting setting) {
        this.setting = setting;
    }

    protected <T extends LoaderSetting> T getSetting(){
        return (T) this.setting;
    }

    public abstract void preLoad(JobContext context);
    /**
     * load the data to target
     *
     * @param context
     */
    public abstract void load(JobContext context);

    public abstract void doFinish();

    public void close(AutoCloseable... resources) {
        for (AutoCloseable resource : resources) {
            try {
                if (resource != null)
                    resource.close();
            } catch (Exception e) {
                // do nothing
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    protected void resolveColumns(JobContext context){
        if(this.setting.isAutoResolve()){
            Extractor extractor = context.getExtractor(this.setting.getExtractors().get(0));
            List<ColumnSetting> columnSettings = new ArrayList<ColumnSetting>();
            List<String> columns = extractor.getColumns();
            for(String column : columns){
                columnSettings.add(new ColumnSetting(column, this.setting.getExtractors().get(0)));
            }
            for(ColumnSetting userDefinedColumnSetting : columnSettings){
                for(ColumnSetting columnSetting : this.setting.getColumns()){
                    if(columnSetting.getName().equals(userDefinedColumnSetting.getName())){
                        columnSetting.setExpression(userDefinedColumnSetting.getExpression());
                    }
                }
            }
            this.setting.getColumns().clear();
            this.setting.getColumns().addAll(columnSettings);
        }
    }

    @Override
    public int compareTo(Loader anotherLoader) {
        return this.getName().compareTo(anotherLoader.getName());
    }

    public static Loader createLoader(LoaderSetting setting) {
        if (setting instanceof FileLoaderSetting) {
            return new FileLoader((FileLoaderSetting) setting);
        } else if (setting instanceof DatabaseLoaderSetting) {
            return new DatabaseLoader((DatabaseLoaderSetting) setting);
        }
        throw new IllegalArgumentException("unsupported loader setting: " + setting.getName());
    }
}
