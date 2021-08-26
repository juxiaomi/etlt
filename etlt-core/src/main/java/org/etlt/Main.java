package org.etlt;

import org.etlt.job.JobContext;
import org.etlt.job.JobExecutor;
import org.etlt.job.ParalleledJobExecutor;

import java.io.File;
import java.io.IOException;

/**
 * 
 * @author juxiaomi
 * @date 2020年10月20日 - 下午5:29:27
 *
 */
public class Main {

    public static final String JOB_INVENTORY = "job.inventory";

    /**
     *
     * @param args
     * args[0] - etlt config directory
     */
    public static void main(String args[]) throws IOException {
        if(args.length < 1)
            throw new IllegalArgumentException("please set the etl config directory.");
        File jobDirectory = new File(args[0]);
        JobContext context = new JobContext(jobDirectory);
        context.init();
        System.setProperty(JOB_INVENTORY, context.getContextRoot().getAbsolutePath());
        runAsParallel(context);
    }

    /**
     * run as parallel, many loaders are running within different threads
     * @param context
     */
    protected static void runAsParallel(JobContext context){
        ParalleledJobExecutor paralleledJobExecutor = new ParalleledJobExecutor(context.getJobSetting().getParallel());
        paralleledJobExecutor.execute(context);
    }

    /**
     * run as serial, all loaders are running within a single thread
     * @param context
     */
    protected static void runAsSerial(JobContext context){
        JobExecutor jobExecutor = new JobExecutor();
        jobExecutor.execute(context);
    }

}
