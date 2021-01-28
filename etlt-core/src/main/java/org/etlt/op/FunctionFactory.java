package org.etlt.op;

import org.etlt.SettingReader;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * 1)read built-in function description
 * <br>
 * 2)read extended functions description
 */
class FunctionFactoryV1 {

    private static FunctionFactoryV1 FUNCTION_FACTORY;

    static {
        try {
            FUNCTION_FACTORY = new FunctionFactoryV1();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private FunctionSetting setting;

    private FunctionFactoryV1() throws IOException {
        SettingReader reader = new SettingReader();
        this.setting = reader.read(getClass().getClassLoader().getResourceAsStream("function.json"), FunctionSetting.class);
    }

    public static FunctionFactoryV1 getInstance(){
        return FUNCTION_FACTORY;
    }

    public Function getFunction(String fname) {
        try {
            Class clazz = this.setting.getBuiltIn().get(fname);
            if (clazz != null) {
                return (Function) clazz.getDeclaredConstructor(null).newInstance();
            }
            throw new IllegalArgumentException("undefined function: " + fname);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            throw new IllegalArgumentException(e);
        }
    }

}
