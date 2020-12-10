package com.jk.soccer.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;

import com.jk.soccer.R;
import com.jk.soccer.data.Repository;
import com.jk.soccer.data.local.Player;
import com.jk.soccer.data.local.Team;
import com.jk.soccer.ui.home.HomeAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends AndroidViewModel {

    private final Application application;
    private Repository repository;
    private HomeAdapter adapter;

    private MutableLiveData<Integer> index;

    final private LiveData<List<Player>> livedataPlayers;
    private LiveData<List<Team>> livedataTeam;

    final private ArrayList<LiveData<String>> livedataPlayerName;
    final private ArrayList<LiveData<Boolean>> livedataBookmark;
    //final private LiveData<String> livedataTeamName;
    final private ArrayList<LiveData<String>> livedataPosition;
    final private ArrayList<LiveData<String>> livedataHeight;
    final private ArrayList<LiveData<String>> livedataFoot;
    final private ArrayList<LiveData<String>> livedataAge;
    final private ArrayList<LiveData<String>> livedataShirt;
    final private ArrayList<LiveData<String>> livedataPlayerUrl;

    public MutableLiveData<Integer> getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index.setValue(index);
    }

    public HomeAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(HomeAdapter adapter) {
        this.adapter = adapter;
    }

    public LiveData<String> getLivePlayerName(int index) {
        return livedataPlayerName.get(index);
    }

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

    public LiveData<String> getLivePlayerUrl(int index) {
        return livedataPlayerUrl.get(index);
    }

    public LiveData<List<Player>> getLivePlayers(){
        return livedataPlayers;
    }

    public HomeViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        repository = Repository.getInstance(application);
        livedataPlayers = repository.getPlayer();
        index = new MutableLiveData<>();
        livedataPlayerName = new ArrayList<>();
        livedataPosition = new ArrayList<>();
        livedataHeight = new ArrayList<>();
        livedataFoot = new ArrayList<>();
        livedataAge = new ArrayList<>();
        livedataShirt = new ArrayList<>();
        livedataBookmark = new ArrayList<>();
        livedataPlayerUrl = new ArrayList<>();

        int len = repository.countPlayer();

        for (int i = 0; i < len; i++){
            int k = i;
            livedataPlayerName.add(Transformations.map(
                    livedataPlayers, input ->  input.get(k).printName() ));

            livedataPosition.add(Transformations.map(
                    livedataPlayers, input -> (input != null) ? input.get(k).printPosition() : null));

            livedataHeight.add(Transformations.map(
                    livedataPlayers, input -> (input != null) ? input.get(k).printHeight() : null));

            livedataFoot.add(Transformations.map(
                    livedataPlayers, input -> (input != null) ? input.get(k).printFoot() : null));

            livedataAge.add(Transformations.map(
                    livedataPlayers, input -> (input != null) ? input.get(k).printAge() : null));

            livedataShirt.add(Transformations.map(
                    livedataPlayers, input -> (input != null) ? input.get(k).printShirt() : null));

            livedataBookmark.add(Transformations.map(
                    livedataPlayers, input -> (input != null) ? input.get(k).isBookmark() : null));

            livedataPlayerUrl.add(Transformations.map(
                    livedataPlayers, input -> (input != null) ? application.getString(R.string.baseUrl2)
                            + input.get(k).getId() + application.getString(R.string.png) : null));
        }
    }

}