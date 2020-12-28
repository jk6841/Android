package com.jk.soccer.model.local;

import android.app.Application;
import android.content.Context;
import android.database.Cursor;

import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.jk.soccer.model.local.converter.EventList;
import com.jk.soccer.model.local.converter.LineupList;

@androidx.room.Database(
        entities = {Table.class,
                TableLeague.class,
                TableTeam.class,
                TablePlayer.class,
                TempLeague.class,
                TempTeam.class,
                TempPlayer.class},
        version = 1,
        exportSchema =  false)
@TypeConverters({EventList.class, LineupList.class})
public abstract class Database extends RoomDatabase {

    public abstract DBDao dbDao();
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
