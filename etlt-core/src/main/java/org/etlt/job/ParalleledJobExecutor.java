package org.etlt.job;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.etlt.EtltRuntimeException;
import org.etlt.load.Loader;
import org.etlt.util.SystemUtil;
import org.etlt.validate.Validator;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.*;

public class ParalleledJobExecutor {

    private int count = SystemUtil.getCpuCount();

    private final ExecutorService executorService = Executors.newFixedThreadPool(this.count);

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
            jobContext.init();
            executeLoaders(jobContext);
        } catch (IOException e) {
            throw new EtltRuntimeException(e);
        }

    }

    public void execute(JobContext context) {
        executeLoaders(context);
        executeValidators(context);
    }

    /**
     * todo: validator parallel unsupported now. WARNING!!!
     *
     * @param jobContext
     */
    protected void executeLoaders(JobContext jobContext) {
        List<Loader> _loaders = jobContext.getAllLoader();
        log.info("there are " + _loaders.size() + " loaders.");
        log.info("now all loaders will be started in " + this.count + " threads.");
        this.loaders.addAll(_loaders);
        JobExecutor executor = new JobExecutor();
        for (int i = 0; i < this.count; i++) {

            for (Loader loader = getLoader(); loader != null; loader = getLoader()) {
                final Loader taskLoader = loader;
                executorService.execute(() -> {
                    executor.execute(jobContext, taskLoader);
                });
            }


            try {
                executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    protected void executeValidators(JobContext jobContext) {
        List<Validator> _loaders = jobContext.getAllValidators();
        log.info("there are " + _loaders.size() + " validators.");
        log.info("now all validators will be started in " + this.count + " threads.");
        this.validators.addAll(_loaders);
        JobExecutor executor = new JobExecutor();
        for (int i = 0; i < this.count; i++) {

            for (Validator validator = getValidator(); validator != null; validator = getValidator()) {
                final Validator taskValidator = validator;
                executorService.execute(() -> {
                    executor.execute(jobContext, taskValidator);
                });
            }


            try {
                executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    protected Loader getLoader() {
        return this.loaders.poll();
    }

    private Queue<Loader> loaders = new ConcurrentLinkedQueue<>();

    protected Validator getValidator(){
        return this.validators.poll();
    }

    private Queue<Validator> validators = new ConcurrentLinkedQueue<>();
}
