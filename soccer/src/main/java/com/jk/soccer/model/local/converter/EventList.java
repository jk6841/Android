package com.jk.soccer.model.local.converter;

import androidx.room.TypeConverter;

import com.jk.soccer.etc.Event;
import com.jk.soccer.etc.MyJson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EventList {

    @TypeConverter
    public static String eventToString(List<Event> eventList){
        JSONArray jsonArray = MyJson.myJSONArray("[]");
        if (jsonArray == null)
            return "";
        for (int i = 0; i < eventList.size(); i++){
            JSONObject jsonObject = MyJson.myJSONObject("{}");
            Event event = eventList.get(i);
            MyJson.myJSONPut(jsonObject, "time", event.getTime());
            MyJson.myJSONPut(jsonObject, "type", event.getType());
            MyJson.myJSONPut(jsonObject, "detail", event.getDetail());
            MyJson.myJSONPut(jsonObject, "home", event.getHome());
            MyJson.myJSONPut(jsonArray, i, jsonObject);
        }
        return jsonArray.toString();
    }

    @TypeConverter
    public static List<Event> stringToEvent(String string){
        List<Event> eventList = new ArrayList<>();
        JSONArray jsonArray = MyJson.myJSONArray(string);
        for (int i = 0; i < jsonArray.length(); i++){
            JSONObject jsonEvent = MyJson.myJSONObject(jsonArray, i);
            Event event = new Event();
            event.setTime(MyJson.myJSONInt(jsonEvent, "time"));
            event.setType(MyJson.myJSONString(jsonEvent, "type"));
            event.setDetail(MyJson.myJSONString(jsonEvent, "detail"));
            event.setHome(MyJson.myJSONBoolean(jsonEvent, "home"));
            eventList.add(event);
        }
        return eventList;
    }
}
