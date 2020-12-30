package com.jk.soccer.model.local;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.jk.soccer.model.local.converter.DateString;
import com.jk.soccer.model.local.converter.EventList;
import com.jk.soccer.model.local.converter.LineupList;
import com.jk.soccer.model.local.converter.RoleInt;
import com.jk.soccer.model.local.converter.TypeInt;

@androidx.room.Database(
        entities = {
                TableSearch.class,
                TableLeague.class,
                TableTeam.class,
                TablePlayer.class,
                TempLeague.class,
                TempTeam.class,
                TempPlayer.class},
        version = 1,
        exportSchema =  false)
@TypeConverters({EventList.class, LineupList.class, RoleInt.class, DateString.class, TypeInt.class})
public abstract class Database extends RoomDatabase {

    public abstract DBDao dbDao();

    public static Database getInstance(Application application){
        if (database == null){
            Context context = application.getApplicationContext();
            database = Room
                    .databaseBuilder(context, Database.class, storeDB)
                    .createFromAsset(assetDB).build();
        }
        return database;
    }

    private static Database database = null;
    final private static String storeDB = "app.db";
    final private static String assetDB = "database/init.db";

}
