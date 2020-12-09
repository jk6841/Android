package com.jk.soccer.converter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import androidx.room.TypeConverter;

import java.io.ByteArrayOutputStream;

public class BitmapStringConverter {

    @TypeConverter
    public static String BitmapToString(Bitmap bitmap){
        if (bitmap == null)
            return null;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, byteArrayOutputStream);
        return Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
    }

    @TypeConverter
    public static Bitmap StringToBitmap(String string){
        if (string == null)
            return null;
        try {
            byte[] bytes = Base64.decode(string,Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
