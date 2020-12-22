package com.jk.soccer.data.local;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@Entity (tableName = "tablePlayer")

public class Player implements Comparable<Player>{

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
    private String height = unknownMsg;

    @ColumnInfo(name = "Foot")
    private String foot = unknownMsg;

    @ColumnInfo(name = "Age")
    private Integer age = 0;

    @ColumnInfo(name = "Shirt")
    private Integer shirt = 0;

    @ColumnInfo(name = "TeamID")
    private Integer teamID = 0;

    @ColumnInfo(name = "Bookmark")
    private boolean bookmark = false;

    public Player(int id){
        this.id = id;
    }

    @Ignore
    public Player(String jsonString){
        try{
            JSONObject jsonObject = new JSONObject(jsonString);
            id = jsonObject.getInt("id");
            JSONObject jsonOrigin = jsonObject.getJSONObject("origin");
            teamID = jsonOrigin.getInt("teamId");
            JSONObject jsonPositionDesc = jsonOrigin.getJSONObject("positionDesc");
            position = jsonPositionDesc.getString("primaryPosition");
            JSONArray jsonPlayerProps = jsonObject.getJSONArray("playerProps");
            for (int i = 0; i < jsonPlayerProps.length(); i++){
                JSONObject jsonElem = jsonPlayerProps.getJSONObject(i);
                height = jsonElem.getString("title") .equals("Height") ?
                        jsonElem.getString("value") : height;
                foot = jsonElem.getString("title").equals("Preferred foot") ?
                        jsonElem.getString("value") : foot;
                age = jsonElem.getString("title") .equals("Age") ?
                        jsonElem.getInt("value") : age;
                shirt = jsonElem.getString("title") .equals("Shirt") ?
                        jsonElem.getInt("value") : shirt;
            }
        } catch(JSONException e){
            e.printStackTrace();
        }
    }

    public int compareTo(Player player){
        boolean l1 = this.bookmark;
        boolean l2 = player.bookmark;

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

    public String getHeight() {
        return height;
    }

    public void setHeight(String height){
        this.height = height;
    }

    public String printHeight(){
        if (height == null)
            return unknownMsg;
        return height;
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age){
        this.age = age;
    }

    public String printAge(){
        if (shirt == null)
            return unknownMsg;
        return age + "세";
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

    public boolean isBookmark(){
        return bookmark;
    }

    public void setBookmark(boolean bookmark){
        this.bookmark = bookmark;
    }

}
