package com.jk.soccer.data.local;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity (tableName = "tableMatch")
public class Match {

    @PrimaryKey
    @ColumnInfo (name = "ID")
    private Integer id;

    @ColumnInfo (name = "Date")
    private Date date;

    @ColumnInfo (name = "Score")
    private String score;

    @ColumnInfo (name = "Home")
    private Integer homeId;

    @ColumnInfo (name = "Away")
    private Integer awayId;

    @ColumnInfo(name = "Bookmark")
    private boolean bookmark;

    public Match(Integer id){
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public Integer getHomeId() {
        return homeId;
    }

    public void setHomeId(Integer homeId) {
        this.homeId = homeId;
    }

    public Integer getAwayId() {
        return awayId;
    }

    public void setAwayId(Integer awayId) {
        this.awayId = awayId;
    }

    public boolean isBookmark() {
        return bookmark;
    }

    public void setBookmark(boolean bookmark) {
        this.bookmark = bookmark;
    }

}
