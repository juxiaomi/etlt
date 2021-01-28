package org.etlt.job;

import org.etlt.Constants;
import org.etlt.extract.Extractor;
import org.etlt.job.JobContext;
import org.etlt.load.Loader;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class TestJobContext {


    JobContext context;

    @Before
    public void init() throws IOException {
        File file = new File(Constants.CONFIG_DIRECTORY);
        context = new JobContext(file);
        context.init();
    }

    @Test
    public void testExtractors() {
        Extractor clientExtractor =
                context.getExtractor("client");
        Assert.assertEquals("client", clientExtractor.getName());
    }

    @Test
    public void testLoaders() {
        Loader clientLoader =
                context.getLoader("client");
        Assert.assertEquals("client", clientLoader.getName());
    }

}
