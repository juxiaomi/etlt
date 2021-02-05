package org.etlt.loader;

import org.etlt.Constants;
import org.etlt.SettingReader;
import org.etlt.job.JobContext;
import org.etlt.load.Loader;
import org.etlt.load.LoaderSetting;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class TestDbLoader {
    SettingReader settingReader = new SettingReader();

    String settingPath = Constants.CONFIG_DIRECTORY + File.separator + "t_props_1.ldr";

    Loader loader;

    JobContext context;

    @Before
    public void init() throws IOException {
        LoaderSetting setting = settingReader.read(settingPath, LoaderSetting.class);
        context = new JobContext(new File(Constants.CONFIG_DIRECTORY));
        context.init();
        loader = Loader.createLoader(setting);
    }

    @Test
    public void testLoader(){
        Assert.assertNotNull(loader);
        loader.preLoad(context);
        loader.load(context);
        loader.doFinish();
    }
}
