package com.jk.soccer.model.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.jk.soccer.etc.enumeration.Type;

import java.util.List;

@Dao
public interface DBDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSearch(List<TableSearch> entries);

    @Query("SELECT * FROM tableSearch WHERE Name LIKE :name AND Type = :type")
    LiveData<List<TableSearch>> getSearch(String name, Type type);

    @Query("SELECT * FROM tableSearch WHERE Type = :type AND ParentID = :parentID")
    List<TableSearch> getList(Type type, Integer parentID);

    @Query("DELETE FROM tableSearch WHERE ParentID != 0")
    void clearSearch();

}
