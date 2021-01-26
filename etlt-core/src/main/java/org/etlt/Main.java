package org.etlt;

import org.etlt.job.JobExecutor;

import java.io.File;

/**
 * 
 * @author juxiaomi
 * @date 2020年10月20日 - 下午5:29:27
 *
 */
public class Main {
    /**
     *
     * @param args
     * args[0] - etlt config directory
     */
    public static void main(String args[]){
        if(args.length < 1)
            throw new IllegalArgumentException("please set the etl config directory.");
        File jobDirectory = new File(args[0]);
        JobExecutor jobExecutor = new JobExecutor();
        jobExecutor.execute(jobDirectory);
    }

}
