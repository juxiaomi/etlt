/**
 *
 */
package org.etlt.expression.function;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


/**
 * system default functions for String
 * @version 2.0
 */
public class StringFunctions {

    /**
     * concat all strings
     * @param s1
     * @return
     */
    @FunctionEnabled("CONCAT0")
    public String concat(String s1, String s2) {
        StringBuilder sb = new StringBuilder();
        sb.append(s1).append(s2);
        return sb.toString();
    }

    /**
     * String prefix comparison
     * @param str1
     * @param str2
     * @return
     */
    @FunctionEnabled("STARTSWITH")
    public boolean startsWith(String str1, String str2) {
        if (str1 == null || str2 == null) {
            throw new NullPointerException("函数\"STARTSWITH\"参数为空");
        }
        return str1.startsWith(str2);
    }

    /**
     * String suffix comparison
     * @param str1
     * @param str2
     * @return
     */
    @FunctionEnabled("ENDSWITH")
    public boolean endsWith(String str1, String str2) {
        if (str1 == null || str2 == null) {
            throw new NullPointerException("函数\"ENDSWITH\"参数为空");
        }
        return str1.endsWith(str2);
    }

    /**
     * 日期计算
     * @param date 原始的日期
     * @param years 年份偏移量
     * @param months 月偏移量
     * @param days 日偏移量
     * @param hours 小时偏移量
     * @param minutes 分偏移量
     * @param seconds 秒偏移量
     * @return 偏移后的日期
     */
    @FunctionEnabled("CALCDATE")
    public Date calcDate(Date date, int years, int months, int days, int hours, int minutes, int seconds) {
        if (date == null) {
            throw new NullPointerException("函数\"CALCDATE\"参数为空");
        }
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, years);
        calendar.add(Calendar.MONTH, months);
        calendar.add(Calendar.DAY_OF_MONTH, days);
        calendar.add(Calendar.HOUR, hours);
        calendar.add(Calendar.MINUTE, minutes);
        calendar.add(Calendar.SECOND, seconds);
        return calendar.getTime();
    }

    /**
     * 获取系统当前时间
     * @return
     */
    @FunctionEnabled("SYSDATE")
    public Date sysDate() {
        return new Date();
    }

    /**
     * 日期相等比较，精确到天
     * @param date1
     * @param date2
     * @return
     */
    @FunctionEnabled("DAYEQUALS")
    public boolean dayEquals(Date date1, Date date2) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dayOfDate1 = sdf.format(date1);
        String dayOfDate2 = sdf.format(date2);
        return dayOfDate1.equals(dayOfDate2);
    }

}
