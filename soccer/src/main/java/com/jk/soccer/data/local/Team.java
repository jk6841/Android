package com.jk.soccer.data.local;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

@Entity(tableName = "tableTeam")

public class Team {

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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLeague() {
        return league;
    }

    public void setLeague(String league) {
        this.league = league;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getTopRating() {
        return topRating;
    }

    public void setTopRating(String topRating) {
        this.topRating = topRating;
    }

    public String getTopGoal() {
        return topGoal;
    }

    public void setTopGoal(String topGoal) {
        this.topGoal = topGoal;
    }

    public String getTopAssist() {
        return topAssist;
    }

    public void setTopAssist(String topAssist) {
        this.topAssist = topAssist;
    }

    public String getStadium() {
        return stadium;
    }

    public void setStadium(String stadium) {
        this.stadium = stadium;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getFixture() {
        return fixture;
    }

    public void setFixture(String fixture) {
        this.fixture = fixture;
    }

    public ArrayList<Integer> getFixtures() {
        ArrayList<Integer> fixtures = new ArrayList<>();
        try {
            JSONArray jsonFixtures = new JSONArray(fixture);
            for (int i = 0; i < jsonFixtures.length(); i++){
                JSONObject jsonFixture = jsonFixtures.getJSONObject(i);
                fixtures.add(jsonFixture.getInt("id"));
            }
            return fixtures;
        } catch (JSONException e){
            e.printStackTrace();
        }
        return null;
    }

    @PrimaryKey
    @ColumnInfo(name = "ID")
    private Integer id;

    @ColumnInfo(name = "Name")
    private String name;

    @ColumnInfo(name = "Country")
    private String country;

    @ColumnInfo(name = "League")
    private String league;

    @ColumnInfo(name = "Rank")
    private Integer rank;

    @ColumnInfo(name = "TopRating")
    private String topRating;

    @ColumnInfo(name = "TopGoal")
    private String topGoal;

    @ColumnInfo(name = "TopAssist")
    private String topAssist;

    @ColumnInfo(name = "Stadium")
    private String stadium;

    @ColumnInfo(name = "City")
    private String city;

    @ColumnInfo(name = "Fixture")
    private String fixture;

    public Team(Integer id){
        this.id = id;
    }

    @Ignore
    public Team(String jsonString){
        try{
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONObject jsonDetails = jsonObject.getJSONObject("details");
            this.id = jsonDetails.getInt("id");
            this.name = jsonDetails.getString("name");
            this.country = jsonDetails.getString("country");
            fixture = jsonObject.getString("fixtures");
            JSONObject jsonTableData = jsonObject.getJSONObject("tableData");
            JSONArray jsonTables = jsonTableData.getJSONArray("tables");
            JSONObject jsonTablesElem = jsonTables.getJSONObject(0);
            this.league = jsonTablesElem.getString("leagueName");
            JSONArray jsonTable = jsonTablesElem.getJSONArray("table");
            for (int i = 0; i < jsonTable.length(); i++){
                JSONObject jsonTableElem = jsonTable.getJSONObject(i);
                if (id.equals(jsonTableElem.getInt("id"))){
                    rank = i + 1;
                    break;
                }
            }
            JSONObject jsonTopPlayers = jsonObject.getJSONObject("topPlayers");
            JSONArray jsonTopRating = jsonTopPlayers.getJSONArray("byRating");
            JSONArray jsonTopGoal = jsonTopPlayers.getJSONArray("byGoals");
            JSONArray jsonTopAssist = jsonTopPlayers.getJSONArray("byAssists");
            this.topRating = jsonTopRating.getJSONObject(0).getString("name");
            this.topGoal = jsonTopGoal.getJSONObject(0).getString("name");
            this.topAssist = jsonTopAssist.getJSONObject(0).getString("name");
            JSONObject jsonVenue = jsonObject.getJSONObject("venue");
            JSONObject jsonWidget = jsonVenue.getJSONObject("widget");
            this.stadium = jsonWidget.getString("name");
            this.city = jsonWidget.getString("city");
        } catch (JSONException e){
            e.printStackTrace();
        }
    }
}
