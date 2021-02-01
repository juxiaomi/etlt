package org.etlt.extract;

import org.etlt.Constants;
import org.etlt.SettingReader;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class TestFileExtractor {

    SettingReader settingReader = new SettingReader();

    String settingPath = Constants.CONFIG_DIRECTORY + File.separator + "client.ext";

    Extractor extractor;

    @Before
    public void init() throws IOException {
        File file = new File(settingPath);
        if (file.exists()) {
            ExtractorSetting setting = settingReader.read(settingPath, ExtractorSetting.class);
            extractor = new FileExtractor((FileExtractSetting) setting);
        }
    }

    @Test
    public void testExtract() {
        Assert.assertNotNull(this.extractor);
    }
}
