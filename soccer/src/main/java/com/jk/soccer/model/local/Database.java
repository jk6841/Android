package com.jk.soccer.model.local;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

@androidx.room.Database(
        entities = {TablePlayer.class, TableTeam.class, TableMatch.class},
        version = 1,
        exportSchema =  false)

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
