package com.jk.soccer.model.local;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tempTeam")
public class TempTeam {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name="ID")
    private Integer ID;

    public TempTeam(@NonNull Integer ID) {
        this.ID = ID;
    }

    @NonNull
    public Integer getID() {
        return ID;
    }

    public void setID(@NonNull Integer ID) {
        this.ID = ID;
    }
}
