package com.jk.soccer.data.local;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@Entity (tableName = "tableMatch")
public class Match {

    @PrimaryKey
    @ColumnInfo (name = "ID")
    private Integer id;

    @ColumnInfo (name = "LeagueID")
    private Integer leagueId;

    @ColumnInfo (name = "Text")
    private String text;

    @ColumnInfo (name = "HomeName")
    private String homeName;

    @ColumnInfo (name = "HomeScore")
    private Integer homeScore;

    @ColumnInfo (name = "HomeImage")
    private String homeImage;

    @ColumnInfo (name = "AwayName")
    private String awayName;

    @ColumnInfo (name = "AwayScore")
    private Integer awayScore;

    @ColumnInfo (name = "AwayImage")
    private String awayImage;

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

    @ColumnInfo(name = "Stadium")
    private String stadium;

    @ColumnInfo(name = "Bookmark")
    private boolean bookmark;

    public Match (Integer id){
        this.id = id;
    }

    @Ignore
    public Match(Integer id, String jsonString){
        this.id = id;
        try{
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONObject jsonHeader = jsonObject.getJSONObject("header");
            JSONArray jsonTeams = jsonHeader.getJSONArray("teams");
            JSONObject jsonHome = jsonTeams.getJSONObject(0);
            JSONObject jsonAway = jsonTeams.getJSONObject(1);
            this.homeName = jsonHome.getString("name");
            this.homeScore = jsonHome.getInt("score");
            this.homeImage = jsonHome.getString("imageUrl");
            this.awayName = jsonAway.getString("name");
            this.awayScore = jsonAway.getInt("score");
            this.awayImage = jsonAway.getString("imageUrl");
            JSONObject jsonStatus = jsonHeader.getJSONObject("status");
            this.started = jsonStatus.getBoolean("started");
            this.cancelled = jsonStatus.getBoolean("cancelled");
            this.finished = jsonStatus.getBoolean("finished");
            this.startDateStr = jsonStatus.getString("startDateStr");
            this.startTimeStr = jsonStatus.getString("startTimeStr");
            JSONObject jsonContent = jsonObject.getJSONObject("content");
            JSONObject jsonMatchFacts = jsonContent.getJSONObject("matchFacts");
            JSONObject jsonInfoBox = jsonMatchFacts.getJSONObject("infoBox");
            JSONObject jsonTournament = jsonInfoBox.getJSONObject("Tournament");
            this.leagueId = jsonTournament.getInt("id");
            this.text = jsonTournament.getString("text");
            JSONObject jsonStadium = jsonInfoBox.getJSONObject("Stadium");
            this.stadium = jsonStadium.getString("name");
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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

    public String getStadium() {
        return stadium;
    }

    public void setStadium(String stadium) {
        this.stadium = stadium;
    }

    public boolean isBookmark() {
        return bookmark;
    }

    public void setBookmark(boolean bookmark) {
        this.bookmark = bookmark;
    }

}
