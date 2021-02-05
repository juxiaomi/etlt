package org.etlt.job;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.etlt.EtltException;
import org.etlt.SettingReader;
import org.etlt.expression.VariableContext;
import org.etlt.expression.datameta.Variable;
import org.etlt.extract.Extractor;
import org.etlt.extract.ExtractorSetting;
import org.etlt.load.Loader;
import org.etlt.load.LoaderSetting;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JobContext implements VariableContext {

    public static final String JOB_SETTING = "job.json";

    public static final String ALL = "*";

    public static final String EXTRACTOR_SUFFIX = ".ext";
    private static final String LOADER_SUFFIX = ".ldr";

    private final Map<String, Map<String, Object>> data = new HashMap<>();

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

    public Object getValue(String entity, String column) {
        if (isExist(entity)) {
            return this.data.get(entity).get(column);
        }
        return null;
    }

    /**
     * @param catalog
     * @param key
     * @return
     */
    public Object mapping(String catalog, String key) {
        if (StringUtils.isBlank(catalog))
            throw new IllegalArgumentException("catalog name of mapping cannot be blank.");
        if (StringUtils.isBlank(key))
            return key;
        Map cmap = ((Map) this.mapping.get(catalog));
        if (ObjectUtils.isEmpty(cmap) )
            throw new IllegalArgumentException("catalog of mapping missing, check mapping configuration.");
        return cmap.get(key.trim());
    }

    public void setEntity(String entity, Map<String, Object> data) {
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
        String[] exts = configDirectory.list((dir, name) ->
                name.endsWith(EXTRACTOR_SUFFIX)
        );
        List<Extractor> allExtractors = readExtractors(exts);
        if (extractorNames.size() != 0) {
            if (!extractorNames.get(0).equals(ALL)) {
                allExtractors = allExtractors.stream().filter(e ->
                        extractorNames.contains(e.getName())
                ).collect(Collectors.toList());
            }
        }
        for (Extractor extractor : allExtractors) {
            this.extractors.put(extractor.getName(), extractor);
        }
    }

    protected void initLoaders() throws IOException {
        List<String> loaderNames = this.jobSetting.getLoaders();
        String[] loaders = configDirectory.list((dir, name) ->
                name.endsWith(LOADER_SUFFIX)
        );
        List<Loader> allLoaders = readLoaders(loaders);
        if (loaderNames.size() != 0) {
            if (!loaderNames.get(0).equals(ALL)) {
                //read all
                allLoaders = allLoaders.stream().filter(e ->
                        loaderNames.contains(e.getName())
                ).collect(Collectors.toList());
            }
        }
        for (Loader loader : allLoaders) {
            this.loaders.put(loader.getName(), loader);
        }
    }

    protected void initMapping() throws IOException {
        if (!ObjectUtils.isEmpty(this.jobSetting.getMapping()))
            this.mapping = reader.read(new File(this.configDirectory, this.jobSetting.getMapping()), Map.class);
    }

    protected List<Extractor> readExtractors(String[] extractSettings) throws IOException {
        List<Extractor> extractors = new ArrayList<>();
        for (String ext : extractSettings) {
            extractors.add(readExtractor(ext));
        }
        return extractors;
    }

    protected Extractor readExtractor(String extractSetting) throws IOException {
        ExtractorSetting extractorSetting = this.reader.read(new File(this.configDirectory, extractSetting), ExtractorSetting.class);
        return Extractor.createExtractor(extractorSetting, this);
    }

    protected List<Loader> readLoaders(String[] loaderSettings) throws IOException {
        List<Loader> extractors = new ArrayList<>();
        for (String ext : loaderSettings) {
            extractors.add(readLoader(ext));
        }
        return extractors;
    }

    protected Loader readLoader(String loadSetting) throws IOException {
        LoaderSetting loadSetting1 = this.reader.read(new File(this.configDirectory, loadSetting), LoaderSetting.class);
        return Loader.createLoader(loadSetting1);
    }

    public Extractor getExtractor(String name) {
        Extractor extractor = this.extractors.get(name);
        if (ObjectUtils.isEmpty(extractor))
            throw new EtltException("extractor not found: " + name);
        return extractor;
    }

    public Loader getLoader(String name) {
        return this.loaders.get(name);
    }

    public List<Loader> getAllLoader() {
        return new ArrayList<Loader>(this.loaders.values());
    }

    public Object getParameter(String name) {
        Object result = this.jobSetting.getParameters().get(name);
        if (ObjectUtils.isEmpty(result))
            throw new IllegalArgumentException("parameter not found: " + name);
        return result;
    }

    /**
     * @param name entity.column
     * @return
     */
    @Override
    public Variable getVariable(String name) {
        int index = name.indexOf('.');
        if (index > 0) {
            String catalog = name.substring(0, index);
            String key = name.substring(index + 1);
            return Variable.createVariable(name, getValue(catalog, key));
        }
        throw new IllegalArgumentException("variable not found: " + name);
    }
}
