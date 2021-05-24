package org.etlt.job;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.etlt.EtltException;
import org.etlt.load.Loader;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JobExecutor {

    protected Log log = LogFactory.getLog(getClass());

    public void execute(File jobDirectory){
        try {
            JobContext jobContext = new JobContext(jobDirectory);
            jobContext.init();
            List<Loader> loaders = jobContext.getAllLoader();
            log.info("there are " + loaders.size() + " loaders.");
            for(Loader loader: loaders){
                log.info("loader " + loader.getName() + " starting...");
                loader.preLoad(jobContext);
                loader.load(jobContext);
                loader.doFinish();
                log.info("loader " + loader.getName() + " finished");
            }
        }catch (IOException e){
            throw new EtltException("job executing error." ,e );
        }
    }
}
