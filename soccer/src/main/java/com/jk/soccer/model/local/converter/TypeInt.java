package com.jk.soccer.model.local.converter;

import androidx.room.TypeConverter;

import com.jk.soccer.etc.Type;

public class TypeInt {

    @TypeConverter
    public static Integer typeToInt(Type type){
        return type.getValue();
    }

    @TypeConverter
    public static Type intToType(Integer integer){
        Type type = Type.NONE;
        type.setValue(integer);
        return type;
    }
}
