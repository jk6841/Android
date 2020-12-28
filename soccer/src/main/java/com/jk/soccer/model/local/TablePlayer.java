package com.jk.soccer.model.local;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "tablePlayer")

public class TablePlayer{

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "ID")
    private Integer ID;

    @NonNull
    @ColumnInfo(name = "TeamID")
    private Integer teamID;

    @NonNull
    @ColumnInfo(name = "Name", defaultValue = "")
    private String name;

    @NonNull
    @ColumnInfo(name = "Role", defaultValue = "")
    private String role;

    public TablePlayer(@NonNull Integer ID,
                       @NonNull Integer teamID,
                       @NonNull String name,
                       @NonNull String role) {
        this.ID = ID;
        this.teamID = teamID;
        this.name = name;
        this.role = role;
    }

    @NonNull
    public Integer getID() {
        return ID;
    }

    public void setID(@NonNull Integer ID) {
        this.ID = ID;
    }

    @NonNull
    public Integer getTeamID() {
        return teamID;
    }

    public void setTeamID(@NonNull Integer teamID) {
        this.teamID = teamID;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getRole() {
        return role;
    }

    public void setRole(@NonNull String role) {
        this.role = role;
    }
}
