package org.etlt.extract;

import org.etlt.Constants;
import org.etlt.SettingReader;
import org.etlt.job.JobContext;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class TestFileExtractor {

    SettingReader settingReader = new SettingReader();

    String settingPath = Constants.CONFIG_DIRECTORY + File.separator + "client.ext";

    Extractor extractor;

    JobContext context;
    @Before
    public void init() throws IOException {
        File file = new File(settingPath);
        if (file.exists()) {
            ExtractorSetting setting = settingReader.read(settingPath, ExtractorSetting.class);
            extractor = new FileExtractor((FileExtractSetting) setting);
        }
        context = new JobContext(new File(Constants.CONFIG_DIRECTORY));
        context.init();
    }

    @Test
    public void testExtract() {
        Assert.assertNotNull(this.extractor);
        extractor.init(context);
        extractor.extract(context);
        Assert.assertEquals("client_id", context.getValue("client", "client_id"));
    }
}
