package org.etlt.extract;

import org.etlt.job.JobContext;

public abstract class Extractor {
    private String name;

    /**
     * extract data and set it to context
     * @param context
     */
    public abstract void extract(JobContext context);

    /**
     * clean resources
     */
    public abstract void doFinish();

//    abstract List<Map<String,String>> extractAll();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
