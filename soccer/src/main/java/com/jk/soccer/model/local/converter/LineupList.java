package com.jk.soccer.model.local.converter;

import androidx.room.TypeConverter;

import com.jk.soccer.etc.Lineup;
import com.jk.soccer.etc.MyJson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LineupList {

    @TypeConverter
    public static String lineupToString(List<Lineup> lineupList){
        if (lineupList == null)
            return "[]";
        JSONArray jsonArray = MyJson.myJSONArray("[]");
        if (jsonArray == null)
            return "";
        for (int i = 0; i < lineupList.size(); i++){
            Lineup lineup = lineupList.get(i);
            JSONObject jsonObject = MyJson.myJSONObject("{}");
            MyJson.myJSONPut(jsonObject, "position", lineup.getPosition());
            MyJson.myJSONPut(jsonObject, "shirt", lineup.getShirt());
            MyJson.myJSONPut(jsonObject, "name", lineup.getName());
            MyJson.myJSONPut(jsonArray, i, jsonObject);
        }
        return jsonArray.toString();
    }

    @TypeConverter
    public static List<Lineup> stringToLineup(String string){
        List<Lineup> lineupList = new ArrayList<>();
        JSONArray jsonArray = MyJson.myJSONArray(string);
        for (int i = 0; i < jsonArray.length(); i++){
            JSONObject jsonObject = MyJson.myJSONObject(jsonArray, i);
            Integer position = MyJson.myJSONInt(jsonObject, "position");
            Integer shirt = MyJson.myJSONInt(jsonObject, "shirt");
            String name = MyJson.myJSONString(jsonObject, "name");
            Lineup event = new Lineup(position, shirt, name);
            lineupList.add(event);
        }
        return lineupList;
    }
}
