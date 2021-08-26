package org.etlt.extract;

import org.etlt.SettingReader;
import org.etlt.job.JobContext;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExtractorFactory {

    private static final ExtractorFactory EXTRACTOR_FACTORY = new ExtractorFactory();

    private ExtractorFactory(){}

    public static ExtractorFactory getInstance(){
        return EXTRACTOR_FACTORY;
    }

    private final SettingReader reader = new SettingReader();

    public List<Extractor> resolveInstances(String[] settingFiles, JobContext context) throws IOException {
        List<Extractor> extractors = new ArrayList<>();
        for (String ext : settingFiles) {
            extractors.add(resolveInstance(ext, context));
        }
        return extractors;
    }

    public Extractor resolveInstance(String settingFile, JobContext context) throws IOException {
        ExtractorSetting extractorSetting = this.reader.read(new File(context.getContextRoot(), settingFile), ExtractorSetting.class);
        return createExtractor(extractorSetting, context);
    }

    public List<Extractor> resolveBundleInstances(String[] settingFiles, JobContext context) throws IOException {
        List<Extractor> extractors = new ArrayList<>();
        for (String ext : settingFiles) {
            extractors.addAll(resolveBundleInstance(ext, context));
        }
        return extractors;
    }

    public List<Extractor> resolveBundleInstance(String settingFile, JobContext context) throws IOException {
        BundleExtractorSetting extractorBundleSetting = this.reader.read(new File(context.getContextRoot(), settingFile), BundleExtractorSetting.class);
        List<ExtractorSetting> extractorSettings = extractorBundleSetting.createExtractorSetting();
        List<Extractor> extractors = new ArrayList<>(extractorSettings.size());
        extractorSettings.forEach((setting) -> {
            extractors.add(createExtractor(setting, context));
        });
        return extractors;
    }

    public Extractor createExtractor(ExtractorSetting setting, JobContext context){
        if(setting instanceof FileExtractSetting){
            FileExtractor extractor = new FileExtractor((FileExtractSetting)setting);
            extractor.init(context);
            return  extractor;
        }else if(setting instanceof DatabaseExtractSetting){
            DatabaseExtractor extractor = new DatabaseExtractor((DatabaseExtractSetting) setting);
            extractor.init(context);
            return  extractor;
        }
        throw new IllegalArgumentException("unsupported extractor setting: " + setting.getName());
    }
}
