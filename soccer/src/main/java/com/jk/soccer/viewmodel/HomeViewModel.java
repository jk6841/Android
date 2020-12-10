package com.jk.soccer.viewmodel;

import android.app.Application;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.jk.soccer.data.Repository;
import com.jk.soccer.data.local.Player;
import com.jk.soccer.data.local.Team;

import java.util.List;

public class HomeViewModel extends AndroidViewModel {

    private Repository repository;

    private LiveData<List<Player>> mlPlayer;
    private LiveData<List<Team>> mlTeam;


    private LiveData<String> mlPlayerName;
    private LiveData<Boolean> mlBookmark;
    private LiveData<Bitmap> mlPlayerImage;
    private LiveData<String> mlTeamName;

    public LiveData<String> getMlPlayerName() {
        return mlPlayerName;
    }

    public LiveData<Bitmap> getMlPlayerImage() {
        return mlPlayerImage;
    }

    public LiveData<String> getMlPosition() {
        return mlPosition;
    }

    public LiveData<String> getMlHeight() {
        return mlHeight;
    }

    public LiveData<String> getMlFoot() {
        return mlFoot;
    }

    public LiveData<String> getMlAge() {
        return mlAge;
    }

    public LiveData<String> getMlShirt() {
        return mlShirt;
    }

    private LiveData<String> mlPosition;
    private LiveData<String> mlHeight;
    private LiveData<String> mlFoot;
    private LiveData<String> mlAge;
    private LiveData<String> mlShirt;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        repository = Repository.getInstance(application);
        mlPlayer = repository.getPlayer();
        mlTeam = repository.getTeam();
        mlPlayerName = Transformations.map(mlPlayer, new Function<List<Player>, String>() {
            @Override
            public String apply(List<Player> input) {
                return "이름: " + input.get(0).getName();
            }
        });
        mlBookmark = Transformations.map(mlPlayer, new Function<List<Player>, Boolean>() {
            @Override
            public Boolean apply(List<Player> input) {
                return input.get(0).isBookmark();
            }
        });
        mlPlayerImage = Transformations.map(mlPlayer, new Function<List<Player>, Bitmap>() {
            @Override
            public Bitmap apply(List<Player> input) {
                return input.get(0).getImage();
            }
        });
        mlPosition = Transformations.map(mlPlayer, new Function<List<Player>, String>() {
            @Override
            public String apply(List<Player> input) {
                return "포지션: " + input.get(0).getPosition();
            }
        });
        mlHeight = Transformations.map(mlPlayer, new Function<List<Player>, String>() {
            @Override
            public String apply(List<Player> input) {
                return "신장: " + input.get(0).getHeight();
            }
        });
        mlFoot = Transformations.map(mlPlayer, new Function<List<Player>, String>() {
            @Override
            public String apply(List<Player> input) {
                return "주발: " + input.get(0).getFoot();
            }
        });
        mlAge = Transformations.map(mlPlayer, new Function<List<Player>, String>() {
            @Override
            public String apply(List<Player> input) {
                return "나이: " + input.get(0).getAge() + "세";
            }
        });
        mlShirt = Transformations.map(mlPlayer, new Function<List<Player>, String>() {
            @Override
            public String apply(List<Player> input) {
                return "등번호: " + input.get(0).getShirt();
            }
        });
    }


}