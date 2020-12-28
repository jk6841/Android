package com.jk.soccer.model.local;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.jk.soccer.etc.Pair;
import com.jk.soccer.etc.Player;
import com.jk.soccer.etc.Type;

import java.util.List;

@Dao
public interface DBDao {

    //////// tablePlayer ////////

    //// Read ////

//    @Query("SELECT * FROM TablePlayer WHERE ID = :id")
//    public abstract List<TablePlayer> findPlayer(Integer id);
//
//    @Query("SELECT * FROM tablePlayer ORDER By Bookmark DESC, Name")
//    public abstract List<TablePlayer> findPlayer();
//
//    @Query("SELECT tablePlayer.ID AS id," +
//            "tablePlayer.Name AS name," +
//            "tablePlayer.Position AS position," +
//            "tablePlayer.Height AS height," +
//            "tablePlayer.Foot AS foot," +
//            "tablePlayer.Birth AS birth," +
//            "tablePlayer.Shirt AS shirt," +
//            "tableTeam.Name AS team," +
//            "tablePlayer.Bookmark AS bookmark " +
//            "FROM tablePlayer, tableTeam " +
//            "WHERE tablePlayer.ID = :id " +
//            "AND " +
//            "tablePlayer.teamID = tableTeam.ID")
//    public abstract LiveData<Player> findPlayerLiveData(Integer id);
//
//    @Query("SELECT tablePlayer.ID AS id," +
//            "tablePlayer.Name AS name," +
//            "tablePlayer.Position AS position," +
//            "tablePlayer.Height AS height," +
//            "tablePlayer.Foot AS foot," +
//            "tablePlayer.Birth AS birth," +
//            "tablePlayer.Shirt AS shirt," +
//            "tableTeam.Name AS team," +
//            "tablePlayer.Bookmark AS bookmark " +
//            "FROM tablePlayer, tableTeam " +
//            "WHERE tablePlayer.teamID = tableTeam.ID " +
//            "ORDER BY Bookmark DESC, Name")
//    public abstract LiveData<List<Player>> findPlayerLiveData();

    //// Update ////

//    @Query("UPDATE TablePlayer SET Bookmark = NOT Bookmark WHERE ID = :id")
//    abstract void togglePlayerBookmarkByID(Integer id);

//    @Transaction
//    public void registerPlayerBookmark(Integer id){
//        togglePlayerBookmarkByID(id);
//        increaseTeamBookmarkByPlayerID(id);
//    }
//
//    @Transaction
//    public void unregisterPlayerBookmark(Integer id){
//        togglePlayerBookmarkByID(id);
//        decreaseTeamBookmarkByPlayerID(id);
//        deleteMatchByPlayerID(id);
//    }

//    //////// tableTeam ////////
//
//    //// Create ////
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    public abstract void insertTeam(TableTeam team);
//
//    //// Read ////
//
//    @Query("SELECT * FROM TableTeam " +
//            "WHERE ID = (SELECT TeamID FROM TablePlayer WHERE ID = :playerID)")
//    public abstract LiveData<TableTeam> findTeamByPlayerID(Integer playerID);
//
//    @Query("SELECT * FROM TableTeam WHERE ID = :id")
//    public abstract List<TableTeam> findTeam(Integer id);
//
//    @Query("SELECT * FROM TableTeam")
//    public abstract List<TableTeam> findTeam();
//
//    @Query("SELECT * FROM TableTeam WHERE ID = :id")
//    public abstract LiveData<TableTeam> findTeamLiveData(Integer id);
//
//    @Query("SELECT * FROM TableTeam")
//    public abstract LiveData<List<TableTeam>> findTeamLiveData();

//    //// Update ////
//
//    @Query("UPDATE tableTeam " +
//            "SET Rank = :rank, " +
//            "TopRating = :topRating, " +
//            "TopGoal = :topGoal, " +
//            "TopAssist = :topAssist, " +
//            "Fixture = :fixture " +
//            "WHERE ID = :id")
//    public abstract void updateTeamByID(Integer rank,
//                                        String topRating,
//                                        String topGoal,
//                                        String topAssist,
//                                        String fixture, Integer id);
//
//    @Query("UPDATE tableTeam SET Bookmark = Bookmark + 1 " +
//            "WHERE ID = (SELECT TeamID From tablePlayer WHERE ID = :id)")
//    public abstract void increaseTeamBookmarkByPlayerID(Integer id);
//
//    @Query("UPDATE tableTeam SET Bookmark = Bookmark - 1 " +
//            "WHERE ID = (SELECT TeamID From tablePlayer WHERE ID = :id)")
//    public abstract void decreaseTeamBookmarkByPlayerID(Integer id);


//    //// Delete ////
//
//    @Query("DELETE FROM TableTeam WHERE " +
//            "ID = (SELECT TeamID FROM TablePlayer WHERE ID = :id) " +
//            "AND " +
//            "ID NOT IN (SELECT TeamID FROM TablePlayer WHERE Bookmark = 1)")
//    abstract void deleteTeamByPlayerID(Integer id);
//
//    //////// tableMatch ////////
//
//    //// Create ////
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    public abstract void insertMatch(TableMatch match);
//
//    //// Read ////
//
//    @Query("SELECT * FROM TableMatch WHERE ID = :id")
//    public abstract List<TableMatch> findMatch(Integer id);
//
//    @Query("SELECT * FROM TableMatch WHERE Cancelled = 0 ORDER By Year, Month, Date, Time")
//    public abstract List<TableMatch> findMatch();
//
//    @Query("SELECT * FROM TableMatch WHERE ID = :id")
//    public abstract LiveData<TableMatch> findMatchLiveData(Integer id);
//
//    @Query("SELECT * FROM TableMatch WHERE Cancelled = 0 ORDER By Year, Month, Date, Time")
//    public abstract LiveData<List<TableMatch>> findMatchLiveData();
//
//    //// Delete ////
//
//    @Query("WITH Team AS (SELECT TeamID FROM TablePlayer WHERE ID = :id AND Bookmark = 0)" +
//            "DELETE FROM TableMatch " +
//            "WHERE (HomeID = (SELECT * FROM Team)) " +
//            "OR (AwayID = (SELECT * FROM Team))")
//    abstract void deleteMatchByPlayerID(Integer id);
//
//    //////// Counting Queries ////////
//
//    @Query("SELECT Count(*) From tablePlayer")
//    public abstract List<Integer> countPlayer();
//
//    @Query("SELECT Count(*) From tableMatch")
//    public abstract List<Integer> countMatch();
//
//    @Query("SELECT EventCount FROM tableMatch WHERE Cancelled = 0 ORDER By Year, Month, Date, Time")
//    public abstract List<Integer> countEvent();
//
//    @Query("SELECT HomeLineupCount FROM tableMatch WHERE Cancelled = 0 ORDER By Year, Month, Date, Time")
//    public abstract List<Integer> countHomeLineup();
//
//    @Query("SELECT AwayLineupCount FROM tableMatch WHERE Cancelled = 0 ORDER By Year, Month, Date, Time")
//    public abstract List<Integer> countAwayLineup();

    /////////////////////////////////  NEW ////////////////////////////////////////////

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTeam(TableTeam entry);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPlayer(TablePlayer entry);

    @Query("SELECT ID FROM tableLeague LIMIT 1 OFFSET :leagueIndex")
    Integer getLeagueID(Integer leagueIndex);

    @Query("SELECT ID FROM tableTeam " +
            "WHERE LeagueID IN tempLeague ORDER BY Rank LIMIT 1 OFFSET :teamIndex")
    Integer getTeamID(Integer teamIndex);

    @Query("SELECT ID FROM tablePlayer " +
            "WHERE TeamID IN tempTeam " +
            "ORDER BY Role DESC " +
            "LIMIT 1 " +
            "OFFSET :playerIndex")
    Integer getPlayerID(Integer playerIndex);

    @Query("SELECT ID AS ID, Name AS name FROM tableLeague")
    LiveData<List<Pair>> getLeagueList();

    @Query("SELECT ID AS ID, Name AS name FROM tableTeam " +
            "WHERE LeagueID IN tempLeague ORDER BY Rank")
    LiveData<List<Pair>> getTeamList();

    @Query("SELECT ID AS ID, Name AS name FROM tablePlayer " +
            "WHERE teamID IN tempTeam " +
            "ORDER BY Role DESC")
    LiveData<List<Pair>> getPlayerList();

    @Query("INSERT INTO tempLeague SELECT ID FROM tableLeague LIMIT 1 OFFSET :leagueIndex")
    void selectLeague(Integer leagueIndex);

    @Query("DELETE FROM tempLeague")
    void clearLeague();

    @Query("INSERT INTO tempTeam SELECT ID FROM tableTeam " +
            "WHERE LeagueID IN tempLeague " +
            "ORDER BY Rank " +
            "LIMIT 1 " +
            "OFFSET :teamIndex")
    void selectTeam(Integer teamIndex);

    @Query("DELETE FROM tempTeam")
    void clearTeam();

    @Query("INSERT INTO tempPlayer SELECT ID FROM tablePlayer " +
            "WHERE teamID IN tempTeam " +
            "ORDER BY Role DESC " +
            "LIMIT 1 " +
            "OFFSET :playerIndex")
    void selectPlayer(Integer playerIndex);

    @Query("DELETE FROM tempPlayer")
    void clearPlayer();


}
