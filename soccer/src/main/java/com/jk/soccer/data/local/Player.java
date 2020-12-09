package com.jk.soccer.data.local;

import android.graphics.Bitmap;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/*@Entity(tableName = "tablePlayer",
        foreignKeys = @ForeignKey(
                entity = Team.class,
                parentColumns = "Id",
                childColumns = "TeamId",
                onDelete = ForeignKey.CASCADE
        )
)*/

@Entity (tableName = "tablePlayer")

public class Player implements Comparable<Player>{

    @PrimaryKey
    @ColumnInfo(name = "ID")
    private Integer id;

    @ColumnInfo(name = "Name")
    private String name;

    @ColumnInfo(name = "Bookmark")
    private boolean bookmark;

    @ColumnInfo(name = "Image")
    private Bitmap image;

    @ColumnInfo(name = "TeamID")
    private Integer teamID;

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
            //JSONObject jsonPositions = jsonPositionDesc.getJSONObject("positions");
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

    @Ignore
    public Player(Integer id, Bitmap image){
        this.id = id;
        this.image = image;
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

    public boolean isBookmark(){
        return this.bookmark;
    }

    public void setBookmark(boolean bookmark){
        this.bookmark = bookmark;
    }

    public Bitmap getImage(){
        return this.image;
    }

    public void setImage(Bitmap image){
        this.image = image;
    }

    public Integer getTeamID(){
        return this.teamID;
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

    public String getHeight() {
        return this.height;
    }

    public void setHeight(String height){
        this.height = height;
    }

    public String getFoot() {
        return this.foot;
    }

    public void setFoot(String foot){
        this.foot = foot;
    }

    public Integer getAge() {
        return this.age;
    }

    public void setAge(Integer age){
        this.age = age;
    }

    public Integer getShirt() {
        return this.shirt;
    }

    public void setShirt(Integer shirt) {
        this.shirt = shirt;
    }


}
