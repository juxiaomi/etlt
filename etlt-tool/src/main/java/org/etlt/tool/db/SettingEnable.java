package org.etlt.tool.db;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class SettingEnable {

    public File getConfigDir() {
        return configDir;
    }

    private final File configDir;

    protected SettingEnable(String configDir) {
        this.configDir = new File(configDir);
    }

    protected <T> T readSetting(String source, Class<T> target) throws IOException {
        return readSetting(new FileInputStream(new File(configDir, source)), target);
    }

    protected <T> T readSetting(FileInputStream source, Class<T> target) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(source, target);
    }

}
