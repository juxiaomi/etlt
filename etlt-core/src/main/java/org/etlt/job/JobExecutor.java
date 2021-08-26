package org.etlt.job;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.etlt.EtltException;
import org.etlt.EtltRuntimeException;
import org.etlt.load.Loader;
import org.etlt.validate.Validator;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JobExecutor {

    protected Log log = LogFactory.getLog(getClass());

    public void execute(File jobDirectory) {
        try {
            JobContext jobContext = new JobContext(jobDirectory);
            execute(jobContext);
        } catch (IOException e) {
            throw new EtltRuntimeException("job executing error.", e);
        }
    }

    public void execute(JobContext context) {
        /**
         * execute all loaders
         */
        List<Loader> loaders = context.getAllLoader();
        log.info("there are " + loaders.size() + " loaders.");
        for (int i = 0; i < loaders.size(); i++) {
            Loader loader = loaders.get(i);
            log.info((i + 1) + "/" + loaders.size() + ", loader " + loader.getName() + " starting...");
            loader.preLoad(context);
            loader.load(context);
            loader.doFinish();
            log.info((i + 1) + "/" + loaders.size() + ", loader " + loader.getName() + " finished");
        }
        /**
         * execute all validators
         * todo
         */
        List<Validator> validators = context.getAllValidators();
        log.info("there are " + validators.size() + " validators.");
        //todo
        validators.forEach(validator -> {
            validator.validate(context);
        });
    }

    /**
     * execute designated loader
     *
     * @param loader
     */
    public void execute(JobContext jobContext, Loader loader) {
        log.info("loader " + loader.getName() + " starting...");
        loader.preLoad(jobContext);
        loader.load(jobContext);
        loader.doFinish();
        log.info("loader " + loader.getName() + " finished");
    }
}
