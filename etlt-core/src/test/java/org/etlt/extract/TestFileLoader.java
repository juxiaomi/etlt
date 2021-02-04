package org.etlt.extract;

import org.etlt.Constants;
import org.etlt.SettingReader;
import org.etlt.load.FileLoader;
import org.etlt.load.FileLoaderSetting;
import org.etlt.load.LoadSetting;
import org.etlt.load.Loader;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class TestFileLoader {
    SettingReader settingReader = new SettingReader();

    String settingPath = Constants.CONFIG_DIRECTORY + File.separator + "client.ldr";

    Loader loader;

    @Before
    public void init() throws IOException {
        File file = new File(settingPath);
        if (file.exists()) {
            LoadSetting setting = settingReader.read(settingPath, LoadSetting.class);
            loader = new FileLoader((FileLoaderSetting) setting);
        }
    }
    @Test
    public void testCreate(){
        Assert.assertNotNull(loader);
    }
}
