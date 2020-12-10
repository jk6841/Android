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
    private final String unknownMsg = "알 수 없음";

    @PrimaryKey
    @ColumnInfo(name = "ID")
    private Integer id;

    @ColumnInfo(name = "Name")
    private String name;

    @ColumnInfo(name = "Position")
    private String position;

    @ColumnInfo(name = "Height")
    private String height;

    @ColumnInfo(name = "Foot")
    private String foot;

    @ColumnInfo(name = "Age")
    private Integer age;

    @ColumnInfo(name = "Shirt")
    private Integer shirt;

    @ColumnInfo(name = "TeamID")
    private Integer teamID;

    @ColumnInfo(name = "Bookmark")
    private boolean bookmark;

    public Player(int id){
        this.id = id;
    }

    @Ignore
    public Player(Integer id, String jsonString){
        this.id = id;
        position = "";
        height = "";
        foot = "";
        age = -1;
        shirt = -1;
        teamID = -1;
        try{
            JSONObject jsonObject = new JSONObject(jsonString);
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

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String printName(){
        return "이름: " + name;
    }

    public boolean isBookmark(){
        return bookmark;
    }

    public void setBookmark(boolean bookmark){
        this.bookmark = bookmark;
    }

    public Integer getTeamID(){
        return teamID;
    }

    public void setTeamID(Integer teamID){
        this.teamID = teamID;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position){
        this.position = position;
    }

    public String printPosition(){
        if (position.equals(""))
            return unknownMsg;
        return "포지션: " + position;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height){
        this.height = height;
    }

    public String printHeight(){
        if (height.equals(""))
            return unknownMsg;
        return "신장: " + height;
    }

    public String getFoot() {
        return foot;
    }

    public void setFoot(String foot){
        this.foot = foot;
    }

    public String printFoot(){
        if (foot.equals(""))
            return unknownMsg;
        return "주발: " + foot;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age){
        this.age = age;
    }

    public String printAge(){
        if (shirt == -1)
            return unknownMsg;
        return "나이: " + age + "세";
    }

    public Integer getShirt() {
        return shirt;
    }

    public void setShirt(Integer shirt) {
        this.shirt = shirt;
    }

    public String printShirt(){
        if (shirt == -1)
            return unknownMsg;
        return "등번호: " + shirt;
    }

}
