package org.etlt.loader;

import org.etlt.Constants;
import org.etlt.SettingReader;
import org.etlt.job.JobContext;
import org.etlt.load.FileLoader;
import org.etlt.load.FileLoaderSetting;
import org.etlt.load.Loader;
import org.etlt.load.LoaderSetting;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class TestFileLoader {
    SettingReader settingReader = new SettingReader();

    String settingPath = Constants.CONFIG_DIRECTORY + File.separator + "client.ldr";

    Loader loader;

    JobContext context;

    @Before
    public void init() throws IOException {
        File file = new File(settingPath);
        if (file.exists()) {
            LoaderSetting setting = settingReader.read(settingPath, LoaderSetting.class);
            loader = new FileLoader((FileLoaderSetting) setting);
        }
        context = new JobContext(new File(Constants.CONFIG_DIRECTORY));
        context.init();
    }
    @Test
    public void testCreate(){
        Assert.assertNotNull(loader);
        loader.preLoad(context);
        loader.load(context);
        loader.doFinish();
    }
}
