package com.jk.soccer.model.local.converter;

import androidx.room.TypeConverter;

import com.jk.soccer.etc.enumeration.Type;

public class TypeInt {
    @TypeConverter
    public static Integer typeToInt(Type type){
        switch (type){
            case MATCH:
                return 4;
            case LEAGUE:
                return 3;
            case TEAM:
                return 2;
            case PERSON:
                return 1;
            default:
                return 0;
        }
    }
    @TypeConverter
    public static Type intToType(Integer integer){
        switch (integer){
            case 4:
                return Type.MATCH;
            case 3:
                return Type.LEAGUE;
            case 2:
                return Type.TEAM;
            case 1:
                return Type.PERSON;
            default:
                return Type.NONE;
        }
    }
}
