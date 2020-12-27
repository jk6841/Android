package com.jk.soccer.model.local;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

import com.jk.soccer.etc.Type;

@Entity (tableName = "table", primaryKeys = {"Type", "ID"})
public class Table {

    @NonNull
    @ColumnInfo(name = "Type")
    private Type type;

    @NonNull
    @ColumnInfo(name = "ID")
    private Integer ID;

    @ColumnInfo(name = "Name", defaultValue = "")
    private String name;

    public Table(@NonNull Type type, @NonNull Integer ID){
        this.type = type;
        this.ID = ID;
    }

    @NonNull
    public Type getType() {
        return type;
    }

    public void setType(@NonNull Type type) {
        this.type = type;
    }

    @NonNull
    public Integer getID() {
        return ID;
    }

    public void setID(@NonNull Integer ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
