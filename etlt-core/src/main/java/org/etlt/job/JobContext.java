package org.etlt.job;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.etlt.EtltRuntimeException;
import org.etlt.SettingReader;
import org.etlt.expression.VariableContext;
import org.etlt.expression.datameta.Variable;
import org.etlt.extract.*;
import org.etlt.load.BundleLoaderSetting;
import org.etlt.load.Loader;
import org.etlt.load.LoaderFactory;
import org.etlt.load.LoaderSetting;
import org.etlt.validate.BundleValidatorSetting;
import org.etlt.validate.Validator;
import org.etlt.validate.ValidatorFactory;
import org.etlt.validate.ValidatorSetting;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * this class is not thread safe, when it is used in many threads, copy it firstly
 * <p>Usage:</p>
 * 1)JobContext context = new JobContext();<br>
 * 2)context.init()<br>
 */
public class JobContext implements VariableContext {

    public static final String JOB_SETTING = "job.json";

    public static final String ALL = "*";

    public static final String EXTRACTOR_SUFFIX = ".ext";
    private static final String LOADER_SUFFIX = ".ldr";
    private static final String VALIDATOR_SUFFIX = ".vld";
    /**
     * define many extractors
     */
    public static final String BUNDLE_EXTRACTOR_SUFFIX = ".exts";
    /**
     * define many loaders
     */
    private static final String BUNDLE_LOADER_SUFFIX = ".ldrs";

    /**
     * define many loaders
     */
    private static final String BUNDLE_VALIDATOR_SUFFIX = ".vlds";
    /**
     *
     */
    private Map<String, Entity> entityContainer = new HashMap<>();

    private final Map<String, Object> resourceContainer = new ConcurrentHashMap<>();

    private File contextRoot;

    private JobSetting jobSetting;

    private final SettingReader reader = new SettingReader();

    private final Map<String, Extractor> extractors = new HashMap<>();

    private final Map<String, Loader> loaders = new HashMap<>();

    private final Map<String, Validator> validators = new HashMap<>();

    private Map<String, Object> mapping = new HashMap<>();

    public JobContext(File contextRoot) throws IOException {
        this.contextRoot = contextRoot;
        this.jobSetting = this.reader.read(new File(this.contextRoot, JOB_SETTING), JobSetting.class);
    }

    private JobContext() {
    }

    public JobContext copy() {
        JobContext jobContext = new JobContext();
        jobContext.contextRoot = this.contextRoot;
        jobContext.jobSetting = this.jobSetting;
        jobContext.extractors.putAll(this.extractors);
        jobContext.loaders.putAll(this.loaders);
        jobContext.validators.putAll(this.validators);
        jobContext.mapping.putAll(this.mapping);
        jobContext.resourceContainer.putAll(this.resourceContainer);
        /**
         * entity container should be initialized
         */
        jobContext.entityContainer = new HashMap<>();
        return jobContext;
    }

    public JobSetting getJobSetting() {
        return jobSetting;
    }

    public File getContextRoot() {
        return contextRoot;
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
     * @param catalog catalog
     * @param key     key
     * @return mapping result
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
     * read a job setting {@link JobSetting} from job.json file located in {@link #contextRoot}
     */
    public void init() throws IOException {
        initResources();
        /**
         * init all extractors
         */
        initExtractors();
        initBundleExtractors();
        /**
         * init all loaders
         */
        initLoaders();
        initBundleLoaders();
        /**
         * init all validators
         */
        initValidators();
        initBundleValidators();
        /**
         * init mapping
         */
        initMapping();
    }

    private void addExtractor(Extractor extractor) {
        if (this.extractors.containsKey(extractor.getName()))
            throw new EtltRuntimeException("duplicated extractor found: " + extractor.getName());
        List<String> extractorNames = this.jobSetting.getExtractors();
        if (extractorNames.contains(ALL) || extractorNames.contains(extractor.getName()))
            this.extractors.put(extractor.getName(), extractor);
    }

    private void addLoader(Loader loader) {
        if (this.loaders.containsKey(loader.getName()))
            throw new EtltRuntimeException("duplicated loader found: " + loader.getName());
        List<String> loaderNames = this.jobSetting.getLoaders();
        if (loaderNames.contains(ALL) || loaderNames.contains(loader.getName()))
            this.loaders.put(loader.getName(), loader);
    }

    private void addValidator(Validator validator) {
        if (this.validators.containsKey(validator.getName()))
            throw new EtltRuntimeException("duplicated validator found: " + validator.getName());
        List<String> validatorNames = this.jobSetting.getValidators();
        if (validatorNames.contains(ALL) || validatorNames.contains(validator.getName()))
            this.validators.put(validator.getName(), validator);
    }

    /**
     * read all file with {@link #EXTRACTOR_SUFFIX}
     * <br>read all file with {@link #BUNDLE_EXTRACTOR_SUFFIX}
     *
     * @throws IOException
     */
    protected void initExtractors() throws IOException {
        String[] exts = getContextRoot().list((dir, name) ->
                name.endsWith(EXTRACTOR_SUFFIX)
        );
        List<Extractor> allExtractors = ExtractorFactory.getInstance().resolveInstances(exts, this);
        allExtractors.forEach((extractor -> addExtractor(extractor)));
    }

    protected void initBundleExtractors() throws IOException {
        String[] exts = getContextRoot().list((dir, name) ->
                name.endsWith(BUNDLE_EXTRACTOR_SUFFIX)
        );
        List<Extractor> allExtractors = ExtractorFactory.getInstance().resolveBundleInstances(exts, this);
        allExtractors.forEach(extractor -> addExtractor(extractor));
    }

    /**
     * read all file with {@link #LOADER_SUFFIX}
     * <br>read all file with {@link #BUNDLE_LOADER_SUFFIX}
     *
     * @throws IOException
     */
    protected void initLoaders() throws IOException {
        String[] loaders = getContextRoot().list((dir, name) ->
                name.endsWith(LOADER_SUFFIX)
        );
        List<Loader> allLoaders = LoaderFactory.getInstance().resolveInstances(loaders, this);
        allLoaders.forEach(loader -> addLoader(loader));
    }

    protected void initBundleLoaders() throws IOException {
        String[] loaderSettings = getContextRoot().list((dir, name) ->
                name.endsWith(BUNDLE_LOADER_SUFFIX)
        );
        List<Loader> allLoaders = LoaderFactory.getInstance().resolveBundleInstances(loaderSettings, this);
        allLoaders.forEach(loader -> addLoader(loader));
    }

    /**
     * read all file with {@link #VALIDATOR_SUFFIX}
     * <br>read all file with {@link #BUNDLE_VALIDATOR_SUFFIX}
     *
     * @throws IOException
     */
    protected void initValidators() throws IOException {
        String[] validators = getContextRoot().list((dir, name) ->
                name.endsWith(VALIDATOR_SUFFIX)
        );
        List<Validator> allValidators = ValidatorFactory.getInstance().resolveInstances(validators, this);
        allValidators.forEach(validator -> addValidator(validator));
    }

    protected void initBundleValidators() throws IOException {
        String[] validatorSettings = getContextRoot().list((dir, name) ->
                name.endsWith(BUNDLE_VALIDATOR_SUFFIX)
        );
        List<Validator> allValidators = ValidatorFactory.getInstance().resolveBundleInstances(validatorSettings, this);
        allValidators.forEach(validator -> addValidator(validator));
    }

    protected void initMapping() throws IOException {
        if (!ObjectUtils.isEmpty(this.jobSetting.getMapping()))
            this.mapping = reader.read(new File(this.getContextRoot(), this.jobSetting.getMapping()), Map.class);
    }

    protected void initResources() {
        List<ResourceSetting> resources = this.jobSetting.getResources();
        ResourceFactory resourceFactory = new ResourceFactory();
        resources.forEach((s) -> {
            Object resource = resourceFactory.createResource(s);
            if (this.resourceContainer.containsKey(s.getName()))
                throw new EtltRuntimeException("Duplicated resource name found: " + s.getName());
            this.resourceContainer.put(s.getName(), resource);
        });
    }


    public Extractor getExtractor(String name) {
        Extractor extractor = this.extractors.get(name);
        if (ObjectUtils.isEmpty(extractor))
            throw new EtltRuntimeException("extractor not found: " + name);
        return extractor.createInstance();
    }

    public Loader getLoader(String name) {
        return this.loaders.get(name);
    }

    public List<Loader> getAllLoader() {
        List<Loader> loaders = new ArrayList<Loader>(this.loaders.values());
        return loaders;
    }

    public List<Validator> getAllValidators() {
        List<Validator> validators = new ArrayList<Validator>(this.validators.values());
        return validators;
    }

    public Object getParameter(String name) {
        Map<String, Object> parameters = this.jobSetting.getParameters();
        if (parameters == null) {
            throw new EtltRuntimeException("no parameters set. ");
        }
        Object result = parameters.get(name);
        if (ObjectUtils.isEmpty(result))
            throw new EtltRuntimeException("parameter not found: " + name);
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
        throw new EtltRuntimeException("variable not found: " + name);
    }

    @SuppressWarnings("unchecked")
    public <T> T getResource(String name) {
        Object resource = this.resourceContainer.get(name);
        if (resource == null)
            throw new EtltRuntimeException("resource not found: " + name);
        return (T) resource;
    }

}
