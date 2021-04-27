package org.etlt.loader;

import org.etlt.extract.DatabaseExtractSetting;
import org.etlt.extract.ExtractorSetting;
import org.etlt.load.BundleLoaderSetting;
import org.etlt.load.DatabaseLoaderSetting;
import org.etlt.load.LoaderSetting;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestBundleLoaderSetting {
    BundleLoaderSetting bundleLoaderSetting = new BundleLoaderSetting();

    @Before
    public void prepare() {
        Map properties = new HashMap();
        properties.put("type", "DATA_BASE");
        properties.put("autoResolve", true);
        properties.put("datasourceRef", "h2datasource");
        bundleLoaderSetting.setProperties(properties);

        List<Map> loaders = new ArrayList<>();
        Map loader = new HashMap();
        loader.put("name", "t_props_1");
        List<String> extractors = new ArrayList<>();
        extractors.add("t_props");
        loader.put("extractors", extractors);
        loader.put("preDml", "TRUNCATE TABLE T_PROPS");
        loader.put("dml", "INSERT INTO T_PROPS VALUES(?, ?, ?, ?,?)");
        loaders.add(loader);

        loader = new HashMap();
        loader.put("name", "t_props_2");
        loader.put("extractors", extractors);
        loader.put("preDml", "TRUNCATE TABLE T_PROPS");
        loader.put("dml", "INSERT INTO T_PROPS VALUES(?, ?, ?, ?,?)");
        loaders.add(loader);

        bundleLoaderSetting.setLoaders(loaders);
    }

    @Test
    public void testCreate() {
        List<LoaderSetting> extractorSettings =
                bundleLoaderSetting.createLoaderSetting();
        Assert.assertNotNull(extractorSettings);
        Assert.assertEquals(2, extractorSettings.size());
        LoaderSetting loaderSetting = extractorSettings.get(0);
        Assert.assertEquals(DatabaseLoaderSetting.class, loaderSetting.getClass());
    }
}
