package com.jk.soccer.model.local;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.jk.soccer.etc.MyCallback;
import com.jk.soccer.etc.enumeration.Type;

import java.util.List;

public class MyLocal {

    public static MyLocal getInstance(Application application){
        if (myLocal != null)
            return myLocal;
        myLocal = new MyLocal(application);
        return myLocal;
    }

    public void close(){
        database.close();
    }

    public void getLeagueList(MyCallback<List<TableSearch>> callback){
        getList(Type.LEAGUE, 0, callback);
    }

    public void getTeamList(Integer leagueID, MyCallback<List<TableSearch>> callback){
        getList(Type.TEAM, leagueID, callback);
    }

    public void getPlayerList(Integer teamID, MyCallback<List<TableSearch>> callback){
        getList(Type.PERSON, teamID, callback);
    }

    private void getList(Type type, Integer parentID, MyCallback<List<TableSearch>> callback){
        new Thread(()->callback.onComplete(dao.getList(type, parentID))).start();
    }

    public void insertSearch(List<TableSearch> entries){
        Thread t = new Thread(() -> dao.insertSearch(entries));
        t.start();
        try {
            t.join();
        } catch (Exception e){
            Log.e("Error: ", e.getMessage());
        }
    }

    public LiveData<List<TableSearch>> getSearch(String searchWord, Type type){
        return dao.getSearch(searchWord, type);
    }

    public void clearSearch(){
        Thread t = new Thread(dao::clearSearch);
        t.start();
        try {
            t.join();
        } catch (Exception e){
            Log.e("Error: ", e.getMessage());
        }
    }

    private static MyLocal myLocal;
    final private Database database;
    final private DBDao dao;

    private MyLocal(Application application){
        database = Database.getInstance(application);
        dao = database.dbDao();
    }

}
