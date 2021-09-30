package org.etlt.gui.bounds;


import java.awt.*;

public abstract class BoundsManager {

    private final static BoundsManager INSTANCE = new DefaultBoundsManager();

    public static BoundsManager getInstance() {
        return INSTANCE;
    }

    public abstract Rectangle getRectangle(String ... componentNames);

    public static Dimension getScreeSize() {
        return Toolkit.getDefaultToolkit().getScreenSize();
    }

    public static Point getComponentAnchor(Dimension dimension) {
        Dimension screenSize = getScreeSize();
        Point point = new Point();
        point.setLocation(screenSize.getWidth() / 2 - dimension.getWidth() / 2, screenSize.getHeight() / 2 - dimension.getHeight() / 2);
        return point;
    }
}
