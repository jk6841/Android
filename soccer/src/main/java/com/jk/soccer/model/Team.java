package com.jk.soccer.model;

import java.util.List;

public class Team {

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public List<Fixture> getFixtures() {
        return fixtures;
    }

    public void setFixtures(List<Fixture> fixtures) {
        this.fixtures = fixtures;
    }

    public List<TopPlayer> getTopGoal() {
        return topGoal;
    }

    public void setTopGoal(List<TopPlayer> topGoal) {
        this.topGoal = topGoal;
    }

    public List<TopPlayer> getTopAssist() {
        return topAssist;
    }

    public void setTopAssist(List<TopPlayer> topAssist) {
        this.topAssist = topAssist;
    }

    private String ID;

    private String name;

    private String stadium;

    private String city;

    private List<Fixture> fixtures;

    private List<TopPlayer> topGoal;

    private List<TopPlayer> topAssist;

}
