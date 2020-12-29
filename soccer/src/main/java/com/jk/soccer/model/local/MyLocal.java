package com.jk.soccer.model.local;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.jk.soccer.etc.Type;

import java.util.List;

public class MyLocal {

    public static MyLocal getInstance(Application application){
        if (myLocal != null)
            return myLocal;
        myLocal = new MyLocal(application);
        return myLocal;
    }

    public MyLocal(Application application){
        database = Database.getInstance(application);
        dao = database.dbDao();
    }

    public void close(){
        database.close();
    }

    //// Main thread cannot call this.
    public List<TableSearch> getLeagueList(){
        return dao.getList(Type.LEAGUE);
    }

    //// Main thread cannot call this.
    public void insertSearch(TableSearch entry){
        dao.insertSearch(entry);
    }

    public LiveData<List<TableSearch>> getSearch(String searchWord){
        return dao.getSearch(searchWord);
    }

    private static MyLocal myLocal;
    final private Database database;
    final private DBDao dao;

}
