package org.etlt.job;

import org.etlt.EtltException;
import org.etlt.load.Loader;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JobExecutor {
    public void execute(File jobDirectory){
        try {
            JobContext jobContext = new JobContext(jobDirectory);
            jobContext.init();
            List<Loader> loaders = jobContext.getAllLoader();
            for(Loader loader: loaders){
                loader.preLoad(jobContext);
                loader.load(jobContext);
                loader.doFinish();
            }
        }catch (IOException e){
            throw new EtltException("job executing error." ,e );
        }
    }
}
