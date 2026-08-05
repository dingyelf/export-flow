package io.github.exportflow.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    private static final String DATE_FORMAT = "yyyy-MM-dd";

    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static String formatDateTime(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
        return simpleDateFormat.format(date);
    }

}
