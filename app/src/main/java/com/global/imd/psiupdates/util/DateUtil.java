package com.global.imd.psiupdates.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Caca Rusmana on 19/09/2017.
 */

public class DateUtil {

    public static String formatDate(String pattern, Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone(Constant.SG_ZONE_ID));
        return sdf.format(date);
    }

    public static Date parseDate(String pattern, String date) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone(Constant.SG_ZONE_ID));

        try {
            return sdf.parse(date);
        } catch (ParseException pe) {
            return null;
        }
    }
}
