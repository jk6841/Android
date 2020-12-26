package com.jk.soccer.model.local.converter;

import androidx.room.TypeConverter;

import com.jk.soccer.etc.Lineup;
import com.jk.soccer.model.local.MyParser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LineupList {

    @TypeConverter
    public static String lineupToString(List<Lineup> lineupList){
        if (lineupList == null)
            return "[]";
        JSONArray jsonArray = MyParser.myJSONArray("[]");
        if (jsonArray == null)
            return "";
        for (int i = 0; i < lineupList.size(); i++){
            Lineup lineup = lineupList.get(i);
            JSONObject jsonObject = MyParser.myJSONObject("{}");
            MyParser.myJSONPut(jsonObject, "position", lineup.getPosition());
            MyParser.myJSONPut(jsonObject, "shirt", lineup.getShirt());
            MyParser.myJSONPut(jsonObject, "name", lineup.getName());
            MyParser.myJSONPut(jsonArray, i, jsonObject);
        }
        return jsonArray.toString();
    }

    @TypeConverter
    public static List<Lineup> stringToLineup(String string){
        List<Lineup> lineupList = new ArrayList<>();
        JSONArray jsonArray = MyParser.myJSONArray(string);
        for (int i = 0; i < jsonArray.length(); i++){
            JSONObject jsonObject = MyParser.myJSONObject(jsonArray, i);
            Integer position = MyParser.myJSONInt(jsonObject, "position");
            Integer shirt = MyParser.myJSONInt(jsonObject, "shirt");
            String name = MyParser.myJSONString(jsonObject, "name");
            Lineup event = new Lineup(position, shirt, name);
            lineupList.add(event);
        }
        return lineupList;
    }
}
