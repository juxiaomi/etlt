package org.etlt.tool.db.setting;

public class DdlSetting {
    private String descFile;
    private String ddlFile;
    private boolean execute;
    private boolean writeDdl;

    public String getDescFile() {
        return descFile;
    }

    public void setDescFile(String descFile) {
        this.descFile = descFile;
    }

    public String getDdlFile() {
        return ddlFile;
    }

    public void setDdlFile(String ddlFile) {
        this.ddlFile = ddlFile;
    }

    public boolean isExecute() {
        return execute;
    }

    public void setExecute(boolean execute) {
        this.execute = execute;
    }

    public boolean isWriteDdl() {
        return writeDdl;
    }

    public void setWriteDdl(boolean writeDdl) {
        this.writeDdl = writeDdl;
    }
}
