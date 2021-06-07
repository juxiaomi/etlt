package org.etlt;

public interface SettingCheck {

    void check();

    default void assertNotNull(String message, Object... objs) {
        boolean isNull = true;
        for (Object obj : objs) {
            isNull = (obj == null);
            if (!isNull)
                break;
        }
        if (isNull)
            throw new IllegalArgumentException("unsupported null object: " + message);
    }

    default void assertNotNull(String message, Object obj) {
        if (obj == null)
            throw new IllegalArgumentException("unsupported null object: " + message);
    }

    default void assertCondition(String message, boolean condition){
        if(!condition){
            throw new IllegalArgumentException("condition should be true: " + message);
        }
    }
}
