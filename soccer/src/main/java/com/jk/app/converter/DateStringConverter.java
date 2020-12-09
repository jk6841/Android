package com.jk.app.converter;

import androidx.room.TypeConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateStringConverter {

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd",Locale.KOREA);

    @TypeConverter
    public static String DateToString(Date date){
        if (date != null)
            return dateFormat.format(date);
        return null;
    }

    @TypeConverter
    public static Date StringToDate(String string){
        if (string != null){
            try {
                return dateFormat.parse(string);
            } catch (ParseException e){
                e.printStackTrace();
            }
        }
        return null;
    }

}
