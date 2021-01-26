package org.etlt.transform;

import java.util.Map;

public abstract class Transformer {
    abstract Map<String, String> transform(Map<String, String> stuff);

    abstract Map<String, String> transform(String condition, Map<String,String> ... stuff);
}
