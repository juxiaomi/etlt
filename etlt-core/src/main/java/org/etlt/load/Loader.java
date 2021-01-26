package org.etlt.load;

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
}
