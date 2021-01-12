package com.example.catchcrashlib;

import android.annotation.SuppressLint;
import android.os.Build;

import androidx.annotation.Nullable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Created by ldp.
 * <p>
 * Date: 2020-12-31
 * <p>
 * Summary: 获取当前时间
 */
public class DateUtil {

    private static DateFormat dateFormat; // 线程不安全
    private static DateTimeFormatter timeFormatter;// java8之后使用 线程安全

    public static String getNowTime() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (timeFormatter == null) {
                timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
            }
            return LocalDate.now().format(timeFormatter);
        } else {
            if (dateFormat == null) {
                dateFormat = df.get();
            }
            return dateFormat.format(new Date());
        }
    }

    private static final ThreadLocal<DateFormat> df = new ThreadLocal<DateFormat>() {
        @SuppressLint("SimpleDateFormat")
        @Nullable
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        }
    };
}
