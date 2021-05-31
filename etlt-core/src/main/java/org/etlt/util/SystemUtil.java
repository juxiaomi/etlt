package org.etlt.util;

public class SystemUtil {

    public static int getCpuCount(){
        return Runtime.getRuntime().availableProcessors();
    }
}
