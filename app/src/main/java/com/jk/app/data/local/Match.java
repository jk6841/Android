package com.jk.app.data.local;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;

/*@Entity (tableName = "tableMatch",
        foreignKeys = @ForeignKey(
                entity = Team.class,
                parentColumns = {"ID", "ID"},
                childColumns = {"Home", "Away"},
                onDelete = ForeignKey.CASCADE
        )
)*/
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
