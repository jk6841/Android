package com.jk.soccer.ui.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.jk.soccer.data.Repository;
import com.jk.soccer.data.local.Player;
import com.jk.soccer.data.local.Team;

import java.util.ArrayList;
import java.util.List;

public class PlayerListViewModel extends AndroidViewModel {

    private final Application application;
    private Repository repository;
    private PlayerListAdapter adapter;
    private List<Player> baseTable;

    private ArrayList<Integer> colors;

    private MutableLiveData<Integer> index;

    final private LiveData<List<Player>> livedataPlayers;
    final private LiveData<List<Team>> livedataTeams;
    private LiveData<List<Team>> livedataTeam;

    final private ArrayList<LiveData<Integer>> livedataId;
    final private ArrayList<LiveData<String>> livedataName;
    final private ArrayList<LiveData<String>> livedataTeamName;
    final private ArrayList<LiveData<String>> livedataPosition;
    final private ArrayList<LiveData<String>> livedataHeight;
    final private ArrayList<LiveData<String>> livedataFoot;
    final private ArrayList<LiveData<String>> livedataAge;
    final private ArrayList<LiveData<String>> livedataShirt;
    final private ArrayList<LiveData<Boolean>> livedataBookmark;

    public MutableLiveData<Integer> getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index.setValue(index);
    }

    public PlayerListAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(PlayerListAdapter adapter) {
        this.adapter = adapter;
    }

    public LiveData<Integer> getLiveId(int index){ return livedataId.get(index); }

    public LiveData<String> getLiveName(int index) {
        return livedataName.get(index);
    }

    public LiveData<String> getLiveTeamName(int index) {return livedataTeamName.get(index); }

    public LiveData<String> getLivePosition(int index) {
        return livedataPosition.get(index);
    }

    public LiveData<String> getLiveHeight(int index) {
        return livedataHeight.get(index);
    }

    public LiveData<String> getLiveFoot(int index) {
        return livedataFoot.get(index);
    }

    public LiveData<String> getLiveAge(int index) {
        return livedataAge.get(index);
    }

    public LiveData<String> getLiveShirt(int index) {
        return livedataShirt.get(index);
    }

    public LiveData<Boolean> getLiveBookmark(int index) {return livedataBookmark.get(index); }

    public void setBookmark(int index){
        repository.bookmark(
                Repository.Object.Player,
                !livedataPlayers.getValue().get(index).isBookmark(),
                livedataPlayers.getValue().get(index).getId());
        adapter.notifyDataSetChanged();
    }

    public LiveData<List<Player>> getLivePlayers(){
        return livedataPlayers;
    }

    public ArrayList<Integer> getColors(){
        return colors;
    }

    public void setColors(Integer ... colors){
        int length = colors.length;
        for (int i = 0; i < length; i++)
            this.colors.add(application.getResources().getColor(colors[i]));
    }

    public PlayerListViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        repository = Repository.getInstance(application);
        baseTable = repository.getBaseTable();
        livedataPlayers = repository.getPlayer();
        livedataTeams = repository.getTeam();
        index = new MutableLiveData<>();
        colors = new ArrayList<>();
        livedataId = new ArrayList<>();
        livedataName = new ArrayList<>();
        livedataTeamName = new ArrayList<>();
        livedataPosition = new ArrayList<>();
        livedataHeight = new ArrayList<>();
        livedataFoot = new ArrayList<>();
        livedataAge = new ArrayList<>();
        livedataShirt = new ArrayList<>();
        livedataBookmark = new ArrayList<>();

        int len = repository.countPlayer();

        for (int i = 0; i < len; i++){
            int k = i;
            livedataId.add(Transformations.map(
                    livedataPlayers, input -> input.get(k).getId()));

            livedataName.add(Transformations.map(
                    livedataPlayers, input -> input.get(k).printName()));

            livedataTeamName.add(Transformations.map(
                    repository.getTeamByPlayerIndex(k),
                    input -> (input != null)? input.getName() : null));

            livedataPosition.add(Transformations.map(
                    livedataPlayers, input -> input.get(k).printPosition()));

            livedataHeight.add(Transformations.map(
                    livedataPlayers, input -> input.get(k).printHeight()));

            livedataFoot.add(Transformations.map(
                    livedataPlayers, input -> input.get(k).printFoot()));

            livedataAge.add(Transformations.map(
                    livedataPlayers, input -> input.get(k).printAge()));

            livedataShirt.add(Transformations.map(
                    livedataPlayers, input -> input.get(k).printShirt()));

            livedataBookmark.add(Transformations.map(
                    livedataPlayers, input -> input.get(k).isBookmark()));

        }
    }

}