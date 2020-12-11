package com.jk.soccer.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DBDao {

    // For Initialization //
    @Query("SELECT * FROM tablePlayer ORDER By Bookmark DESC, Name")
    List<Player> base();


    //////// tablePlayer ////////

    //// Create ////

    @Insert
    void insertPlayer(Player player);

    //// Read ////

    @Query("SELECT * FROM tablePlayer WHERE ID = :id")
    LiveData<Player> findPlayerById(Integer id);

    @Query("SELECT * FROM tablePlayer WHERE Name = :name")
    LiveData<List<Player>> findPlayerByName(String name);

    @Query("SELECT * FROM tablePlayer ORDER BY Bookmark DESC, Name")
    LiveData<List<Player>> findPlayerAll();

    @Query("SELECT COUNT(*) FROM tablePlayer")
    Integer countPlayer();

    //// Update ////

    @Query("UPDATE tablePlayer SET " +
            "TeamID = :teamID, " +
            "Position = :position, " +
            "Height = :height, " +
            "Foot = :foot, " +
            "Age = :age, " +
            "Shirt = :shirt " +
            "WHERE ID = :id")
    void updatePlayerInfoById(Integer teamID,
                              String position,
                              String height,
                              String foot,
                              Integer age,
                              Integer shirt,
                              Integer id);

    @Query("UPDATE tablePlayer SET Bookmark = :bookmark WHERE ID = :id")
    void updatePlayerBookmarkById(boolean bookmark, Integer id);

    @Query("UPDATE tablePlayer SET Bookmark = :bookmark")
    void updatePlayerBookmarkAll(boolean bookmark);

    //// Delete ////

    @Query("DELETE FROM tablePlayer WHERE ID = :id")
    void deletePlayerById(Integer id);

    @Query("DELETE FROM tablePlayer")
    void deletePlayerAll();

    //////// tableTeam ////////

    //// Create ////

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertTeam(Team team);

    //// Read ////

    @Query("SELECT tableTeam.* FROM tableTeam, tablePlayer WHERE tablePlayer.TeamID = tableTeam.ID AND tablePlayer.Id = :playerId")
    LiveData<Team> findTeamByPlayerId(Integer playerId);

    @Query("SELECT * FROM tableTeam WHERE ID = :id")
    LiveData<Team> findTeamById(Integer id);

    @Query("SELECT * FROM tableTeam WHERE Name = :name")
    LiveData<List<Team>> findTeamByName(String name);

    @Query("SELECT * FROM tableTeam")
    LiveData<List<Team>> findTeamAll();


    //// Update ////

    //// Delete ////

    @Query("DELETE FROM tableTeam WHERE ID = :id")
    void deleteTeamById(Integer id);

    @Query("DELETE FROM tableTeam")
    void deleteTeamAll();

    //////// tableMatch ////////

    //// Create ////

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMatch(Match match);

    //// Read ////

    @Query("SELECT * FROM tableMatch WHERE ID = :id")
    LiveData<Match> findMatchById(Integer id);

//    @Query("SELECT * FROM tableMatch WHERE Date = :date")
//    LiveData<List<Match>> findMatchByDate(String date);
//
//    @Query("SELECT * FROM tableMatch WHERE Date >= :startDate and Date <= :endDate")
//    LiveData<List<Match>> findMatchByDate(String startDate, String endDate);

    @Query("SELECT * FROM tableMatch")
    LiveData<List<Match>> findMatchAll();

    //// Update ////

//    @Query("UPDATE tableMatch SET Score = :score WHERE ID = :id")
//    void updateMatchScoreById(String score, Integer id);

    //// Delete ////

    @Query("DELETE FROM tableMatch WHERE ID = :id")
    void deleteMatchById(Integer id);

//    @Query("DELETE FROM tableMatch WHERE Date = :date")
//    void deleteMatchByDate(String date);
//
//    @Query("DELETE FROM tableMatch WHERE Date >= :startDate and Date <= :endDate")
//    void deleteMatchByDate(String startDate, String endDate);

    @Query("DELETE FROM tableMatch")
    void deleteMatchAll();

}
