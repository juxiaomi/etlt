package org.etlt.expression.function;

import java.sql.Timestamp;
import java.util.Date;

/**
 * system default functions for Date
 * @version 2.0
 */
public class DateFunctions {

    @FunctionEnabled(value = "DATE", help = "get current date")
    public Object date(){
        return new Date();
    }

    @FunctionEnabled(value = "current_timestamp", help = "get current timestamp")
    public Object timestamp(){
        return new Timestamp(System.currentTimeMillis());
    }
}
