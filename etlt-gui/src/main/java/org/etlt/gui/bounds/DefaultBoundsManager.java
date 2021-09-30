package org.etlt.gui.bounds;

import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DefaultBoundsManager extends BoundsManager {

    private Map<String, Object> bounds = new HashMap<>();

    public DefaultBoundsManager() {
        SettingReader reader = new SettingReader();
        try {
            Map<String, Object> boundsDefs =
                    reader.read(getClass().getResourceAsStream("/bounds/definition.json"), Map.class);
            this.bounds.putAll(boundsDefs);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public Rectangle getRectangle(String ... componentNames) {
        Map subBoundsMap = this.bounds;
        String name = null;
        for(int i = 0; i < componentNames.length; i++){
            name = componentNames[i];
            Object subBounds = subBoundsMap.get(name);
            if(i != componentNames.length - 1){
                subBoundsMap = (Map) subBounds;
            }
        }
        return fromString(subBoundsMap.get(name).toString());
    }

    protected Rectangle fromString(String rectangle) {
        String[] recs = rectangle.split(",");
        if (recs.length != 4) {
            throw new IllegalArgumentException();
        }
        return new Rectangle(Integer.parseInt(recs[0]),
                Integer.parseInt(recs[1]),
                Integer.parseInt(recs[2]),
                Integer.parseInt(recs[3]));
    }
}
