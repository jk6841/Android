package com.jk.soccer.model.local;

import android.app.Application;
import android.os.AsyncTask;

import com.jk.soccer.etc.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MyLocal {

    public static MyLocal getInstance(Application application){
        if (myLocal != null)
            return myLocal;
        myLocal = new MyLocal(application);
        return myLocal;
    }

    public MyLocal(Application application){
        Database database = Database.getInstance(application);
        dao = database.dbDao();
    }

    public List<Pair> getLeagueList(){
        return getChildren(0);
    }

    public List<Pair> getTeamList(Integer leagueID){
        return getChildren(leagueID);
    }

    public List<Pair> getPlayerList(Integer teamID){
        return getChildren(teamID);
    }

    public void insertList(Table[] list){
        LocalTask localTask = new LocalTask(dao, LocalTask.Query.CREATE);
        localTask.execute(list);
    }

    private static MyLocal myLocal = null;
    final private DBDao dao;

    private List<Pair> getChildren(Integer parentID){
        LocalTask localTask = new LocalTask(dao, LocalTask.Query.READ);
        Table input= new Table(0, parentID, "");
        try{
            return localTask.execute(input).get().get(0);
        } catch (ExecutionException | InterruptedException e){
            e.printStackTrace();
        }
        return null;
    }

    private static class LocalTask extends AsyncTask<Table, Void, List<List<Pair>>> {

        public enum Query{
            CREATE, READ, UPDATE, DELETE
        }

        final private DBDao dao;
        final private Query query;

        public LocalTask(DBDao dao, Query query) {
            this.dao = dao;
            this.query = query;
        }

        @Override
        protected List<List<Pair>> doInBackground(Table... tables) {
            List<List<Pair>> result = new ArrayList<>();
            for (Table table : tables) {
                result.add(accessLocal(table));
            }
            return result;
        }

        private List<Pair> accessLocal(Table table){
            switch (query){
                case CREATE:
                    dao.insert(table);
                    return null;
                case READ:
                    return dao.getChildren(table.getParentID());
                case DELETE:
                    dao.delete(table.getID());
                    return null;
                default:
                    return null;
            }
        }
    }

}
