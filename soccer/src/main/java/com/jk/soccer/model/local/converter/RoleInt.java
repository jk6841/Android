package com.jk.soccer.model.local.converter;

import androidx.room.TypeConverter;

import com.jk.soccer.etc.Role;

public class RoleInt {

    @TypeConverter
    public static Integer roleToInt(Role role){
        return role.getValue();
    }

    @TypeConverter
    public static Role intToRole(Integer integer){
        Role role = Role.NONE;
        role.setValue(integer);
        return role;
    }
}
