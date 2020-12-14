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
    final private String unknownMsg = "알 수 없음";

    @PrimaryKey
    @ColumnInfo (name = "ID")
    private Integer id;

    @ColumnInfo (name = "LeagueID")
    private Integer leagueId;

    @ColumnInfo (name = "Text")
    private String text = unknownMsg;

    @ColumnInfo (name = "HomeName")
    private String homeName = unknownMsg;

    @ColumnInfo (name = "HomeScore")
    private Integer homeScore;

    @ColumnInfo (name = "HomeImage")
    private String homeImage = unknownMsg;

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

    @ColumnInfo(name = "YearStr")
    private String yearStr = unknownMsg;

    @ColumnInfo(name = "MonthStr")
    private String monthStr = unknownMsg;

    @ColumnInfo(name = "DateStr")
    private String dateStr = unknownMsg;

    @ColumnInfo(name = "DayStr")
    private String dayStr = unknownMsg;

    @ColumnInfo(name = "Stadium")
    private String stadium = unknownMsg;

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
            JSONObject jsonHeader = MyJSON.myJSONObject(jsonObject, "header");
            JSONArray jsonTeams = MyJSON.myJSONArray(jsonHeader, "teams");
            JSONObject jsonHome = jsonTeams.getJSONObject(0);
            JSONObject jsonAway = jsonTeams.getJSONObject(1);
            this.homeName = MyJSON.myJSONString(jsonHome,"name");
            this.homeScore = MyJSON.myJSONInt(jsonHome,"score");
            this.homeImage = MyJSON.myJSONString(jsonHome,"imageUrl");
            this.awayName = MyJSON.myJSONString(jsonAway,"name");
            this.awayScore = MyJSON.myJSONInt(jsonAway,"score");
            this.awayImage = MyJSON.myJSONString(jsonAway,"imageUrl");
            JSONObject jsonStatus = MyJSON.myJSONObject(jsonHeader,"status");
            this.started = MyJSON.myJSONBoolean(jsonStatus,"started");
            this.cancelled = MyJSON.myJSONBoolean(jsonStatus,"cancelled");
            this.finished = MyJSON.myJSONBoolean(jsonStatus, "finished");
            String startDateStr = MyJSON.myJSONString(jsonStatus, "startDateStr");
            Date date = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH)
                    .parse(startDateStr);
            yearStr = new SimpleDateFormat("yyyy년", Locale.KOREA).format(date);
            monthStr = new SimpleDateFormat("MM월", Locale.KOREA).format(date);
            dateStr = new SimpleDateFormat("dd일", Locale.KOREA).format(date);
            dayStr = new SimpleDateFormat("E요일", Locale.KOREA).format(date);
            startTimeStr = MyJSON.myJSONString(jsonStatus, "startTimeStr");
            if (!startTimeStr.equals("")) {
                Date date2 = new SimpleDateFormat("HH:mm").parse(startTimeStr);
                startTimeStr = new SimpleDateFormat("HH시 mm분", Locale.KOREA).format(date2);
            }
            JSONObject jsonContent = MyJSON.myJSONObject(jsonObject, "content");
            JSONObject jsonMatchFacts = MyJSON.myJSONObject(jsonContent,"matchFacts");
            JSONObject jsonInfoBox = MyJSON.myJSONObject(jsonMatchFacts, "infoBox");
            JSONObject jsonTournament = MyJSON.myJSONObject(jsonInfoBox, "Tournament");
            this.leagueId = MyJSON.myJSONInt(jsonTournament, "id");
            this.text = MyJSON.myJSONString(jsonTournament, "text");
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

    public String getYearStr() {
        return yearStr;
    }

    public void setYearStr(String yearStr) {
        this.yearStr = yearStr;
    }

    public String getMonthStr() {
        return monthStr;
    }

    public void setMonthStr(String monthStr) {
        this.monthStr = monthStr;
    }

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
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

    public boolean isBookmark() {
        return bookmark;
    }

    public void setBookmark(boolean bookmark) {
        this.bookmark = bookmark;
    }

}
