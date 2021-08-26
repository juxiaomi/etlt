package org.etlt.extract;

import org.etlt.job.JobContext;

import java.util.List;

public abstract class Extractor {
    private String name;

    protected int skip = 0;

    protected int index = 0;

    /**
     * extract data and set it to context
     * @param context
     */
    public abstract void extract(JobContext context);

    public abstract List<String> getColumns();

    public abstract <T extends  Extractor> T createInstance();

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

    public void init(JobContext context){}

}
