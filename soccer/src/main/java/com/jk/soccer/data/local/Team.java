package com.jk.soccer.data.local;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.json.JSONException;
import org.json.JSONObject;

@Entity(tableName = "tableTeam")

public class Team {

    @PrimaryKey
    @ColumnInfo(name = "ID")
    private Integer id;

    @ColumnInfo(name = "Name")
    private String name;

    @ColumnInfo(name = "Color")
    private String color;

    @ColumnInfo(name = "Bookmark")
    private boolean bookmark;

    public Team(Integer id){
        this.id = id;
    }

    @Ignore
    public Team(Integer id, String jsonString){
        this.id = id;
        try{
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONObject jsonOrigin = jsonObject.getJSONObject("origin");
            this.name = jsonOrigin.getString("teamName");
            this.color = jsonOrigin.getString("teamColor");
        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    public Integer getId(){
        return id;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getColor(){
        return color;
    }

    public void setColor(String color){
        this.color = color;
    }

    public boolean isBookmark() {
        return bookmark;
    }

    public void setBookmark(boolean bookmark) {
        this.bookmark = bookmark;
    }

}
