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
            jobContext.init();
            execute(jobContext);
        } catch (IOException e) {
            throw new EtltRuntimeException("job executing error.", e);
        }
    }

    public void execute(JobContext context) {
        log.info("start job executing.");
        /**
         * execute all loaders
         */
        List<Loader> loaders = context.getAllLoader();
        log.info("there are " + loaders.size() + " loaders.");
        for (int i = 0; i < loaders.size(); i++) {
            log.info("executing loader: " + (i+1) + "/" + loaders.size());
            Loader loader = loaders.get(i);
            execute(context, loader);
        }
        /**
         * execute all validators
         */
        List<Validator> validators = context.getAllValidators();
        log.info("there are " + validators.size() + " validators.");
        for(int i = 0; i < validators.size(); i++){
            log.info("executing validator: " + (i+1) + "/" + validators.size());
            Validator validator = validators.get(i);
            execute(context, validator);
        };
        log.info("job executing finished.");
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

    public void execute(JobContext context, Validator validator){
        log.info("validator " + validator.getName() + " starting...");
        validator.validate(context);
        log.info("validator " + validator.getName() + " finished");
    }
}
