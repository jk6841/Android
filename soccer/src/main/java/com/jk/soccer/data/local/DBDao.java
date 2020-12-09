package com.jk.soccer.data.local;

import android.graphics.Bitmap;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DBDao {

    //// tablePlayer ////

    /* C */

    @Insert
    void insertPlayer(Player player);

    /* R */

    @Query("SELECT * FROM tablePlayer WHERE ID = :id")
    List<Player> findPlayerById(Integer id);

    @Query("SELECT * FROM tablePlayer WHERE Name = :name")
    List<Player> findPlayerByName(String name);

    @Query("SELECT * FROM tablePlayer")
    List<Player> findPlayerAll();

    @Query("SELECT COUNT(*) FROM tablePlayer")
    Integer countPlayer();

    /* U */

    @Query("UPDATE tablePlayer SET Bookmark = :bookmark WHERE ID = :id")
    void updatePlayerBookmarkById(boolean bookmark, Integer id);

    @Query("UPDATE tablePlayer SET Bookmark = :bookmark")
    void updatePlayerBookmarkAll(boolean bookmark);

    @Query("UPDATE tablePlayer SET Image = :image WHERE ID = :id")
    void updatePlayerImageById(Bitmap image, Integer id);

    @Query("UPDATE tablePlayer SET TeamID = :teamID WHERE ID = :id")
    void updatePlayerTeamIDById(Integer teamID, Integer id);

    @Query("UPDATE tablePlayer SET Position = :position WHERE ID = :id")
    void updatePlayerPositionById(String position, Integer id);

    @Query("UPDATE tablePlayer SET Height = :height WHERE ID = :id")
    void updatePlayerHeightById(String height, Integer id);

    @Query("UPDATE tableplayer SET Foot = :foot WHERE ID = :id")
    void updatePlayerFootById(String foot, Integer id);

    @Query("UPDATE tableplayer SET Age = :age WHERE ID = :id")
    void updatePlayerAgeById(Integer age, Integer id);

    @Query("UPDATE tableplayer SET Shirt = :shirt WHERE ID = :id")
    void updatePlayerShirtById(Integer shirt, Integer id);

    /* D */

    @Query("DELETE FROM tablePlayer WHERE ID = :id")
    void deletePlayerById(Integer id);

    @Query("DELETE FROM tablePlayer")
    void deletePlayerAll();

    //// tableTeam ////

    /* C */

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertTeam(Team team);

    /* R */

    @Query("SELECT * FROM tableTeam WHERE ID = :id")
    List<Team> findTeamById(Integer id);

    @Query("SELECT * FROM tableTeam WHERE Name = :name")
    List<Team> findTeamByName(String name);

    @Query("SELECT * FROM tableTeam")
    List<Team> findTeamAll();


    /* U */
    @Query("UPDATE tableTeam SET Image = :image WHERE ID = :id")
    void updateTeamImageById(Bitmap image, Integer id);

    /* D */
    @Query("DELETE FROM tableTeam WHERE ID = :id")
    void deleteTeamById(Integer id);

    @Query("DELETE FROM tableTeam")
    void deleteTeamAll();

    //// tableMatch ////

    /* C */

    @Insert
    void insertMatch(Match match);

    /* R */
    @Query("SELECT * FROM tableMatch WHERE ID = :id")
    Match findMatchById(Integer id);

    @Query("SELECT * FROM tableMatch WHERE Date = :date")
    Match findMatchByDate(String date);

    @Query("SELECT * FROM tableMatch WHERE Date >= :startDate and Date <= :endDate")
    Match findMatchByDate(String startDate, String endDate);

    /* U */

    @Query("UPDATE tableMatch SET Score = :score WHERE ID = :id")
    void updateMatchScoreById(String score, Integer id);

    /* D */

    @Query("DELETE FROM tableMatch WHERE ID = :id")
    void deleteMatchById(Integer id);

    @Query("DELETE FROM tableMatch WHERE Date = :date")
    void deleteMatchByDate(String date);

    @Query("DELETE FROM tableMatch WHERE Date >= :startDate and Date <= :endDate")
    void deleteMatchByDate(String startDate, String endDate);

    @Query("DELETE FROM tableMatch")
    void deleteMatchAll();

}
