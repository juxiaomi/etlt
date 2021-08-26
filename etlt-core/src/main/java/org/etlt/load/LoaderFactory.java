package org.etlt.load;

import org.etlt.SettingReader;
import org.etlt.job.JobContext;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoaderFactory {
    private static final LoaderFactory LOADER_FACTORY = new LoaderFactory();

    private LoaderFactory(){}

    public static LoaderFactory getInstance(){
        return LOADER_FACTORY;
    }

    private final SettingReader reader = new SettingReader();
    public List<Loader> resolveInstances(String[] settingFiles, JobContext context) throws IOException {
        List<Loader> loaders = new ArrayList<>();
        for (String ext : settingFiles) {
            loaders.add(resolveInstance(ext, context));
        }
        return loaders;
    }

    public Loader resolveInstance(String settingFile, JobContext context) throws IOException {
        LoaderSetting loaderSetting = this.reader.read(new File(context.getContextRoot(), settingFile), LoaderSetting.class);
        return createLoader(loaderSetting);
    }

    public List<Loader> resolveBundleInstances(String[] settingFiles, JobContext context) throws IOException {
        List<Loader> loaders = new ArrayList<>();
        for (String ext : settingFiles) {
            loaders.addAll(resolveBundleInstance(ext, context));
        }
        return loaders;
    }

    public List<Loader> resolveBundleInstance(String settingFile, JobContext context) throws IOException {
        BundleLoaderSetting bundleLoaderSetting = this.reader.read(new File(context.getContextRoot(), settingFile), BundleLoaderSetting.class);
        List<LoaderSetting> extractorSettings = bundleLoaderSetting.createLoaderSetting();
        List<Loader> loaders = new ArrayList<>(extractorSettings.size());
        extractorSettings.forEach((setting) -> {
            loaders.add(createLoader(setting));
        });
        return loaders;
    }

    public Loader createLoader(LoaderSetting setting) {
        if (setting instanceof FileLoaderSetting) {
            return new FileLoader((FileLoaderSetting) setting);
        } else if (setting instanceof DatabaseLoaderSetting) {
            return new DatabaseLoader((DatabaseLoaderSetting) setting);
        }
        throw new IllegalArgumentException("unsupported loader setting: " + setting.getName());
    }
}
