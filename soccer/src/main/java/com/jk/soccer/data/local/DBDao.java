package com.jk.soccer.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public abstract class DBDao {

    //////// tablePlayer ////////

    //// Read ////

    @Query("SELECT * FROM TablePlayer WHERE ID = :id")
    public abstract List<TablePlayer> findPlayer(Integer id);

    @Query("SELECT * FROM TablePlayer ORDER By Bookmark DESC, Name")
    public abstract List<TablePlayer> findPlayer();

    @Query("SELECT * FROM TablePlayer WHERE ID = :id")
    public abstract LiveData<TablePlayer> findPlayerLiveData(Integer id);

    @Query("SELECT * FROM TablePlayer ORDER BY Bookmark DESC, Name")
    public abstract LiveData<List<TablePlayer>> findPlayerLiveData();

    //// Update ////

    @Query("UPDATE TablePlayer SET " +
            "TeamID = :teamID, " +
            "Position = :position, " +
            "Height = :height, " +
            "Foot = :foot, " +
            "Age = :age, " +
            "Shirt = :shirt " +
            "WHERE ID = :id")
    public abstract void updatePlayerInfoById(Integer teamID,
                              String position,
                              String height,
                              String foot,
                              Integer age,
                              Integer shirt,
                              Integer id);

    @Query("UPDATE TablePlayer SET Bookmark = NOT Bookmark WHERE ID = :id")
    abstract void togglePlayerBookmarkById(Integer id);

    @Transaction
    public void registerPlayerBookmark(Integer id){
        togglePlayerBookmarkById(id);
    }

    @Transaction
    public void unregisterPlayerBookmark(Integer id){
        togglePlayerBookmarkById(id);
        deleteTeamByPlayerId(id);
        deleteMatchByPlayerId(id);
    }

    //////// tableTeam ////////

    //// Create ////

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertTeam(TableTeam team);

    //// Read ////

    @Query("SELECT * FROM TableTeam " +
            "WHERE ID = (SELECT TeamID FROM TablePlayer WHERE ID = :playerId)")
    public abstract LiveData<TableTeam> findTeamByPlayerId(Integer playerId);

    @Query("SELECT * FROM TableTeam WHERE ID = :id")
    public abstract List<TableTeam> findTeam(Integer id);

    @Query("SELECT * FROM TableTeam")
    public abstract List<TableTeam> findTeam();

    @Query("SELECT * FROM TableTeam WHERE ID = :id")
    public abstract LiveData<TableTeam> findTeamLiveData(Integer id);

    @Query("SELECT * FROM TableTeam")
    public abstract LiveData<List<TableTeam>> findTeamLiveData();

    //// Delete ////

    @Query("DELETE FROM TableTeam WHERE " +
            "ID = (SELECT TeamID FROM TablePlayer WHERE ID = :id) " +
            "AND " +
            "ID NOT IN (SELECT TeamID FROM TablePlayer WHERE Bookmark = 1)")
    abstract void deleteTeamByPlayerId(Integer id);

    //////// tableMatch ////////

    //// Create ////

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertMatch(TableMatch match);

    //// Read ////

    @Query("SELECT * FROM TableMatch WHERE ID = :id")
    public abstract List<TableMatch> findMatch(Integer id);

    @Query("SELECT * FROM TableMatch WHERE Cancelled = 0 ORDER By Year, Month, Date, StartTimeStr")
    public abstract List<TableMatch> findMatch();

    @Query("SELECT * FROM TableMatch WHERE ID = :id")
    public abstract LiveData<TableMatch> findMatchLiveData(Integer id);

    @Query("SELECT * FROM TableMatch WHERE Cancelled = 0 ORDER By Year, Month, Date, StartTimeStr")
    public abstract LiveData<List<TableMatch>> findMatchLiveData();

    //// Delete ////

    @Query("WITH Team AS (SELECT TeamID FROM TablePlayer WHERE ID = :id), " +
            "TeamList AS (SELECT ID FROM TableTeam) " +
            "DELETE FROM TableMatch " +
            "WHERE (HomeID = (SELECT * FROM Team) AND HOMEID NOT IN (SELECT ID FROM TableTeam)) " +
            "OR (AwayID = (SELECT * FROM Team) AND AwayID NOT IN (SELECT ID FROM TableTeam))")
    abstract void deleteMatchByPlayerId(Integer id);

}
