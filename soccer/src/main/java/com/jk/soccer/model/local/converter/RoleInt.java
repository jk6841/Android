package com.jk.soccer.model.local.converter;

import androidx.room.TypeConverter;

import com.jk.soccer.etc.Role;

public class RoleInt {

    @TypeConverter
    public static Integer roleToInt(Role role){
        switch (role){
            case COACH:
                return 5;
            case GK:
                return 4;
            case DF:
                return 3;
            case MF:
                return 2;
            case FW:
                return 1;
            default:
                return 0;
        }
    }

    @TypeConverter
    public static Role intToRole(Integer integer){
        switch (integer){
            case 1:
                return Role.FW;
            case 2:
                return Role.MF;
            case 3:
                return Role.DF;
            case 4:
                return Role.GK;
            case 5:
                return Role.COACH;
            default:
                return Role.NONE;
        }
    }
}
