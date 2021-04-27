package org.etlt.job;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.etlt.EtltException;
import org.etlt.EtltRuntimeException;
import org.etlt.SettingReader;
import org.etlt.expression.VariableContext;
import org.etlt.expression.datameta.Variable;
import org.etlt.extract.Entity;
import org.etlt.extract.Extractor;
import org.etlt.extract.BundleExtractorSetting;
import org.etlt.extract.ExtractorSetting;
import org.etlt.load.BundleLoaderSetting;
import org.etlt.load.Loader;
import org.etlt.load.LoaderSetting;

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
    /**
     * define many extractors
     */
    public static final String BUNDLE_EXTRACTOR_SUFFIX = ".exts";
    /**
     * define many loaders
     */
    private static final String BUNDLE_LOADER_SUFFIX = ".ldrs";

    private final Map<String, Entity> entityContainer = new HashMap<String, Entity>();

    private final File configDirectory;

    private JobSetting jobSetting;

    private SettingReader reader = new SettingReader();

    private final Map<String, Extractor> extractors = new HashMap<>();

    private final Map<String, Loader> loaders = new HashMap<>();

    private Map<String, Object> mapping = new HashMap<>();

    public JobContext(File configDirectory) {
        this.configDirectory = configDirectory;
    }

    public File getConfigDirectory() {
        return configDirectory;
    }

    public boolean isExist(String entity) {
        return this.entityContainer.containsKey(entity);
    }

    public Object getValue(String entity, String column) {
        if (isExist(entity)) {
            return this.entityContainer.get(entity).getValue(column);
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
        if (ObjectUtils.isEmpty(cmap))
            throw new IllegalArgumentException("catalog of mapping missing, check mapping configuration.");
        return cmap.get(key.trim());
    }

    public void setEntity(String entityName, Entity entity) {
        this.entityContainer.put(entityName, entity);
    }

    public void removeEntity(String entity) {
        this.entityContainer.remove(entity);
    }

    /**
     * read a job setting {@link JobSetting} from job.json file located in {@link #configDirectory}
     */
    public void init() throws IOException {
        this.jobSetting = this.reader.read(new File(this.configDirectory, JOB_SETTING), JobSetting.class);
        initExtractors();
        initBundleExtractors();
        initLoaders();
        initBundleLoaders();
        initMapping();
    }

    private void addExtractor(Extractor extractor){
        if(this.extractors.containsKey(extractor.getName()))
            throw new EtltRuntimeException("duplicated extractor found: " + extractor.getName());
        List<String> extractorNames = this.jobSetting.getExtractors();
        if(extractorNames.contains(ALL) || extractorNames.contains(extractor.getName()))
            this.extractors.put(extractor.getName(), extractor);
    }

    private void addLoader(Loader loader){
        if(this.loaders.containsKey(loader.getName()))
            throw new EtltRuntimeException("duplicated loader found: " + loader.getName());
        List<String> loaderNames = this.jobSetting.getLoaders();
        if(loaderNames.contains(ALL) || loaderNames.contains(loader.getName()))
            this.loaders.put(loader.getName(), loader);
    }
    /**
     * read all file with {@link #EXTRACTOR_SUFFIX}
     * <br>read all file with {@link #BUNDLE_EXTRACTOR_SUFFIX}
     *
     * @throws IOException
     */
    protected void initExtractors() throws IOException {
        List<String> extractorNames = this.jobSetting.getExtractors();
        String[] exts = configDirectory.list((dir, name) ->
                name.endsWith(EXTRACTOR_SUFFIX)
        );
        List<Extractor> allExtractors = readExtractors(exts);
        allExtractors.forEach((extractor -> addExtractor(extractor)));
    }

    protected void initBundleExtractors() throws IOException {
        List<String> extractorNames = this.jobSetting.getExtractors();
        String[] exts = configDirectory.list((dir, name) ->
                name.endsWith(BUNDLE_EXTRACTOR_SUFFIX)
        );
        List<Extractor> allExtractors = readBundleExtractors(exts);
        allExtractors.forEach(extractor -> addExtractor(extractor));
    }

    /**
     * read all file with {@link #LOADER_SUFFIX}
     * <br>read all file with {@link #BUNDLE_LOADER_SUFFIX}
     *
     * @throws IOException
     */
    protected void initLoaders() throws IOException {
        String[] loaders = configDirectory.list((dir, name) ->
                name.endsWith(LOADER_SUFFIX)
        );
        List<Loader> allLoaders = readLoaders(loaders);
        allLoaders.forEach(loader-> addLoader(loader));
    }

    protected void initBundleLoaders() throws IOException {
        String[] loaderSettings = configDirectory.list((dir, name) ->
                name.endsWith(BUNDLE_LOADER_SUFFIX)
        );
        List<Loader> allLoaders = readBundleLoaders(loaderSettings);
        allLoaders.forEach(loader-> addLoader(loader));
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

    protected List<Extractor> readBundleExtractors(String[] bundleExtractSettings) throws IOException {
        List<Extractor> extractors = new ArrayList<>();
        for (String ext : bundleExtractSettings) {
            extractors.addAll(readBundleExtractor(ext));
        }
        return extractors;
    }

    protected Extractor readExtractor(String extractSetting) throws IOException {
        ExtractorSetting extractorSetting = this.reader.read(new File(this.configDirectory, extractSetting), ExtractorSetting.class);
        return Extractor.createExtractor(extractorSetting, this);
    }

    /**
     * read extractors from extractor bundle setting
     * @param extractSetting
     * @return
     * @throws IOException
     */
    protected List<Extractor> readBundleExtractor(String extractSetting) throws IOException {
        BundleExtractorSetting extractorBundleSetting = this.reader.read(new File(this.configDirectory, extractSetting), BundleExtractorSetting.class);
        List<ExtractorSetting> extractorSettings = extractorBundleSetting.createExtractorSetting();
        List<Extractor> extractors = new ArrayList<>(extractorSettings.size());
        extractorSettings.forEach((setting)->{
            extractors.add(Extractor.createExtractor(setting, this));
        });
        return extractors;
    }

    protected List<Loader> readLoaders(String[] loaderSettings) throws IOException {
        List<Loader> loaders = new ArrayList<>();
        for (String ext : loaderSettings) {
            loaders.add(readLoader(ext));
        }
        return loaders;
    }

    protected List<Loader> readBundleLoaders(String[] loaderSettings) throws IOException {
        List<Loader> loaders = new ArrayList<>();
        for (String setting : loaderSettings) {
            loaders.addAll(readBundleLoader(setting));
        }
        return loaders;
    }

    protected Loader readLoader(String loadSetting) throws IOException {
        LoaderSetting loadSetting1 = this.reader.read(new File(this.configDirectory, loadSetting), LoaderSetting.class);
        return Loader.createLoader(loadSetting1);
    }

    protected List<Loader> readBundleLoader(String loadSetting) throws IOException {
        BundleLoaderSetting loadSetting1 = this.reader.read(new File(this.configDirectory, loadSetting), BundleLoaderSetting.class);
        List<LoaderSetting> loaderSettings = loadSetting1.createLoaderSetting();
        List<Loader> loaders = new ArrayList<>(loaderSettings.size());
        loaderSettings.forEach(ls -> loaders.add(Loader.createLoader(ls)));
        return loaders;
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
