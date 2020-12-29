package com.jk.soccer.model.local;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

import com.google.gson.annotations.SerializedName;
import com.jk.soccer.etc.Type;

@Entity(tableName = "tableSearch", primaryKeys = {"ID", "ParentID"})
public class TableSearch {

    @NonNull
    @ColumnInfo(name = "ID", defaultValue = "0")
    private Integer ID;

    @NonNull
    @ColumnInfo(name = "ParentID", defaultValue = "0")
    private Integer parentID;

    @NonNull
    @ColumnInfo(name = "Type", defaultValue = "0")
    private Type type;

    @NonNull
    @ColumnInfo(name = "Name", defaultValue = "")
    private String name;

    public TableSearch(@NonNull Integer ID,
                       @NonNull Integer parentID,
                       @NonNull Type type,
                       @NonNull String name) {
        this.ID = ID;
        this.parentID = parentID;
        this.type = type;
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

    @NonNull
    public Type getType() {
        return type;
    }

    public void setType(@NonNull Type type) {
        this.type = type;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }
}
