package org.etlt;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class SettingReader {

    public <T> T read(File settingFile, Class<T> target)throws IOException{
        FileInputStream inputStream = new FileInputStream(settingFile);
        return read(inputStream, target);
    }

    public <T> T read(String settingFilePath, Class<T> target)throws IOException{
        FileInputStream inputStream = new FileInputStream(new File(settingFilePath));
        return read(inputStream, target);
    }

    public <T> T read(InputStream source, Class<T> target)throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        T object = objectMapper.readValue(source, target);
        if(object instanceof SettingCheck){
            ((SettingCheck)object).check();
        }
        return object;
    }

}
