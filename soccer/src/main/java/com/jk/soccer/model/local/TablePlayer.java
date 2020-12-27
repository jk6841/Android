package com.jk.soccer.model.local;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@Entity (tableName = "tablePlayer")

public class TablePlayer implements Comparable<TablePlayer>{

    @Ignore
    private final String unknownMsg = "";

    @PrimaryKey
    @ColumnInfo(name = "ID")
    private Integer id;

    @ColumnInfo(name = "Name")
    private String name = unknownMsg;

    @ColumnInfo(name = "Position")
    private String position = unknownMsg;

    @ColumnInfo(name = "Height")
    private Integer height = 0;

    @ColumnInfo(name = "Foot")
    private String foot = unknownMsg;

    @ColumnInfo(name = "Birth")
    private String birth = "";

    @ColumnInfo(name = "Shirt")
    private Integer shirt = 0;

    @ColumnInfo(name = "TeamID")
    private Integer teamID = 0;

    @ColumnInfo(name = "Bookmark")
    private Boolean bookmark = false;

    public TablePlayer(int id){
        this.id = id;
    }

    public int compareTo(TablePlayer player){
        Boolean l1 = this.bookmark;
        Boolean l2 = player.bookmark;

        if (l1 && !l2){
            return -1;
        }
        else if (!l1 && l2){
            return 1;
        }
        else{
            return this.name.compareTo(player.name);
        }
    }

    public Integer getId(){
        return id;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position){
        this.position = position;
    }

    public String printPosition(){
        if (position == null)
            return unknownMsg;
        String[] splited = position.split(" ");
        if (splited.length == 1)
            return position;
        String ret = "";
        for (int i = 0; i < splited.length; i++)
            ret += splited[i].substring(0, 1).toUpperCase();
        if (position == null)
            return unknownMsg;
        return ret;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getFoot() {
        return foot;
    }

    public void setFoot(String foot){
        this.foot = foot;
    }

    public String printFoot(){
        if (foot == null)
            return unknownMsg;
        switch (foot){
            case "right":
                return "오른발";
            case "left":
                return "왼발";
            case "both":
                return "양발";
            default:
                return unknownMsg;
        }
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public Integer getShirt() {
        return shirt;
    }

    public void setShirt(Integer shirt) {
        this.shirt = shirt;
    }

    public String printShirt(){
        if (shirt == null)
            return unknownMsg;
        if (shirt == -1)
            return unknownMsg;
        return shirt.toString();
    }

    public Integer getTeamID(){
        return teamID;
    }

    public void setTeamID(Integer teamID){
        this.teamID = teamID;
    }

    public Boolean getBookmark() {
        return bookmark;
    }

    public void setBookmark(Boolean bookmark){
        this.bookmark = bookmark;
    }

}
