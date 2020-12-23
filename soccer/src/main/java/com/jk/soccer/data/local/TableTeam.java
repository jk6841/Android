package com.jk.soccer.data.local;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

@Entity(tableName = "tableTeam")

public class TableTeam {

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

    @ColumnInfo(name = "Bookmark")
    private Integer bookmark;

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

    public Integer getBookmark() {
        return bookmark;
    }

    public void setBookmark(Integer bookmark) {
        this.bookmark = bookmark;
    }

    public ArrayList<Integer> getFixtures() {
        ArrayList<Integer> fixtures = new ArrayList<>();
        try {
            JSONArray jsonFixtures = new JSONArray(fixture);
            for (int i = 0; i < jsonFixtures.length(); i++){
                fixtures.add(jsonFixtures.getInt(i));
            }
            return fixtures;
        } catch (JSONException e){
            e.printStackTrace();
        }
        return null;
    }

    public TableTeam(Integer id){
        this.id = id;
    }
}
