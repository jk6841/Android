package com.jk.soccer.data.local;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity (tableName = "tableMatch")
public class TableMatch {

    @Ignore
    final private String unknownMsg = "";

    @PrimaryKey
    @ColumnInfo (name = "ID")
    private Integer id;

    @ColumnInfo (name = "Name")
    private String name = unknownMsg;

    @ColumnInfo (name = "HomeID")
    private Integer homeId = 0;

    @ColumnInfo (name = "HomeName")
    private String homeName = unknownMsg;

    @ColumnInfo (name = "HomeScore")
    private Integer homeScore;

    @ColumnInfo (name = "HomeImage")
    private String homeImage = unknownMsg;

    @ColumnInfo (name = "HomeLineup")
    private String homeLineup = unknownMsg;

    @ColumnInfo (name = "AwayID")
    private Integer awayId = 0;

    @ColumnInfo (name = "AwayName")
    private String awayName = unknownMsg;

    @ColumnInfo (name = "AwayScore")
    private Integer awayScore;

    @ColumnInfo (name = "AwayImage")
    private String awayImage = unknownMsg;

    @ColumnInfo (name = "AwayLineup")
    private String awayLineup = unknownMsg;

    @ColumnInfo (name = "Started")
    private Boolean started;

    @ColumnInfo (name = "Cancelled")
    private Boolean cancelled;

    @ColumnInfo (name = "Finished")
    private Boolean finished;

    @ColumnInfo(name = "Time")
    private String time = unknownMsg;

    @ColumnInfo(name = "Year")
    private Integer year = 0;

    @ColumnInfo(name = "Month")
    private Integer month = 0;

    @ColumnInfo(name = "Date")
    private Integer date = 0;

    @ColumnInfo(name = "Day")
    private String day = unknownMsg;

    @ColumnInfo(name = "Stadium")
    private String stadium = unknownMsg;

    @ColumnInfo(name = "Event")
    private String event = unknownMsg;

    @ColumnInfo(name = "BestPlayerID")
    private Integer bestPlayerID = 0;

    @ColumnInfo(name = "BestPlayerName")
    private String bestPlayerName = unknownMsg;

    @ColumnInfo(name = "BestTeam")
    private String bestTeam = unknownMsg;

    public TableMatch(Integer id){
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getHomeId() {
        return homeId;
    }

    public void setHomeId(Integer homeId) {
        this.homeId = homeId;
    }

    public String getHomeName() {
        return homeName;
    }

    public void setHomeName(String homeName) {
        this.homeName = homeName;
    }

    public Integer getHomeScore() {
        return homeScore;
    }

    public void setHomeScore(Integer homeScore) {
        this.homeScore = homeScore;
    }

    public String getHomeImage() {
        return homeImage;
    }

    public void setHomeImage(String homeImage) {
        this.homeImage = homeImage;
    }

    public String getHomeLineup() {
        return homeLineup;
    }

    public void setHomeLineup(String homeLineup) {
        this.homeLineup = homeLineup;
    }

    public Integer getAwayId() {
        return awayId;
    }

    public void setAwayId(Integer awayId) {
        this.awayId = awayId;
    }

    public String getAwayName() {
        return awayName;
    }

    public void setAwayName(String awayName) {
        this.awayName = awayName;
    }

    public Integer getAwayScore() {
        return awayScore;
    }

    public void setAwayScore(Integer awayScore) {
        this.awayScore = awayScore;
    }

    public String getAwayImage() {
        return awayImage;
    }

    public void setAwayImage(String awayImage) {
        this.awayImage = awayImage;
    }

    public String getAwayLineup() {
        return awayLineup;
    }

    public void setAwayLineup(String awayLineup) {
        this.awayLineup = awayLineup;
    }

    public Boolean getStarted() {
        return started;
    }

    public void setStarted(Boolean started) {
        this.started = started;
    }

    public Boolean getCancelled() {
        return cancelled;
    }

    public void setCancelled(Boolean cancelled) {
        this.cancelled = cancelled;
    }

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getStadium() {
        return stadium;
    }

    public void setStadium(String stadium) {
        this.stadium = stadium;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public Integer getBestPlayerID() {
        return bestPlayerID;
    }

    public void setBestPlayerID(Integer bestPlayerID) {
        this.bestPlayerID = bestPlayerID;
    }

    public String getBestPlayerName() {
        return bestPlayerName;
    }

    public void setBestPlayerName(String bestPlayer) {
        this.bestPlayerName = bestPlayer;
    }

    public String getBestTeam() {
        return bestTeam;
    }

    public void setBestTeam(String bestTeam) {
        this.bestTeam = bestTeam;
    }
}
