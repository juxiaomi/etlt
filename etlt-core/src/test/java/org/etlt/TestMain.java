package org.etlt;


import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class TestMain {
    public static void main(String[] args) throws IOException, ParseException {
        SimpleDateFormat df =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        df.setTimeZone(TimeZone.getTimeZone("GMT+3"));
        Date date = df.parse("2021-06-29 18:00:00.307");

        Calendar calendar =
                Calendar.getInstance();
//                Calendar.getInstance(Locale.CHINA);
//                  Calendar.getInstance(TimeZone.getTimeZone("GMT+3"));
        calendar.setTimeInMillis(date.getTime());

        Timestamp timestamp1 = new Timestamp(calendar.getTime().getTime());
        System.out.println(timestamp1);

        Timestamp timestamp2 = new Timestamp(date.getTime());
        System.out.println(timestamp2);

        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime);
    }

    static boolean isChild(){
        return false;
    }
}
