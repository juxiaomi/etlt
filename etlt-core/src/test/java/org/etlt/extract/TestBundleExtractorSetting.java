package org.etlt.extract;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestBundleExtractorSetting {

    BundleExtractorSetting extractorBundleSetting = new BundleExtractorSetting();

    @Before
    public void prepare() {
        Map properties = new HashMap();
        properties.put("type", "DATA_BASE");
        properties.put("skip", new Integer(0));
        properties.put("datasource", "h2datasource");
        extractorBundleSetting.setProperties(properties);

        List<Map> extractors = new ArrayList<>();
        Map extractor = new HashMap();
        extractor.put("name", "client_db");
        properties.put("skip", new Integer(1));
        extractor.put("dql", "SELECT * FROM T_PROPS");
        extractors.add(extractor);

        extractor = new HashMap();
        extractor.put("name", "client_db_1");
        extractor.put("dql", "SELECT * FROM T_PROPS_1");
        extractors.add(extractor);

        extractorBundleSetting.setExtractors(extractors);
    }

    @Test
    public void testCreate() {
        List<ExtractorSetting> extractorSettings =
                extractorBundleSetting.createExtractorSetting();
        Assert.assertNotNull(extractorSettings);
        Assert.assertEquals(2, extractorSettings.size());
        ExtractorSetting extractorSetting = extractorSettings.get(0);
        Assert.assertEquals(DatabaseExtractSetting.class, extractorSetting.getClass());
        Assert.assertEquals(1, extractorSetting.getSkip());
    }
}
