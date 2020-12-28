package com.jk.soccer.model.local.converter;

import androidx.room.TypeConverter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateString {

    final static private SimpleDateFormat dateFormat
            = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);

    @TypeConverter
    public static String dateToString(Date date){
        if (date == null)
            return "1999-12-31 23:59:59";
        return dateFormat.format(date);
    }

    @TypeConverter
    public static Date stringToDate(String string){
        try {
            return dateFormat.parse(string);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
