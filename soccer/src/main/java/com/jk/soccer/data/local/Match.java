package com.jk.soccer.data.local;

import android.text.format.Time;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.jk.soccer.etc.MyJSON;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Entity (tableName = "tableMatch")
public class Match {

    @Ignore
    final private String unknownMsg = "";

    @PrimaryKey
    @ColumnInfo (name = "ID")
    private Integer id;

    @ColumnInfo (name = "Name")
    private String name = unknownMsg;

    @ColumnInfo (name = "LeagueID")
    private Integer leagueId;

    @ColumnInfo (name = "HomeID")
    private Integer homeId = 0;

    @ColumnInfo (name = "HomeName")
    private String homeName = unknownMsg;

    @ColumnInfo (name = "HomeScore")
    private Integer homeScore;

    @ColumnInfo (name = "HomeImage")
    private String homeImage = unknownMsg;

    @ColumnInfo (name = "AwayID")
    private Integer awayId = 0;

    @ColumnInfo (name = "AwayName")
    private String awayName = unknownMsg;

    @ColumnInfo (name = "AwayScore")
    private Integer awayScore;

    @ColumnInfo (name = "AwayImage")
    private String awayImage = unknownMsg;

    @ColumnInfo (name = "Started")
    private Boolean started;

    @ColumnInfo (name = "Cancelled")
    private Boolean cancelled;

    @ColumnInfo (name = "Finished")
    private Boolean finished;

    @ColumnInfo(name = "StartTimeStr")
    private String startTimeStr = unknownMsg;

    @ColumnInfo(name = "Year")
    private Integer year = 0;

    @ColumnInfo(name = "Month")
    private Integer month = 0;

    @ColumnInfo(name = "Date")
    private Integer date = 0;

    @ColumnInfo(name = "DayStr")
    private String dayStr = unknownMsg;

    @ColumnInfo(name = "Stadium")
    private String stadium = unknownMsg;

    public Match (Integer id){
        this.id = id;
    }

    @Ignore
    public Match(String jsonString){
        try{
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONObject jsonHeader = MyJSON.myJSONObject(jsonObject, "header");
            JSONArray jsonTeams = MyJSON.myJSONArray(jsonHeader, "teams");
            JSONObject jsonHome = jsonTeams.getJSONObject(0);
            JSONObject jsonAway = jsonTeams.getJSONObject(1);
            this.homeName = MyJSON.myJSONString(jsonHome,"name");
            this.homeScore = MyJSON.myJSONInt(jsonHome,"score");
            this.homeImage = MyJSON.myJSONString(jsonHome,"imageUrl");
            this.homeId = Integer.parseInt(this.homeImage.replaceAll("[^\\d]", ""));
            this.awayName = MyJSON.myJSONString(jsonAway,"name");
            this.awayScore = MyJSON.myJSONInt(jsonAway,"score");
            this.awayImage = MyJSON.myJSONString(jsonAway,"imageUrl");
            this.awayId = Integer.parseInt(this.awayImage.replaceAll("[^\\d]", ""));
            JSONObject jsonStatus = MyJSON.myJSONObject(jsonHeader,"status");
            this.started = MyJSON.myJSONBoolean(jsonStatus,"started");
            this.cancelled = MyJSON.myJSONBoolean(jsonStatus,"cancelled");
            this.finished = MyJSON.myJSONBoolean(jsonStatus, "finished");
            String startDateStr = MyJSON.myJSONString(jsonStatus, "startDateStr");
            Date dateValue = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH)
                    .parse(startDateStr);
            year = Integer.parseInt(new SimpleDateFormat("yyyy", Locale.KOREA).format(dateValue));
            month = Integer.parseInt(new SimpleDateFormat("M", Locale.KOREA).format(dateValue));
            date = Integer.parseInt(new SimpleDateFormat("d", Locale.KOREA).format(dateValue));
            dayStr = new SimpleDateFormat("E요일 ", Locale.KOREA).format(date);
            startTimeStr = MyJSON.myJSONString(jsonStatus, "startTimeStr");
            if (!startTimeStr.equals("")) {
                Date date2 = new SimpleDateFormat("HH:mm").parse(startTimeStr);
                startTimeStr = new SimpleDateFormat("H시 m분", Locale.KOREA).format(date2);
            }
            JSONObject jsonContent = MyJSON.myJSONObject(jsonObject, "content");
            JSONObject jsonMatchFacts = MyJSON.myJSONObject(jsonContent,"matchFacts");
            id = MyJSON.myJSONInt(jsonMatchFacts, "matchId");
            JSONObject jsonInfoBox = MyJSON.myJSONObject(jsonMatchFacts, "infoBox");
            JSONObject jsonTournament = MyJSON.myJSONObject(jsonInfoBox, "Tournament");
            this.leagueId = MyJSON.myJSONInt(jsonTournament, "id");
            this.name = MyJSON.myJSONString(jsonTournament, "text");
            JSONObject jsonStadium = MyJSON.myJSONObject(jsonInfoBox, "Stadium");
            this.stadium = MyJSON.myJSONString(jsonStadium, "name");
        } catch (JSONException | ParseException e){
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

    public String getDayStr() {
        return dayStr;
    }

    public void setDayStr(String dayStr) {
        this.dayStr = dayStr;
    }

    public String getStadium() {
        return stadium;
    }

    public void setStadium(String stadium) {
        this.stadium = stadium;
    }

}
