package com.hoolai.chatmonitor.open.utils;

import com.google.common.base.Strings;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class DateUtil {

    private static ThreadLocal<Map<String, SimpleDateFormat>> threadLocal = new ThreadLocal<Map<String, SimpleDateFormat>>();

    public static String format(long currentTimeMillis, String pattern) {
        return format(new Date(currentTimeMillis), pattern);
    }

    public static String formatCurrentDate(String pattern) {
        SimpleDateFormat sdf = getSdf(pattern);
        return sdf.format(DateUtil.newDate());
    }

    public static String format(Date date, String pattern) {
        SimpleDateFormat sdf = getSdf(pattern);
        return sdf.format(date);
    }

    public static Date parse(String date, String... patterns) {
        if (patterns != null) {
            for (String pattern : patterns) {
                SimpleDateFormat sdf = getSdf(pattern);
                try {
                    return sdf.parse(date);
                } catch (ParseException e) {
                    continue;
                }
            }
        }
        throw new RuntimeException();
    }


    private static SimpleDateFormat getSdf(String pattern) {
        Map<String, SimpleDateFormat> sdfMap = threadLocal.get();
        if (sdfMap == null) {
            sdfMap = new HashMap<String, SimpleDateFormat>();
            threadLocal.set(sdfMap);
        }
        SimpleDateFormat sdf = sdfMap.get(pattern);
        if (sdf == null) {
            sdf = new SimpleDateFormat(pattern);
            sdfMap.put(pattern, sdf);
        }
        return sdf;
    }


    public static Date add(Date date, long value, TimeUnit unit) {
        return add(date.getTime(), value, unit);
    }

    public static Date add(long currentTimeMillis, long value, TimeUnit unit) {
        return new Date(currentTimeMillis + unit.toMillis(value));
    }

    public static Date addDay(Date date, int value) {
        return add(date, value, TimeUnit.DAYS);
    }

    public static Date addDay(long currentTimeMillis, int value, TimeUnit unit) {
        return add(currentTimeMillis, value, TimeUnit.DAYS);
    }


    public static Date newDate(int... dateTime) {
        if (dateTime == null || dateTime.length == 0) {
            return new Date(TimeUtil.currentTimeMillis());
        }
        switch (dateTime.length) {
            case 1:
                return yyyy(dateTime[0]);
            case 2:
                return yyyyMM(dateTime[0], dateTime[1]);
            case 3:
                return yyyyMMdd(dateTime[0], dateTime[1], dateTime[2]);
            case 4:
                return yyyyMMddHH(dateTime[0], dateTime[1], dateTime[2], dateTime[3]);
            case 5:
                return yyyyMMddHHmm(dateTime[0], dateTime[1], dateTime[2], dateTime[3], dateTime[4]);
            case 6:
                return yyyyMMddHHmmss(dateTime[0], dateTime[1], dateTime[2], dateTime[3], dateTime[4], dateTime[5]);
        }
        throw new RuntimeException();
    }

    private static Date yyyy(int yyyy) {
        return parse(padStart(yyyy, 4), "yyyy");
    }

    private static Date yyyyMM(int yyyy, int MM) {
        return parse(String.format("%s%s", padStart(yyyy, 4), padStart(MM, 2)), "yyyyMM");
    }

    private static Date yyyyMMdd(int yyyy, int MM, int dd) {
        return parse(String.format("%s%s%s", padStart(yyyy, 4), padStart(MM, 2), padStart(dd, 2)), "yyyyMMdd");
    }

    private static Date yyyyMMddHH(int yyyy, int MM, int dd, int HH) {
        return parse(String.format("%s%s%s%s:", padStart(yyyy, 4), padStart(MM, 2), padStart(dd, 2), padStart(HH, 2)), "yyyyMMddHH");
    }

    private static Date yyyyMMddHHmm(int yyyy, int MM, int dd, int HH, int mm) {
        return parse(String.format("%s%s%s%s%s", padStart(yyyy, 4), padStart(MM, 2), padStart(dd, 2), padStart(HH, 2), padStart(mm, 2)), "yyyyMMddHHmm");
    }

    private static Date yyyyMMddHHmmss(int yyyy, int MM, int dd, int HH, int mm, int ss) {
        return parse(String.format("%s%s%s%s%s%s", padStart(yyyy, 4), padStart(MM, 2), padStart(dd, 2), padStart(HH, 2), padStart(mm, 2), padStart(ss, 2)), "yyyyMMddHHmmss");
    }

    public static DateRange getDateRange(Date startDate, int offsetDay) {
        Date endDate = addDay(startDate, offsetDay);
        return new DateRange(startDate, endDate);
    }

    public static DateRange getYesterdayDateRange() {
        Date startDate = discardTime(newDate());
        startDate = addDay(startDate, -1);
        return getDateRange(startDate, 1);
    }

    public static DateRange getTodayDateRange() {
        Date startDate = discardTime(newDate());
        return getDateRange(startDate, 1);
    }

    public static Date discardTime(Date date) {
        String format = format(date, "yyyyMMdd");
        return parse(format, "yyyyMMdd");
    }

    public static Date discardDay(Date date) {
        String format = format(date, "yyyyMM");
        return parse(format, "yyyyMM");
    }

    public static Date addMonth(Date date, int month) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        instance.add(Calendar.MONTH, month);
        return instance.getTime();
    }

    private static String padStart(Integer value, int length) {
        return Strings.padStart(value + "", length, '0');
    }
}
