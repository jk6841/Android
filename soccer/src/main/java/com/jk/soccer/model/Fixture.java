package com.jk.soccer.model;

public class Fixture{
    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Integer getHomeID() {
        return homeID;
    }

    public void setHomeID(Integer homeID) {
        this.homeID = homeID;
    }

    public String getHomeName() {
        return homeName;
    }

    public void setHomeName(String homeName) {
        this.homeName = homeName;
    }

    public Integer getAwayID() {
        return awayID;
    }

    public void setAwayID(Integer awayID) {
        this.awayID = awayID;
    }

    public String getAwayName() {
        return awayName;
    }

    public void setAwayName(String awayName) {
        this.awayName = awayName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private Integer ID;
    private Integer homeID;
    private String homeName;
    private Integer awayID;
    private String awayName;
    private String date;
    private String score;
    private String color;
    private String status;
}