package org.etlt.job;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.etlt.EtltException;
import org.etlt.EtltRuntimeException;
import org.etlt.load.Loader;
import org.etlt.util.SystemUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ParalleledJobExecutor {

    private int count = SystemUtil.getCpuCount();

    protected Log log = LogFactory.getLog(getClass());

    public ParalleledJobExecutor() {

    }

    public ParalleledJobExecutor(int count) {
        if (count > 0 && count <= SystemUtil.getCpuCount())
            this.count = count;
    }

    public void execute(File jobDirectory) {
        try {
            final JobContext jobContext = new JobContext(jobDirectory);
            execute(jobContext);
        } catch (IOException e) {
            throw  new EtltRuntimeException(e);
        }

    }

    public void execute(JobContext jobContext) {
        try {
            log.info("job is initializing ...");
            jobContext.init();
            List<Loader> _loaders = jobContext.getAllLoader();
            log.info("there are " + _loaders.size() + " loaders.");
            log.info("now all loaders will be started in " + this.count + " threads.");
            this.loaders.addAll(_loaders);
            JobExecutor executor = new JobExecutor();
            for (int i = 0; i < this.count; i++) {
                Thread worker = new Thread(() -> {
                    for (Loader taskLoader = getLoader(); taskLoader != null; taskLoader = getLoader()) {
                        log.info("existing loaders need to be handled: " + this.loaders.size() + "/" + _loaders.size());
                        executor.execute(jobContext, taskLoader);
                    }
                }, "job-worker-" + i);
                worker.start();
            }
        } catch (IOException e) {
            throw new EtltException("job executing error.", e);
        }
    }

    protected Loader getLoader() {
        return this.loaders.poll();
    }

    private Queue<Loader> loaders = new ConcurrentLinkedQueue<>();
}
