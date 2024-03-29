/**
 *
 */
package org.etlt.expression.function;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


/**
 * system default functions for String
 * @version 2.0
 */
public class StringFunctions {

    @FunctionEnabled(value = "lower", help = "return lower of input string")
    public String lower(String arg){
        return arg.toLowerCase();
    }

    @FunctionEnabled("upper")
    public String upper(String arg){
        return arg.toUpperCase();
    }
    /**
     * concat all strings
     * @return
     */
    @FunctionEnabled("CONCAT")
    public String concat(Object... args) {
        if (args.length < 2) {
            throw new IllegalArgumentException("concat need at least 2 arguments.");
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (Object object : args) {
            stringBuilder.append(String.valueOf(object));
        }
        return stringBuilder.toString();
    }

    @FunctionEnabled("subString")
    public String subString(Object... args) {
        String s1 = args[0].toString();
        int from = Integer.parseInt(args[1].toString());
        if(args.length > 2){
            int to = Integer.parseInt(args[2].toString());
            return s1.substring(from, to);
        }else
            return s1.substring(from);
    }

    /**
     * String prefix comparison
     * @param str1
     * @param str2
     * @return
     */
    @FunctionEnabled(value = "STARTSWITH", help = "return true: first string starts with second string.")
    public boolean startsWith(String str1, String str2) {
        return str1.startsWith(str2);
    }

    /**
     * String suffix comparison
     * @param str1
     * @param str2
     * @return
     */
    @FunctionEnabled(value = "ENDSWITH", help = "return true: first string ends with second string.")
    public boolean endsWith(String str1, String str2) {
        return str1.endsWith(str2);
    }

    @FunctionEnabled(value = "LENGTH", help = "calculate the length of a String, 0 for null")
    public int getLength(String str){
        return StringUtils.isBlank(str) ? 0 : str.length();
    }
}
