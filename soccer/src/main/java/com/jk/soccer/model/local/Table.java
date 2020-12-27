package com.jk.soccer.model.local;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity (tableName = "table", primaryKeys = {"ID", "ParentID"})
public class Table {

    @NonNull
    @ColumnInfo(name = "ID")
    private Integer ID;

    @NonNull
    @ColumnInfo(name = "ParentID")
    private Integer parentID;

    @ColumnInfo(name = "Name", defaultValue = "")
    private String name;

    public Table(@NonNull Integer ID, @NonNull Integer parentID, String name){
        this.ID = ID;
        this.parentID = parentID;
        this.name = name;
    }

    @NonNull
    public Integer getID() {
        return ID;
    }

    public void setID(@NonNull Integer ID) {
        this.ID = ID;
    }

    @NonNull
    public Integer getParentID() {
        return parentID;
    }

    public void setParentID(@NonNull Integer parentID) {
        this.parentID = parentID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
