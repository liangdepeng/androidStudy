package com.example.ldp.base_lib.utils;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.Locale;

public class TimeUtils {

    private StringBuilder mFormatBuilder;
    private Formatter mFormatter;


    public TimeUtils() {
        // 转换成字符串的时间
        mFormatBuilder = new StringBuilder();
        mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());

    }

    /**
     * 把毫秒转换成：1:20:30这里形式
     *
     * @param timeMs
     * @return
     */
    public String stringForTime(int timeMs) {
        int totalSeconds = timeMs / 1000;
        int seconds = totalSeconds % 60;

        int minutes = (totalSeconds / 60) % 60;

        int hours = totalSeconds / 3600;

        mFormatBuilder.setLength(0);
        if (hours > 0) {
            return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds)
                    .toString();
        } else {
            return mFormatter.format("%02d:%02d", minutes, seconds).toString();
        }
    }


    /**
     * 日期格式及转换
     *
     * @param dateString  输入日期
     * @param inputFormat  输入格式
     * @param outFormat 输出格式
     * @return 输出格式的时间
     */
    @SuppressLint("SimpleDateFormat")
    public static String parseDate(String dateString, String inputFormat, String outFormat) {

        try {
            //"yyyy-MM-dd HH:mm:ss"
            SimpleDateFormat inputDate = new SimpleDateFormat(inputFormat);
            //"MM月dd日"  "mm:ss"
            SimpleDateFormat outputDate = new SimpleDateFormat(outFormat);
            Date date = inputDate.parse(dateString);
            return outputDate.format(date);

        } catch (Exception e) {
            return "";
        }

    }


}
