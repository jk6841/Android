package com.jk.soccer.model.remote;

import com.jk.soccer.model.LeagueTableEntry;
import com.jk.soccer.model.Fixture;
import com.jk.soccer.model.League;
import com.jk.soccer.model.TopPlayer;
import com.jk.soccer.etc.json.MyJSONArray;
import com.jk.soccer.etc.json.MyJSONObject;
import com.jk.soccer.model.Player;
import com.jk.soccer.model.Team;
import com.jk.soccer.etc.enumeration.Type;
import com.jk.soccer.model.local.TableSearch;

import java.util.ArrayList;
import java.util.List;

public class Parser {

    public static League parseLeague(String jsonString){
        League league = new League();
        MyJSONObject jsonObject = new MyJSONObject(jsonString);
        MyJSONObject jsonDetails = jsonObject.getJSONObject("details");
        league.setID(jsonDetails.getInt("id"));
        league.setName(jsonDetails.getString("name"));
        MyJSONArray jsonFixtures = jsonObject.getJSONArray("fixtures");
        league.setFixtures(parseFixtures(jsonFixtures));
        MyJSONObject jsonTopPlayers = jsonObject.getJSONObject("topPlayers");
        MyJSONArray jsonTopGoal = jsonTopPlayers.getJSONArray("byGoals");
        league.setTopGoal(parseTopPlayer(jsonTopGoal, true));
        MyJSONArray jsonTopAssist = jsonTopPlayers.getJSONArray("byAssists");
        league.setTopAssist(parseTopPlayer(jsonTopAssist, false));
        MyJSONObject jsonTableData = jsonObject.getJSONObject("tableData");
        MyJSONArray jsonTable = jsonTableData.getJSONArray("tables")
                .getJSONObject(0).getJSONArray("table");
        league.setTable(parseLeagueTable(jsonTable));
        return league;
    }

    public static List<TableSearch> parseTeamList(String jsonString){
        MyJSONObject jsonObject = new MyJSONObject(jsonString);
        MyJSONObject jsonTableData = jsonObject.getJSONObject("tableData");
        MyJSONArray jsonTables = jsonTableData.getJSONArray("tables");
        MyJSONObject jsonTables2 = jsonTables.getJSONObject(0);
        Integer leagueID = jsonTables2.getInt("leagueId");
        MyJSONArray jsonTable = jsonTables2.getJSONArray("table");
        if (jsonTable != null){
            List<TableSearch> teamList = new ArrayList<>();
            for (int i = 0; i < jsonTable.length(); i++){
                MyJSONObject jsonTeam = jsonTable.getJSONObject(i);
                Integer teamID = jsonTeam.getInt("id");
                String teamName = jsonTeam.getString("name");
                teamList.add(new TableSearch(teamID, leagueID, Type.TEAM, teamName));
            }
            return teamList;
        }
        return null;
    }

    public static Team parseTeam(String jsonString){
        Team team = new Team();
        MyJSONObject jsonObject = new MyJSONObject(jsonString);
        MyJSONObject jsonDetails = jsonObject.getJSONObject("details");
        team.setID(jsonDetails.getString("id"));
        team.setName(jsonDetails.getString("name"));
        MyJSONArray jsonArray = jsonObject.getJSONArray("fixtures");
        team.setFixtures(parseFixtures(jsonArray));

        MyJSONObject jsonTopPlayers = jsonObject.getJSONObject("topPlayers");
        MyJSONArray jsonTopGoals = jsonTopPlayers.getJSONArray("byGoals");
        team.setTopGoal(parseTopPlayer(jsonTopGoals, true));
        MyJSONArray jsonTopAssists = jsonTopPlayers.getJSONArray("byAssists");
        team.setTopAssist(parseTopPlayer(jsonTopAssists, false));

        MyJSONObject jsonVenue = jsonObject.getJSONObject("venue");
        MyJSONObject jsonWidget = jsonVenue.getJSONObject("widget");
        team.setStadium(jsonWidget.getString("name"));
        team.setCity(jsonWidget.getString("city"));

        return team;
    }

    public static List<TableSearch> parsePlayerList(String jsonString){
        MyJSONObject jsonObject = new MyJSONObject(jsonString);
        MyJSONObject jsonDetails = jsonObject.getJSONObject("details");
        Integer teamID = jsonDetails.getInt("id");
        MyJSONArray jsonSquad = jsonObject.getJSONArray("squad");
        if (jsonSquad != null){
            List<TableSearch> playerList = new ArrayList<>();
            for (int i = 0; i < jsonSquad.length(); i++){
                MyJSONArray jsonSquadItem = jsonSquad.getJSONArray(i).getJSONArray(1);
                if (jsonSquadItem != null){
                    for (int j = 0; j < jsonSquadItem.length(); j++){
                        MyJSONObject jsonPlayer = jsonSquadItem.getJSONObject(j);
                        Integer playerID = jsonPlayer.getInt("id");
                        String name = jsonPlayer.getString("name");
                        playerList.add(new TableSearch(playerID, teamID, Type.PERSON, name));
                    }
                }
            }
            return playerList;
        }
        return null;
    }

    public static Player parsePlayer(String jsonString){
        Player player = new Player();
        MyJSONObject jsonObject = new MyJSONObject(jsonString);
        player.setName(jsonObject.getString("name"));
        MyJSONObject jsonOrigin = jsonObject.getJSONObject("origin");
        player.setTeamID(jsonOrigin.getString("teamId"));
        player.setTeamName(jsonOrigin.getString("teamName"));
        MyJSONObject jsonPositionDesc = jsonOrigin.getJSONObject("positionDesc");
        player.setPosition(jsonPositionDesc.getString("primaryPosition"));
        MyJSONArray jsonPlayerProps = jsonObject.getJSONArray("playerProps");
        for (int i = 0; i < jsonPlayerProps.length(); i++){
            MyJSONObject jsonItem = jsonPlayerProps.getJSONObject(i);
            String item = jsonItem.getString("value");
            switch (jsonItem.getString("title")){
                case "Height":
                    player.setHeight(item);
                    break;
                case "Preferred foot":
                    player.setFoot(item);
                    break;
                case "Age":
                    player.setAge(item);
                    break;
                case "Country":
                    player.setCountryName(item);
                    MyJSONObject jsonIcon = jsonItem.getJSONObject("icon");
                    player.setCountryID(jsonIcon.getString("id").toLowerCase());
                    break;
                case "Shirt":
                    player.setShirt(item);
                    break;
                default:
                    break;
            }
        }
        return player;
    }

    private static List<Fixture> parseFixtures(MyJSONArray jsonFixtures){
        List<Fixture> fixtures = new ArrayList<>();
        for (int i = 0 ; i < jsonFixtures.length(); i++){
            MyJSONObject jsonFixture = jsonFixtures.getJSONObject(i);
            Fixture fixture = new Fixture();
            fixture.setID(jsonFixture.getInt("id"));
            MyJSONObject jsonHome = jsonFixture.getJSONObject("home");
            MyJSONObject jsonAway = jsonFixture.getJSONObject("away");
            fixture.setHomeID(jsonHome.getInt("id"));
            fixture.setHomeName(jsonHome.getString("name"));
            fixture.setAwayID(jsonAway.getInt("id"));
            fixture.setAwayName(jsonAway.getString("name"));
            fixture.setColor(jsonFixture.getString("color"));
            MyJSONObject jsonStatus = jsonFixture.getJSONObject("status");
            Boolean started = jsonStatus.getBoolean("started");
            Boolean cancelled = jsonStatus.getBoolean("cancelled");
            Boolean finished = jsonStatus.getBoolean("finished");
            if (cancelled){
                fixture.setStatus(match_cancelled);
            } else if (finished) {
                fixture.setStatus(match_finished);
            } else if (started) {
                fixture.setStatus(match_ing);
            } else {
                fixture.setStatus(match_yet);
            }
            fixture.setScore(jsonStatus.getString("scoreStr"));
            fixture.setDate(jsonStatus.getString("startDateStr"));
            fixtures.add(fixture);
        }
        return fixtures;
    }

    private static List<TopPlayer> parseTopPlayer(MyJSONArray jsonTopPlayer, Boolean isGoal){
        List<TopPlayer> topPlayers = new ArrayList<>();
        for (int i = 0; i < jsonTopPlayer.length(); i++){
            TopPlayer p = new TopPlayer();
            MyJSONObject jsonItem = jsonTopPlayer.getJSONObject(i);
            p.setID(jsonItem.getInt("id"));
            p.setName(jsonItem.getString("name"));
            p.setVal(isGoal? jsonItem.getInt("goals") : jsonItem.getInt("assists"));
            String teamID = jsonItem.getString("teamId");
            if (teamID == null){
                String ccode = jsonItem.getString("ccode");
                if (ccode != null) {
                    p.setParentID(ccode.toLowerCase());
                    p.setParentName(jsonItem.getString("cname"));
                    p.setParentType(Type.COUNTRY);
                }
            } else {
                p.setParentID(teamID);
                p.setParentName(jsonItem.getString("teamName"));
                p.setParentType(Type.TEAM);
            }
            topPlayers.add(p);
        }
        return topPlayers;
    }

    private static List<LeagueTableEntry> parseLeagueTable(MyJSONArray jsonTable){
        List<LeagueTableEntry> leagueTable = new ArrayList<>();
        for (int i = 0; i < jsonTable.length(); i++){
            LeagueTableEntry entry = new LeagueTableEntry();
            MyJSONObject jsonItem = jsonTable.getJSONObject(i);
            entry.setRank(jsonItem.getInt("idx"));
            entry.setTeamID(jsonItem.getInt("id"));
            entry.setTeamName(jsonItem.getString("name"));
            entry.setRound(jsonItem.getInt("played"));
            entry.setWin(jsonItem.getInt("wins"));
            entry.setDraw(jsonItem.getInt("draws"));
            entry.setLose(jsonItem.getInt("losses"));
            entry.setScoreString(jsonItem.getString("scoresStr"));
            entry.setGoalDiff(jsonItem.getInt("goalConDiff"));
            entry.setPoint(jsonItem.getInt("pts"));
            leagueTable.add(entry);
        }
        return leagueTable;
    }

    final static String match_cancelled = "경기 취소";
    final static String match_finished = "경기 종료";
    final static String match_ing = "경기 중";
    final static String match_yet = "경기 시작 전";

}
