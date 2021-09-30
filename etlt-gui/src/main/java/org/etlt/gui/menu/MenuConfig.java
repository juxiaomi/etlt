package org.etlt.gui.menu;

import java.util.List;

public class MenuConfig {
    private String name;

    private List<ItemConfig> items;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ItemConfig> getItems() {
        return items;
    }

    public void setItems(List<ItemConfig> items) {
        this.items = items;
    }
}
