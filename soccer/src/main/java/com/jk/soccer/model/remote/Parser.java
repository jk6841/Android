package com.jk.soccer.model.remote;

import com.jk.soccer.etc.json.MyJSONArray;
import com.jk.soccer.etc.json.MyJSONObject;
import com.jk.soccer.etc.Player;
import com.jk.soccer.etc.Team;
import com.jk.soccer.etc.enumeration.Type;
import com.jk.soccer.model.local.TableSearch;

import java.util.ArrayList;
import java.util.List;

public class Parser {

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
        List<Team.Fixture> fixtures = new ArrayList<>();
        for (int i = 0 ; i < jsonArray.length(); i++){
            MyJSONObject jsonFixture = jsonArray.getJSONObject(i);
            Team.Fixture fixture = new Team.Fixture();
            fixture.setID(jsonFixture.getInt("id"));
            MyJSONObject jsonHome = jsonFixture.getJSONObject("home");
            MyJSONObject jsonAway = jsonFixture.getJSONObject("away");
            fixture.setHomeID(jsonHome.getInt("id"));
            fixture.setHomeName(jsonHome.getString("name"));
            fixture.setAwayID(jsonAway.getInt("id"));
            fixture.setAwayName(jsonAway.getString("name"));
            fixture.setColor(jsonFixture.getString("color"));
            MyJSONObject jsonStatus = jsonFixture.getJSONObject("status");
            fixture.setStarted(jsonStatus.getBoolean("started"));
            fixture.setCancelled(jsonStatus.getBoolean("cancelled"));
            fixture.setFinished(jsonStatus.getBoolean("finished"));
            fixture.setScore(jsonStatus.getString("scoreStr"));
            fixture.setDate(jsonStatus.getString("startDateStr"));
            fixtures.add(fixture);
        }
        team.setFixtures(fixtures);

        MyJSONObject jsonTopPlayers = jsonObject.getJSONObject("topPlayers");

        MyJSONArray jsonTopGoals = jsonTopPlayers.getJSONArray("byGoals");
        List<Team.TopPlayer> topGoal = new ArrayList<>();
        for (int i = 0; i < jsonTopGoals.length(); i++){
            Team.TopPlayer p = new Team.TopPlayer();
            MyJSONObject jsonItem = jsonTopGoals.getJSONObject(i);
            p.setID(jsonItem.getInt("id"));
            p.setName(jsonItem.getString("name"));
            p.setGoal(jsonItem.getInt("goals"));
            p.setAssist(jsonItem.getInt("assists"));
            p.setCountry(jsonItem.getString("ccode").toLowerCase());
            topGoal.add(p);
        }
        team.setTopGoal(topGoal);

        List<Team.TopPlayer> topAssist = new ArrayList<>();
        MyJSONArray jsonTopAssists = jsonTopPlayers.getJSONArray("byAssists");
        for (int i = 0; i < jsonTopAssists.length(); i++){
            Team.TopPlayer p = new Team.TopPlayer();
            MyJSONObject jsonItem = jsonTopAssists.getJSONObject(i);
            p.setID(jsonItem.getInt("id"));
            p.setName(jsonItem.getString("name"));
            p.setGoal(jsonItem.getInt("goals"));
            p.setAssist(jsonItem.getInt("assists"));
            p.setCountry(jsonItem.getString("ccode").toLowerCase());
            topAssist.add(p);
        }
        team.setTopAssist(topAssist);

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

}
