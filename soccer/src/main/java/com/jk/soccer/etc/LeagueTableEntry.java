package com.jk.soccer.etc;

public class LeagueTableEntry {

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public Integer getRound() {
        return round;
    }

    public void setRound(Integer round) {
        this.round = round;
    }

    public Integer getWin() {
        return win;
    }

    public void setWin(Integer win) {
        this.win = win;
    }

    public Integer getDraw() {
        return draw;
    }

    public void setDraw(Integer draw) {
        this.draw = draw;
    }

    public Integer getLose() {
        return lose;
    }

    public void setLose(Integer lose) {
        this.lose = lose;
    }

    public String getScoreString() {
        return scoreString;
    }

    public void setScoreString(String scoreString) {
        this.scoreString = scoreString;
    }

    public Integer getGoalDiff() {
        return goalDiff;
    }

    public void setGoalDiff(Integer goalDiff) {
        this.goalDiff = goalDiff;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    private Integer rank;
    private String team;
    private Integer round;
    private Integer win;
    private Integer draw;
    private Integer lose;
    private String scoreString;
    private Integer goalDiff;
    private Integer point;

}
