package org.etlt.tool.db.setting;

import java.util.Map;

public class SupportedDbSetting {
    private Map<String, String > supportedDatabases ;

    public String getDriver(String type){
        return this.supportedDatabases.get(type);
    }

    public Map<String, String> getSupportedDatabases() {
        return supportedDatabases;
    }

    public void setSupportedDatabases(Map<String, String> supportedDatabases) {
        this.supportedDatabases = supportedDatabases;
    }
}
