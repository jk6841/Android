package com.jk.soccer.data;

import com.jk.soccer.data.local.TableMatch;
import com.jk.soccer.data.local.TableTeam;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
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

    public static String myJSONString(JSONObject jsonObject, String string){
        try {
            return jsonObject.getString(string);
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
        JSONObject jsonInfoBox = myJSONObject(jsonMatchFacts, "infoBox");
        JSONObject jsonTournament = myJSONObject(jsonInfoBox, "Tournament");
        match.setName(myJSONString(jsonTournament, "text"));
        JSONObject jsonStadium = myJSONObject(jsonInfoBox, "Stadium");
        match.setStadium(myJSONString(jsonStadium, "name"));
        return match;
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
