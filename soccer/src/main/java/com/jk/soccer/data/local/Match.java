package com.jk.soccer.data.local;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "tableMatch")
public class Match {

    @PrimaryKey
    @ColumnInfo (name = "ID")
    public Integer id;

    @ColumnInfo (name = "Date")
    public String date;

    @ColumnInfo (name = "Home")
    public Integer homeId;

    @ColumnInfo (name = "Away")
    public Integer awayId;

    @ColumnInfo (name = "Score")
    public String score;
}
