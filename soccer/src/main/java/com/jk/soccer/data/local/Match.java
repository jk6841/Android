package com.jk.soccer.data.local;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

@Entity (tableName = "tableMatch")
public class Match {

    @PrimaryKey
    @ColumnInfo (name = "ID")
    private Integer id;

    @ColumnInfo (name = "LeagueID")
    private Integer leagueId;

    @ColumnInfo (name = "Time")
    private String time;

    @ColumnInfo (name = "HomeID")
    private Integer homeId;

    @ColumnInfo (name = "HomeName")
    private String homeName;

    @ColumnInfo (name = "HomeScore")
    private Integer homeScore;

    @ColumnInfo (name = "AwayID")
    private Integer awayId;

    @ColumnInfo (name = "AwayName")
    private String awayName;

    @ColumnInfo (name = "AwayScore")
    private Integer awayScore;

    @ColumnInfo (name = "Started")
    private Boolean started;

    @ColumnInfo (name = "Cancelled")
    private Boolean cancelled;

    @ColumnInfo (name = "Finished")
    private Boolean finished;

    @ColumnInfo(name = "StartTimeStr")
    private String startTimeStr;

    @ColumnInfo(name = "StartDateStr")
    private String startDateStr;

    @ColumnInfo(name = "TimeTS")
    private Integer timeTS;

    @ColumnInfo(name = "Bookmark")
    private boolean bookmark;

    public Match (Integer id){
        setId(id);
        setLeagueId(-1);
        setTime("");
        setHomeId(-1);
        setHomeName("");
        setHomeScore(0);
        setAwayId(-1);
        setAwayName("");
        setAwayScore(0);
        setStarted(false);
        setCancelled(false);
        setFinished(false);
        setStartTimeStr("");
        setStartDateStr("");
        setTimeTS(-1);
    }

    @Ignore
    public Match(JSONObject jsonMatch){

        try{
            id = jsonMatch.getInt("id");
            leagueId = jsonMatch.getInt("leagueId");
            time = jsonMatch.getString("time");
            JSONObject jsonHome = jsonMatch.getJSONObject("home");
            JSONObject jsonAway = jsonMatch.getJSONObject("away");
            JSONObject jsonStatus = jsonMatch.getJSONObject("status");
            homeId = jsonHome.getInt("id");
            homeName = jsonHome.getString("name");
            homeScore = jsonHome.getInt("score");
            awayId = jsonAway.getInt("id");
            awayName = jsonAway.getString("name");
            awayScore = jsonAway.getInt("score");
            started = jsonStatus.getBoolean("started");
            cancelled = jsonStatus.getBoolean("cancelled");
            finished = jsonStatus.getBoolean("finished");
            startTimeStr = jsonStatus.getString("startTimeStr");
            startDateStr = jsonStatus.getString("startDateStr");
            timeTS = jsonMatch.getInt("timeTS");
        } catch (JSONException e){
            e.printStackTrace();
        }

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLeagueId() {
        return leagueId;
    }

    public void setLeagueId(Integer leagueId) {
        this.leagueId = leagueId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

    public String getStartTimeStr() {
        return startTimeStr;
    }

    public void setStartTimeStr(String startTimeStr) {
        this.startTimeStr = startTimeStr;
    }

    public String getStartDateStr() {
        return startDateStr;
    }

    public void setStartDateStr(String startDateStr) {
        this.startDateStr = startDateStr;
    }

    public Integer getTimeTS() {
        return timeTS;
    }

    public void setTimeTS(Integer timeTS) {
        this.timeTS = timeTS;
    }

    public boolean isBookmark() {
        return bookmark;
    }

    public void setBookmark(boolean bookmark) {
        this.bookmark = bookmark;
    }

}
