package com.jk.app.data.local;

import android.graphics.Bitmap;

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

    @ColumnInfo(name = "Image")
    private Bitmap image;

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

    @Ignore
    public Team(Integer id, Bitmap image){
        this.id = id;
        this.image = image;
    }

    public Integer getId(){
        return this.id;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getColor(){
        return this.color;
    }

    public void setColor(String color){
        this.color = color;
    }

    public Bitmap getImage(){
        return this.image;
    }

    public void setImage(Bitmap image){
        this.image = image;
    }

}
