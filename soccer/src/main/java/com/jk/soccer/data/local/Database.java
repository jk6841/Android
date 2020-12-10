package com.jk.soccer.data.local;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.jk.soccer.etc.DateStringConverter;

@androidx.room.Database(entities = {Player.class, Team.class, Match.class}, version = 1, exportSchema =  false)
@TypeConverters({DateStringConverter.class})
public abstract class Database extends RoomDatabase {

    public abstract DBDao dbPlayerDao();
    private static Database database = null;
    public static Database getInstance(Application application){
        if (database == null){
            Context context = application.getApplicationContext();
            database = Room
                    .databaseBuilder(context, Database.class, "app.db")
                    .createFromAsset("database/init.db").build();
        }
        return database;
    }
}
