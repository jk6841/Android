package com.jk.soccer.etc;

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

    public static class Fixture{
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

        private Integer ID;
        private Integer homeID;
        private String homeName;
        private Integer awayID;
        private String awayName;
        private String date;
        private String score;
        private String color;
        private Boolean started;
        private Boolean cancelled;
        private Boolean finished;
    }

    public static class TopPlayer{
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

        public Integer getGoal() {
            return goal;
        }

        public void setGoal(Integer goal) {
            this.goal = goal;
        }

        public Integer getAssist() {
            return assist;
        }

        public void setAssist(Integer assist) {
            this.assist = assist;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        private Integer ID;
        private String name;
        private Integer goal;
        private Integer assist;
        private String country;
    }


}
