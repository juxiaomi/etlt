package org.etlt.extract;

import org.etlt.Constants;
import org.etlt.SettingReader;
import org.etlt.job.JobContext;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * if no data found in table (T_PROPS), use this sql: <br>
 *     INSERT INTO T_PROPS values('rov', 'enabled', 'true','undefined', CURRENT_TIMESTAMP);
 */
public class TestDbExtractor {

    SettingReader settingReader = new SettingReader();

    String settingPath = Constants.CONFIG_DIRECTORY + File.separator + "client_db.ext";

    Extractor extractor;

    JobContext context;

    @Before
    public void init() throws IOException {
        ExtractorSetting setting = settingReader.read(settingPath, ExtractorSetting.class);
        Assert.assertTrue(setting instanceof DatabaseExtractSetting);
        DatabaseExtractSetting databaseExtractSetting = (DatabaseExtractSetting)setting;
        extractor = new DatabaseExtractor((DatabaseExtractSetting) setting);
        context = new JobContext(new File(Constants.CONFIG_DIRECTORY));
        context.init();
    }

    @Test
    public void testExtract()  {
        Assert.assertNotNull(this.extractor);
        this.extractor.init(context);
        this.extractor.extract(context);
        Assert.assertEquals("rov", context.getValue("client_db", "PROP_SCOPE"));
    }
}
