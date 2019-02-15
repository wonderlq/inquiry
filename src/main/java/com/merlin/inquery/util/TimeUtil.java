package com.merlin.inquery.util;


/**
 * @author lq
 * @since 1.0.0
 * Created On 2019-02-15 14:08
 */
public class TimeUtil {

    public static String getCurrentSecond() {
       return String.valueOf(System.currentTimeMillis()/1000);
    }
}
