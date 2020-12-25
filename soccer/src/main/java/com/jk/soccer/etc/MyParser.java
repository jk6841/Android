package com.jk.soccer.etc;

import com.jk.soccer.model.local.TableMatch;
import com.jk.soccer.model.local.TableTeam;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MyParser {

    final private static SimpleDateFormat inputDateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
    final private static SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.KOREA);
    final private static SimpleDateFormat monthFormat = new SimpleDateFormat("M", Locale.KOREA);
    final private static SimpleDateFormat dateFormat = new SimpleDateFormat("d", Locale.KOREA);
    final private static SimpleDateFormat dayFormat = new SimpleDateFormat("E요일 ", Locale.KOREA);
    final private static SimpleDateFormat inputTimeFormat = new SimpleDateFormat("HH:mm", Locale.US);
    final private static SimpleDateFormat timeFormat = new SimpleDateFormat("H시 m분", Locale.KOREA);

    public static JSONObject myJSONObject(JSONObject jsonObject, String string){
        try{
            return jsonObject.getJSONObject(string);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static JSONObject myJSONObject(String string){
        try{
            return new JSONObject(string);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static JSONObject myJSONObject(JSONArray jsonArray, Integer index){
        try{
            return jsonArray.getJSONObject(index);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static JSONArray myJSONArray(JSONObject jsonObject, String string){
        try {
            return jsonObject.getJSONArray(string);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static JSONArray myJSONArray(JSONArray jsonArray, Integer index){
        try{
            return jsonArray.getJSONArray(index);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static String myJSONString(JSONObject jsonObject, String string){
        try {
            return jsonObject.getString(string);
        } catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    public static String myJSONString(JSONArray jsonArray, Integer index){
        try{
            return jsonArray.getString(index);
        } catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    public static Integer myJSONInt(JSONObject jsonObject, String string){
        try{
            return jsonObject.getInt(string);
        } catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    public static Boolean myJSONBoolean(JSONObject jsonObject, String string){
        try{
            return jsonObject.getBoolean(string);
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public static void myJSONPut(JSONArray jsonArray, Integer index, Object value){
        try{
            jsonArray.put(index, value);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static TableTeam myJSONTeam(String jsonString, Integer ID){
        TableTeam team = new TableTeam(ID);
        JSONObject jsonObject = myJSONObject(jsonString);
        JSONArray jsonFixtures = myJSONArray(jsonObject,"fixtures");
        for (int i = 0; i < jsonFixtures.length(); i++){
            JSONObject jsonFixture = myJSONObject(jsonFixtures, i);
            jsonFixture.remove("pageUrl");
            jsonFixture.remove("home");
            jsonFixture.remove("away");
            jsonFixture.remove("color");
            jsonFixture.remove("status");
            jsonFixture.remove("notStarted");
            myJSONPut(jsonFixtures, i, myJSONInt(jsonFixture, "id"));
        }
        team.setFixture(jsonFixtures.toString());
        JSONObject jsonTableData = myJSONObject(jsonObject, "tableData");
        JSONArray jsonTables = myJSONArray(jsonTableData, "tables");
        JSONObject jsonTablesElem = myJSONObject(jsonTables, 0);
        JSONArray jsonTable = myJSONArray(jsonTablesElem, "table");
        for (int i = 0; i < jsonTable.length(); i++){
            JSONObject jsonTableElem = myJSONObject(jsonTable, i);
            if (ID.equals(myJSONInt(jsonTableElem, "id"))){
                team.setRank(i + 1);
                break;
            }
        }
        JSONObject jsonTopPlayers = myJSONObject(jsonObject,"topPlayers");
        JSONArray jsonTopRating = myJSONArray(jsonTopPlayers,"byRating");
        JSONArray jsonTopGoal = myJSONArray(jsonTopPlayers,"byGoals");
        JSONArray jsonTopAssist = myJSONArray(jsonTopPlayers,"byAssists");
        team.setTopRating(myJSONString(myJSONObject(jsonTopRating,0),"name"));
        team.setTopGoal(myJSONString(myJSONObject(jsonTopGoal,0),"name"));
        team.setTopAssist(myJSONString(myJSONObject(jsonTopAssist,0),"name"));
        return team;
    }

    public static TableMatch myJSONMatch(String jsonString, Integer ID){
        TableMatch match = new TableMatch(ID);
        JSONObject jsonObject = myJSONObject(jsonString);
        JSONObject jsonHeader = myJSONObject(jsonObject, "header");
        JSONArray jsonTeams = myJSONArray(jsonHeader, "teams");
        JSONObject jsonHome = myJSONObject(jsonTeams, 0);
        JSONObject jsonAway = myJSONObject(jsonTeams, 1);
        match.setHomeName(myJSONString(jsonHome,"name"));
        match.setHomeScore(myJSONInt(jsonHome,"score"));
        match.setHomeImage(myJSONString(jsonHome,"imageUrl"));
        match.setHomeId(Integer.parseInt(match.getHomeImage().replaceAll("[^\\d]", "")));
        match.setAwayName(myJSONString(jsonAway,"name"));
        match.setAwayScore(myJSONInt(jsonAway,"score"));
        match.setAwayImage(myJSONString(jsonAway,"imageUrl"));
        match.setAwayId(Integer.parseInt(match.getAwayImage().replaceAll("[^\\d]", "")));
        JSONObject jsonStatus = myJSONObject(jsonHeader,"status");
        match.setStarted(myJSONBoolean(jsonStatus,"started"));
        match.setCancelled(myJSONBoolean(jsonStatus,"cancelled"));
        match.setFinished(myJSONBoolean(jsonStatus, "finished"));
        String DateStr = myJSONString(jsonStatus, "startDateStr");
        Date dateValue = myDate(inputDateFormat, DateStr);
        match.setYear(Integer.parseInt(yearFormat.format(dateValue)));
        match.setMonth(Integer.parseInt(monthFormat.format(dateValue)));
        match.setDate(Integer.parseInt(dateFormat.format(dateValue)));
        match.setDay(dayFormat.format(match.getDate()));
        match.setTime(myJSONString(jsonStatus, "startTimeStr"));
        if (!match.getTime().equals("")) {
            Date date2 = myDate(inputTimeFormat, match.getTime());
            match.setTime(timeFormat.format(date2));
        }
        JSONObject jsonContent = myJSONObject(jsonObject, "content");
        JSONObject jsonMatchFacts = myJSONObject(jsonContent,"matchFacts");
        match.setId(myJSONInt(jsonMatchFacts, "matchId"));
        JSONObject jsonBestPlayer = myJSONObject(jsonMatchFacts, "playerOfTheMatch");
        match.setBestPlayerID(myJSONInt(jsonBestPlayer, "id"));
        match.setBestPlayerName(myJSONString(jsonBestPlayer, "name"));
        match.setBestTeam(myJSONString(jsonBestPlayer, "teamName"));
        match.setEvent(myJSONString(jsonMatchFacts, "events"));
        JSONObject jsonInfoBox = myJSONObject(jsonMatchFacts, "infoBox");
        JSONObject jsonTournament = myJSONObject(jsonInfoBox, "Tournament");
        match.setName(myJSONString(jsonTournament, "text"));
        JSONObject jsonStadium = myJSONObject(jsonInfoBox, "Stadium");
        match.setStadium(myJSONString(jsonStadium, "name"));
        JSONObject jsonLineups = myJSONObject(jsonContent, "lineup");
        JSONArray jsonLineupArray = myJSONArray(jsonLineups, "lineup");
        match.setHomeLineup(myJSONString(jsonLineupArray, 0));
        match.setAwayLineup(myJSONString(jsonLineupArray, 1));
        return match;
    }

    public static ArrayList<Event> myEventList(String jsonString){
        ArrayList<Event> eventList = new ArrayList<>();
        JSONObject jsonObject = myJSONObject(jsonString);
        JSONArray jsonEventArray = myJSONArray(jsonObject, "events");
        for (int i = 0; i < jsonEventArray.length(); i++){
            Event event = new Event();
            JSONObject jsonEvent = myJSONObject(jsonEventArray, i);
            event.setTime(myJSONInt(jsonEvent, "time"));
            String type = myJSONString(jsonEvent, "type");
            if (type.equals("AddedTime"))
                continue;
            event.setHome(myJSONBoolean(jsonEvent, "isHome"));
            String detail = "";
            if (type.equals("Goal")) {
                detail = myJSONString(jsonEvent, "nameStr");
            } else if (type.equals("Substitution")) {
                JSONArray jsonSwap = myJSONArray(jsonEvent, "swap");
                JSONObject jsonIn = myJSONObject(jsonSwap, 0);
                JSONObject jsonOut = myJSONObject(jsonSwap, 1);
                detail = "Out: " + myJSONString(jsonOut, "name")
                        + "\n" + "In: " + myJSONString(jsonIn, "name");
            } else if (type.equals("Card")) {
                type = myJSONString(jsonEvent, "card").equals("Yellow")? "Yellow" : "Red";
                detail = myJSONString(jsonEvent, "nameStr");
            }
            event.setType(type);
            event.setDetail(detail);
            eventList.add(event);
        }
        return eventList;
    }

    private static Integer myPosition(String position){
        if (position.equals("Keeper")){
            return 1;
        }
        if (position.equals("Defender")){
            return 2;
        }
        if (position.equals("Midfielder")){
            return 3;
        }
        return 4;
    }

    public static ArrayList<Lineup> myLineup(String jsonString, Boolean home){
        ArrayList<Lineup> lineup = new ArrayList<>();
        JSONObject jsonObject = myJSONObject(jsonString);
        JSONArray coachArray = myJSONArray(jsonObject, "coach");
        JSONObject jsonCoach = myJSONObject(coachArray, 0);
        lineup.add(new Lineup(0, 0, myJSONString(jsonCoach, "name")));
        JSONArray startingArray = myJSONArray(jsonObject, "players");
        if (startingArray == null)
            return null;
        for (int i = home? 0 : startingArray.length() - 1;
             i < startingArray.length() && i >= 0;
             i = home? i + 1 : i - 1){
            JSONArray tmpArray = myJSONArray(startingArray, i);
            for (int j = 0; j < tmpArray.length(); j++){
                JSONObject jsonPlayer = myJSONObject(tmpArray, j);
                lineup.add(new Lineup(myPosition(myJSONString(jsonPlayer, "role")),
                        myJSONInt(jsonPlayer, "shirt"),
                        myJSONString(jsonPlayer, "name")));
            }
        }
        JSONArray benchArray = myJSONArray(jsonObject, "bench");
        for (int i = 0; i < benchArray.length(); i++){
            JSONObject jsonSub = myJSONObject(benchArray, i);
            lineup.add(new Lineup(myPosition(myJSONString(jsonSub, "role")),
                    myJSONInt(jsonSub, "shirt"),
                    myJSONString(jsonSub, "name")));
        }
        return lineup;
    }

    public static Date myDate(SimpleDateFormat format, String text){
        try {
            return format.parse(text);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
