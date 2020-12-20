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

    @Query("SELECT * FROM tablePlayer WHERE ID = :id")
    public abstract List<Player> findPlayer(Integer id);

    @Query("SELECT * FROM tablePlayer ORDER By Bookmark DESC, Name")
    public abstract List<Player> findPlayer();

    @Query("SELECT * FROM tablePlayer WHERE ID = :id")
    public abstract LiveData<Player> findPlayerLiveData(Integer id);

    @Query("SELECT * FROM tablePlayer ORDER BY Bookmark DESC, Name")
    public abstract LiveData<List<Player>> findPlayerLiveData();

    //// Update ////

    @Query("UPDATE tablePlayer SET " +
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

    @Query("UPDATE tablePlayer SET Bookmark = NOT Bookmark WHERE ID = :id")
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
    public abstract void insertTeam(Team team);

    //// Read ////

    @Query("SELECT * FROM tableTeam " +
            "WHERE ID = (SELECT TeamID FROM tablePlayer WHERE ID = :playerId)")
    public abstract LiveData<Team> findTeamByPlayerId(Integer playerId);

    @Query("SELECT * FROM tableTeam WHERE ID = :id")
    public abstract List<Team> findTeam(Integer id);

    @Query("SELECT * FROM tableTeam")
    public abstract List<Team> findTeam();

    @Query("SELECT * FROM tableTeam WHERE ID = :id")
    public abstract LiveData<Team> findTeamLiveData(Integer id);

    @Query("SELECT * FROM tableTeam")
    public abstract LiveData<List<Team>> findTeamLiveData();

    //// Delete ////

    @Query("DELETE FROM tableTeam WHERE " +
            "ID = (SELECT TeamID FROM tablePlayer WHERE ID = :id) " +
            "AND " +
            "ID NOT IN (SELECT TeamID FROM tablePlayer WHERE Bookmark = 1)")
    abstract void deleteTeamByPlayerId(Integer id);

    //////// tableMatch ////////

    //// Create ////

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertMatch(Match match);

    //// Read ////

    @Query("SELECT * FROM tableMatch WHERE ID = :id")
    public abstract List<Match> findMatch(Integer id);

    @Query("SELECT * FROM tableMatch ORDER By Year, Month, Date, StartTimeStr")
    public abstract List<Match> findMatch();

    @Query("SELECT * FROM tableMatch WHERE ID = :id")
    public abstract LiveData<Match> findMatchLiveData(Integer id);

    @Query("SELECT * FROM tableMatch ORDER By Year, Month, Date, StartTimeStr")
    public abstract LiveData<List<Match>> findMatchLiveData();

    //// Delete ////

    @Query("WITH Team AS (SELECT TeamID FROM tablePlayer WHERE ID = :id), " +
            "TeamList AS (SELECT ID FROM tableTeam) " +
            "DELETE FROM tableMatch " +
            "WHERE (HomeID = (SELECT * FROM Team) AND HOMEID NOT IN (SELECT ID FROM tableTeam)) " +
            "OR (AwayID = (SELECT * FROM Team) AND AwayID NOT IN (SELECT ID FROM tableTeam))")
    abstract void deleteMatchByPlayerId(Integer id);

}
