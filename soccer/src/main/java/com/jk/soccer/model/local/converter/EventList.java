package com.jk.soccer.model.local.converter;

import androidx.room.TypeConverter;

import com.jk.soccer.etc.Event;
import com.jk.soccer.etc.MyParser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EventList {

    @TypeConverter
    public static String eventToString(List<Event> eventList){
        JSONArray jsonArray = MyParser.myJSONArray("[]");
        if (jsonArray == null)
            return "";
        for (int i = 0; i < eventList.size(); i++){
            JSONObject jsonObject = MyParser.myJSONObject("{}");
            Event event = eventList.get(i);
            MyParser.myJSONPut(jsonObject, "time", event.getTime());
            MyParser.myJSONPut(jsonObject, "type", event.getType());
            MyParser.myJSONPut(jsonObject, "detail", event.getDetail());
            MyParser.myJSONPut(jsonObject, "home", event.getHome());
            MyParser.myJSONPut(jsonArray, i, jsonObject);
        }
        return jsonArray.toString();
    }

    @TypeConverter
    public static List<Event> stringToEvent(String string){
        List<Event> eventList = new ArrayList<>();
        JSONArray jsonArray = MyParser.myJSONArray(string);
        for (int i = 0; i < jsonArray.length(); i++){
            JSONObject jsonEvent = MyParser.myJSONObject(jsonArray, i);
            Event event = new Event();
            event.setTime(MyParser.myJSONInt(jsonEvent, "time"));
            event.setType(MyParser.myJSONString(jsonEvent, "type"));
            event.setDetail(MyParser.myJSONString(jsonEvent, "detail"));
            event.setHome(MyParser.myJSONBoolean(jsonEvent, "home"));
            eventList.add(event);
        }
        return eventList;
    }
}
