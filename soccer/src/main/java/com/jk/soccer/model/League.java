package com.jk.soccer.model;

import com.jk.soccer.etc.LeagueTableEntry;

import java.util.List;

public class League {

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<LeagueTableEntry> getTable() {
        return table;
    }

    public void setTable(List<LeagueTableEntry> table) {
        this.table = table;
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

    private Integer ID;

    private String name;

    private List<LeagueTableEntry> table;

    private List<Fixture> fixtures;

    private List<TopPlayer> topGoal;

    private List<TopPlayer> topAssist;

}
