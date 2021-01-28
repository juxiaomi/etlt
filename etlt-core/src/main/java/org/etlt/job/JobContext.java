package org.etlt.job;

import org.etlt.SettingReader;
import org.etlt.extract.Extractor;
import org.etlt.extract.ExtractorSetting;
import org.etlt.extract.FileExtractor;
import org.etlt.load.FileLoader;
import org.etlt.load.LoadSetting;
import org.etlt.load.Loader;
import org.etlt.expression.VariableContext;
import org.etlt.expression.datameta.Variable;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JobContext implements VariableContext {

    public static final String JOB_SETTING = "job.json";

    public static final String ALL = "*";

    public static final String EXTRACTOR_SUFFIX = ".ext";
    private static final String LOADER_SUFFIX = ".ldr";

    private final Map<String, Map<String, String>> data = new HashMap<>();

    private final File configDirectory;

    private JobSetting jobSetting;

    private SettingReader reader = new SettingReader();

    private Map<String, Extractor> extractors = new HashMap<>();

    private Map<String, Loader> loaders = new HashMap<>();

    private Map<String, Object> mapping = new HashMap<>();

    public JobContext(File configDirectory) {
        this.configDirectory = configDirectory;
    }

    public File getConfigDirectory() {
        return configDirectory;
    }

    public boolean isExist(String entity) {
        return this.data.containsKey(entity);
    }

    public String getValue(String entity, String column) {
        if (isExist(entity)) {
            return this.data.get(entity).get(column);
        }
        return null;
    }

    /**
     *
     * @param catalog
     * @param key
     * @return
     */
    public Object mapping(String catalog, String key) {
        if(StringUtils.isBlank(catalog))
            throw new IllegalArgumentException("catalog name of mapping cannot be blank.");
        if(StringUtils.isBlank(key))
            return key;
        Map cmap = ((Map) this.mapping.get(catalog));
        if(cmap == null)
            throw new IllegalArgumentException("catalog of mapping missing, check mapping configuration.");
        return cmap.get(key.trim());
    }

    public void setEntity(String entity, Map<String, String> data) {
        this.data.put(entity, data);
    }

    public void removeEntity(String entity) {
        this.data.remove(entity);
    }

    /**
     * read a job setting {@link JobSetting} from job.json file located in {@link #configDirectory}
     */
    public void init() throws IOException {
        this.jobSetting = this.reader.read(new File(this.configDirectory, JOB_SETTING), JobSetting.class);
        initExtractors();
        initLoaders();
        initMapping();
    }

    protected void initExtractors() throws IOException {
        List<String> extractorNames = this.jobSetting.getExtractors();
        List<Extractor> extractors = null;
        if (extractorNames.size() != 0) {
            if (extractorNames.get(0).equals(ALL)) {
                //read all
                String[] exts = configDirectory.list((dir, name) -> {
                    return name.endsWith(EXTRACTOR_SUFFIX);
                });
                extractors = readExtractors(exts);
            } else {
                extractors = readExtractors(this.jobSetting.getExtractors().toArray(new String[0]));
            }
        }
        for (Extractor extractor : extractors) {
            this.extractors.put(extractor.getName(), extractor);
        }
    }

    protected void initLoaders() throws IOException {
        List<String> loaderNames = this.jobSetting.getLoaders();
        List<Loader> extractors = null;
        if (loaderNames.size() != 0) {
            if (loaderNames.get(0).equals(ALL)) {
                //read all
                String[] exts = configDirectory.list((dir, name) -> {
                    return name.endsWith(LOADER_SUFFIX);
                });
                extractors = readLoaders(exts);
            } else {
                extractors = readLoaders(this.jobSetting.getExtractors().toArray(new String[0]));
            }
        }
        for (Loader loader : extractors) {
            this.loaders.put(loader.getName(), loader);
        }
    }

    protected void initMapping() throws IOException {
        if (this.jobSetting.getMapping() != null)
            this.mapping = reader.read(new File(this.configDirectory, this.jobSetting.getMapping()), Map.class);
    }

    protected List<Extractor> readExtractors(String[] extractSettings) throws IOException {
        List<Extractor> extractors = new ArrayList<>();
        for (String ext : extractSettings) {
            extractors.add(readExtractor(ext + EXTRACTOR_SUFFIX));
        }
        return extractors;
    }

    protected FileExtractor readExtractor(String extractSetting) throws IOException {
        ExtractorSetting extractorSetting = this.reader.read(new File(this.configDirectory, extractSetting), ExtractorSetting.class);
        return new FileExtractor(extractorSetting);
    }

    protected List<Loader> readLoaders(String[] loaderSettings) throws IOException {
        List<Loader> extractors = new ArrayList<>();
        for (String ext : loaderSettings) {
            if (ext.endsWith(LOADER_SUFFIX))
                extractors.add(readLoader(ext));
            else
                extractors.add(readLoader(ext + LOADER_SUFFIX));
        }
        return extractors;
    }

    protected FileLoader readLoader(String loadSetting) throws IOException {
        LoadSetting loadSetting1 = this.reader.read(new File(this.configDirectory, loadSetting), LoadSetting.class);
        return new FileLoader(loadSetting1);
    }

    public Extractor getExtractor(String name) {
        return this.extractors.get(name);
    }

    public Loader getLoader(String name) {
        return this.loaders.get(name);
    }

    public List<Loader> getAllLoader() {
        return new ArrayList<Loader>(this.loaders.values());
    }

    /**
     * @param name entity.column
     * @return
     */
    @Override
    public Variable getVariable(String name) {
        int index = name.indexOf('.');
        if(index > 0){
            String catalog = name.substring(0, index);
            String key = name.substring(index + 1);
            return Variable.createVariable(name, getValue(catalog, key));
        }
        throw new UnsupportedOperationException("unsupported name: " + name);
    }
}
