package org.etlt.expression.function;

import java.util.Date;

/**
 * system default functions for Date
 * @version 2.0
 */
public class DateFunctions {

    @FunctionEnabled("DATE")
    public Object date(){
        return new Date();
    }
}
