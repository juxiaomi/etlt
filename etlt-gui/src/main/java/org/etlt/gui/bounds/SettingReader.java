package org.etlt.gui.bounds;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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
        return object;
    }

    public <T> List<T> readList(InputStream source, Class<T> target) throws IOException{
        ObjectMapper objectMapper = new ObjectMapper();
        List<Object> list = objectMapper.readValue(source, List.class);
        List<T> result = new ArrayList<>();
        list.forEach(item->{
            T object =  objectMapper.convertValue(item, target);
            result.add(object);
        });
        return result;
    }

}
