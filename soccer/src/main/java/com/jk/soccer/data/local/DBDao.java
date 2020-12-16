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

    // For Initialization //
    @Query("SELECT * FROM tablePlayer ORDER By Bookmark DESC, Name")
    public abstract List<Player> playerInit();

    @Query("SELECT * FROM tableMatch WHERE Bookmark = 1 ORDER By Year, Month, Date, StartTimeStr")
    public abstract List<Match> matchInit();

    ////////             ////////
    @Transaction
    public void updateBookmark(Boolean bookmark, Integer playerId){
        updatePlayerBookmarkById(bookmark, playerId);
        updateTeamBookmark();
        updateMatchBookmark();
    }

    //////// tablePlayer ////////

    //// Create ////

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertPlayer(Player player);

    //// Read ////

    @Query("SELECT * FROM tablePlayer WHERE ID = :id")
    public abstract LiveData<Player> findPlayerById(Integer id);

    @Query("SELECT * FROM tablePlayer ORDER BY Bookmark DESC, Name")
    public abstract LiveData<List<Player>> findPlayerAll();

    //// Update ////

    @Query("UPDATE tablePlayer SET " +
            "TeamID = :teamID, " +
            "TeamName = :teamName, " +
            "Position = :position, " +
            "Height = :height, " +
            "Foot = :foot, " +
            "Age = :age, " +
            "Shirt = :shirt " +
            "WHERE ID = :id")
    public abstract void updatePlayerInfoById(Integer teamID,
                              String teamName,
                              String position,
                              String height,
                              String foot,
                              Integer age,
                              Integer shirt,
                              Integer id);

    @Query("UPDATE tablePlayer SET Bookmark = :bookmark WHERE ID = :id")
    public abstract void updatePlayerBookmarkById(boolean bookmark, Integer id);

    @Query("UPDATE tablePlayer SET Bookmark = :bookmark")
    public abstract void updatePlayerBookmarkAll(boolean bookmark);

    //// Delete ////

    @Query("DELETE FROM tablePlayer WHERE ID = :id")
    public abstract void deletePlayerById(Integer id);

    @Query("DELETE FROM tablePlayer")
    public abstract void deletePlayerAll();

    //////// tableTeam ////////

    //// Create ////

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertTeam(Team team);

    //// Read ////

    @Query("SELECT tableTeam.* FROM tableTeam, tablePlayer " +
            "WHERE tablePlayer.TeamID = tableTeam.ID AND tablePlayer.Id = :playerId")
    public abstract LiveData<Team> findTeamByPlayerId(Integer playerId);

    @Query("SELECT * FROM tableTeam WHERE ID = :id")
    public abstract LiveData<Team> findTeamById(Integer id);

    @Query("SELECT * FROM tableTeam")
    public abstract LiveData<List<Team>> findTeamAll();


    //// Update ////

    @Query("UPDATE tableTeam SET Bookmark = CASE " +
            "WHEN ID IN (SELECT TeamID FROM tablePlayer WHERE Bookmark = 1) THEN 1 " +
            "ELSE 0 END ")
    abstract void updateTeamBookmark();

    //// Delete ////

    @Query("DELETE FROM tableTeam WHERE ID = :id")
    public abstract void deleteTeamById(Integer id);

    @Query("DELETE FROM tableTeam")
    public abstract void deleteTeamAll();

    //////// tableMatch ////////

    //// Create ////

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertMatch(Match match);

    @Transaction
    public void insertMatchWrapper(Match match){
        insertMatch(match);
        updateMatchBookmark();
    }

    //// Read ////

    @Query("SELECT * FROM tableMatch WHERE ID = :id")
    public abstract LiveData<Match> findMatchById(Integer id);

    @Query("SELECT * FROM tableMatch WHERE Bookmark = 1 ORDER By Year, Month, Date, StartTimeStr")
    public abstract LiveData<List<Match>> findMatchAll();

    //// Update ////

    @Query("UPDATE tableMatch SET Bookmark = CASE " +
            "WHEN HomeID IN (SELECT tableTeam.ID FROM tableTeam WHERE Bookmark = 1) THEN 1 " +
            "WHEN AwayID IN (SELECT tableTeam.ID FROM tableTeam WHERE Bookmark = 1) THEN 1 " +
            "ELSE 0 END")
    abstract void updateMatchBookmark();

    //// Delete ////

    @Query("DELETE FROM tableMatch WHERE ID = :id")
    public abstract void deleteMatchById(Integer id);

    @Query("DELETE FROM tableMatch")
    public abstract void deleteMatchAll();

}
