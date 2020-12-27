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
    private Integer parent;

    @ColumnInfo(name = "Name", defaultValue = "")
    private String name;

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
