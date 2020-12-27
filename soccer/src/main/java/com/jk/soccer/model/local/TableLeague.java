package com.jk.soccer.model.local;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tableLeague")
public class TableLeague {

    @PrimaryKey
    @ColumnInfo(name = "ID")
    private Integer ID;

    @ColumnInfo(name = "ParentID")
    private Integer parentID;

    @ColumnInfo(name = "Name", defaultValue = "")
    private String name;
}
