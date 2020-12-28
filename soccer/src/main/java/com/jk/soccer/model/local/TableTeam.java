package com.jk.soccer.model.local;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

@Entity(tableName = "tableTeam")

public class TableTeam {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "ID")
    private Integer ID;

    @NonNull
    @ColumnInfo(name = "LeagueID")
    private Integer leagueID;

    @NonNull
    @ColumnInfo(name = "Rank")
    private Integer rank;

    @NonNull
    @ColumnInfo(name = "Name", defaultValue = "")
    private String name;

    public TableTeam(@NonNull Integer ID,
                     @NonNull Integer leagueID,
                     @NonNull Integer rank,
                     @NonNull String name) {
        this.ID = ID;
        this.leagueID = leagueID;
        this.rank = rank;
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
    public Integer getLeagueID() {
        return leagueID;
    }

    public void setLeagueID(@NonNull Integer leagueID) {
        this.leagueID = leagueID;
    }

    @NonNull
    public Integer getRank() {
        return rank;
    }

    public void setRank(@NonNull Integer rank) {
        this.rank = rank;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

}
