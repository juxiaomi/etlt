package org.etlt.validator;

import org.etlt.Main;
import org.etlt.job.JobContext;
import org.etlt.job.JobExecutor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class ValidatorTest {

    JobContext context = null;

    @Before
    public void init() throws IOException {
        context = new JobContext(new File("../config.validator"));
        context.init();
        System.setProperty(Main.JOB_INVENTORY, context.getContextRoot().getAbsolutePath());
    }

    @Test
    public void testInit(){
        Assert.assertNotNull(context);
        Assert.assertEquals(1,context.getAllValidators().size());
    }

    @Test
    public void testRun(){
        JobExecutor executor = new JobExecutor();
        executor.execute(context);
    }
}
