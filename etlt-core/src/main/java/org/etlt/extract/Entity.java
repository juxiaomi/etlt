package org.etlt.extract;

import java.io.Serializable;
import java.util.Map;

public class Entity implements Serializable {
    private final int index;

    private final Map<String, Object> data;

    public Entity(int index, Map<String, Object> data) {
        this.index = index;
        this.data = data;
    }

    public Object getValue(String name){
        return data.get(name);
    }
}
