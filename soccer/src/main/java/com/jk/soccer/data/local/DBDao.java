package com.jk.soccer.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.jk.soccer.data.response.Player;

import java.util.List;

@Dao
public abstract class DBDao {

    //////// tablePlayer ////////

    //// Read ////

    @Query("SELECT * FROM TablePlayer WHERE ID = :id")
    public abstract List<TablePlayer> findPlayer(Integer id);

    @Query("SELECT * FROM tablePlayer ORDER By Bookmark DESC, Name")
    public abstract List<TablePlayer> findPlayer();

    @Query("SELECT tablePlayer.ID AS id," +
            "tablePlayer.Name AS name," +
            "tablePlayer.Position AS position," +
            "tablePlayer.Height AS height," +
            "tablePlayer.Foot AS foot," +
            "tablePlayer.Birth AS birth," +
            "tablePlayer.Shirt AS shirt," +
            "tableTeam.Name AS team," +
            "tablePlayer.Bookmark AS bookmark " +
            "FROM tablePlayer, tableTeam " +
            "WHERE tablePlayer.ID = :id " +
            "AND " +
            "tablePlayer.teamID = tableTeam.ID")
    public abstract LiveData<Player> findPlayerLiveData(Integer id);

    @Query("SELECT * FROM TablePlayer ORDER BY Bookmark DESC, Name")
    public abstract LiveData<List<TablePlayer>> findPlayerLiveData();

    //// Update ////

    @Query("UPDATE TablePlayer SET Bookmark = NOT Bookmark WHERE ID = :id")
    abstract void togglePlayerBookmarkById(Integer id);

    @Transaction
    public void registerPlayerBookmark(Integer id){
        togglePlayerBookmarkById(id);
    }

    @Transaction
    public void unregisterPlayerBookmark(Integer id){
        togglePlayerBookmarkById(id);
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

    @Query("WITH Team AS (SELECT TeamID FROM TablePlayer WHERE ID = :id AND Bookmark = 0)" +
            "DELETE FROM TableMatch " +
            "WHERE (HomeID = (SELECT * FROM Team)) " +
            "OR (AwayID = (SELECT * FROM Team))")
    abstract void deleteMatchByPlayerId(Integer id);

}
