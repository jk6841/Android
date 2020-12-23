package com.jk.soccer.ui;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.jk.soccer.etc.MyParser;
import com.jk.soccer.data.Repository;
import com.jk.soccer.data.local.TableMatch;
import com.jk.soccer.data.local.TablePlayer;
import com.jk.soccer.data.response.Player;
import com.jk.soccer.ui.matchInfo.Event;

import java.util.ArrayList;
import java.util.List;

public class MyViewModel extends AndroidViewModel {

    public MyViewModel(@NonNull Application application) {
        super(application);
        repository = Repository.getInstance(application);
        playerLiveDataList = new ArrayList<>();
        idLiveDataList = new ArrayList<>();
        nameLiveDataList = new ArrayList<>();
        positionLiveDataList = new ArrayList<>();
        heightLiveDataList = new ArrayList<>();
        footLiveDataList = new ArrayList<>();
        birthLiveDataList = new ArrayList<>();
        shirtLiveDataList = new ArrayList<>();
        bookmarkLiveDataList = new ArrayList<>();
        teamLiveDataList = new ArrayList<>();
        matchLiveDataList = new ArrayList<>();
        eventListLiveDataList = new ArrayList<>();
        matchTitleLiveDataList = new ArrayList<>();
        timeLiveDataList = new ArrayList<>();
        stadiumLiveDataList = new ArrayList<>();
        statusLiveDataList = new ArrayList<>();
        scoreLiveDataList = new ArrayList<>();
        homeLiveDataList = new ArrayList<>();
        homeImageLiveDataList = new ArrayList<>();
        awayLiveDataList = new ArrayList<>();
        awayImageLiveDataList = new ArrayList<>();
        bestPlayerIDLiveDataList = new ArrayList<>();
        bestPlayerNameLiveDataList = new ArrayList<>();
        bestTeamLiveDataList = new ArrayList<>();
    }

    public void initPlayer(){
        List<TablePlayer> players = repository.getPlayer();
        for (int i = 0; i < players.size(); i++){
            LiveData<Player> playerLiveData
                    = repository.getPlayerLiveData(players.get(i).getId());
            LiveData<Integer> idLiveData
                    = Transformations.map(playerLiveData, Player::getId);
            LiveData<String> nameLiveData
                    = Transformations.map(playerLiveData, Player::getName);
            LiveData<String> positionLiveData
                    = Transformations.map(playerLiveData, Player::getPosition);
            LiveData<String> heightLiveData
                    = Transformations.map(playerLiveData, input -> input.getHeight() + "cm");
            LiveData<String> footLiveData
                    = Transformations.map(playerLiveData, Player::getFoot);
            LiveData<String> birthLiveData
                    = Transformations.map(playerLiveData, Player::getBirth);
            LiveData<String> shirtLiveData
                    = Transformations.map(playerLiveData, input -> input.getShirt() + "번");
            LiveData<Boolean> bookmarkLiveData
                    = Transformations.map(playerLiveData, Player::getBookmark);
            LiveData<String> teamLiveData
                    = Transformations.map(playerLiveData, Player::getTeam);
            if (sortPlayer){
                playerLiveDataList.set(i, playerLiveData);
                idLiveDataList.set(i, idLiveData);
                nameLiveDataList.set(i, nameLiveData);
                positionLiveDataList.set(i, positionLiveData);
                heightLiveDataList.set(i, heightLiveData);
                footLiveDataList.set(i, footLiveData);
                birthLiveDataList.set(i, birthLiveData);
                shirtLiveDataList.set(i, shirtLiveData);
                bookmarkLiveDataList.set(i, bookmarkLiveData);
                teamLiveDataList.set(i, teamLiveData);
            } else {
                playerLiveDataList.add(playerLiveData);
                idLiveDataList.add(idLiveData);
                nameLiveDataList.add(nameLiveData);
                positionLiveDataList.add(positionLiveData);
                heightLiveDataList.add(heightLiveData);
                footLiveDataList.add(footLiveData);
                birthLiveDataList.add(birthLiveData);
                shirtLiveDataList.add(shirtLiveData);
                bookmarkLiveDataList.add(bookmarkLiveData);
                teamLiveDataList.add(teamLiveData);
            }
        }
        sortPlayer = true;
    }

    public void initMatch(){
        List<TableMatch> matches = repository.getMatch();
        for (int i = 0; i < matches.size(); i++){
            LiveData<TableMatch> matchLiveData
                    = repository.getMatchLiveData(matches.get(i).getId());
            LiveData<String> matchTitleLiveData
                    = Transformations.map(matchLiveData, TableMatch::getName);
            LiveData<String> timeLiveData
                    = Transformations.map(matchLiveData, new Function<TableMatch, String>() {
                @Override
                public String apply(TableMatch input) {
                    String year = input.getYear().toString() + "년";
                    String month = input.getMonth().toString() + "월";
                    String date = input.getDate().toString() + "일";
                    String day = input.getDay();
                    String time = input.getTime();
                    String[] text = new String[]{
                            year, month, date, day, time
                    };
                    return TextUtils.join(" ", text);
                }
            });
            LiveData<String> stadiumLiveData
                    = Transformations.map(matchLiveData, TableMatch::getStadium);
            LiveData<String> statusLiveData
                    = Transformations.map(matchLiveData, new Function<TableMatch, String>() {
                @Override
                public String apply(TableMatch input) {
                    if (input.getFinished()){
                        return "경기 종료";
                    }
                    if (!input.getStarted()){
                        return "경기 시작 전";
                    }
                    return "";
                }
            });
            LiveData<String> scoreLiveData
                    = Transformations.map(matchLiveData, new Function<TableMatch, String>() {
                @Override
                public String apply(TableMatch input) {
                    String vs = " vs ";
                    if (!input.getStarted()){
                        return vs;
                    }
                    Integer homeScore = input.getHomeScore();
                    Integer awayScore = input.getAwayScore();
                    return homeScore.toString() + vs + awayScore.toString();
                }
            });
            LiveData<String> homeLiveData
                    = Transformations.map(matchLiveData, TableMatch::getHomeName);
            LiveData<String> homeImageLiveData
                    = Transformations.map(matchLiveData, TableMatch::getHomeImage);
            LiveData<String> awayLiveData
                    = Transformations.map(matchLiveData, TableMatch::getAwayName);
            LiveData<String> awayImageLiveData
                    = Transformations.map(matchLiveData, TableMatch::getAwayImage);

            LiveData<String> bestPlayerNameLiveData
                    = Transformations.map(matchLiveData, TableMatch::getBestPlayerName);

            LiveData<Integer> bestPlayerIDLiveData
                    = Transformations.map(matchLiveData, TableMatch::getBestPlayerID);
            LiveData<String> bestTeamLiveData
                    = Transformations.map(matchLiveData, TableMatch::getBestTeam);

            LiveData<ArrayList<Event>> eventListLiveData
                    = Transformations.map(matchLiveData, new Function<TableMatch, ArrayList<Event>>() {
                @Override
                public ArrayList<Event> apply(TableMatch input) {
                    String eventString = input.getEvent();
                    return MyParser.myEventList(eventString);
                }
            });
            matchLiveDataList.add(matchLiveData);
            matchTitleLiveDataList.add(matchTitleLiveData);
            timeLiveDataList.add(timeLiveData);
            stadiumLiveDataList.add(stadiumLiveData);
            statusLiveDataList.add(statusLiveData);
            scoreLiveDataList.add(scoreLiveData);
            homeLiveDataList.add(homeLiveData);
            homeImageLiveDataList.add(homeImageLiveData);
            awayLiveDataList.add(awayLiveData);
            awayImageLiveDataList.add(awayImageLiveData);
            bestPlayerIDLiveDataList.add(bestPlayerIDLiveData);
            bestPlayerNameLiveDataList.add(bestPlayerNameLiveData);
            bestTeamLiveDataList.add(bestTeamLiveData);
            eventListLiveDataList.add(eventListLiveData);
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        repository.close();
    }

    public List<TablePlayer> getPlayers() { return repository.getPlayer(); }

    public List<TableMatch> getMatches() { return repository.getMatch(); }

    public List<Event> getEvents(Integer index) {
        List<TableMatch> matches = getMatches();
        String eventString = matches.get(index).getEvent();
        return MyParser.myEventList(eventString);
    }

    public LiveData<Integer> getIdLiveData(Integer index){
        return idLiveDataList.get(index);
    }

    public LiveData<String> getNameLiveData(Integer index) {
        return nameLiveDataList.get(index);
    }

    public LiveData<String> getPositionLiveData(Integer index) {
        return positionLiveDataList.get(index);
    }

    public LiveData<String> getHeightLiveData(Integer index) {
        return heightLiveDataList.get(index);
    }

    public LiveData<String> getFootLiveData(Integer index) {
        return footLiveDataList.get(index);
    }

    public LiveData<String> getBirthLiveData(Integer index) {
        return birthLiveDataList.get(index);
    }

    public LiveData<String> getShirtLiveData(Integer index) {
        return shirtLiveDataList.get(index);
    }

    public LiveData<Boolean> getBookmarkLiveData(Integer index) {
        return bookmarkLiveDataList.get(index);
    }

    public void setBookmark(Integer index){
        repository.bookmark(
                !bookmarkLiveDataList.get(index).getValue(),
                idLiveDataList.get(index).getValue());
    }

    public LiveData<String> getTeamLiveData(Integer index){
        return teamLiveDataList.get(index);
    }

    public LiveData<String> getMatchTitleLiveData(Integer index) {
        return matchTitleLiveDataList.get(index);
    }

    public LiveData<String> getTimeLiveData(Integer index) {
        return timeLiveDataList.get(index);
    }

    public LiveData<String> getStadiumLiveData(Integer index) {
        return stadiumLiveDataList.get(index);
    }

    public LiveData<String> getStatusLiveData(Integer index) {
        return statusLiveDataList.get(index);
    }

    public LiveData<String> getScoreLiveData(Integer index) {
        return scoreLiveDataList.get(index);
    }

    public LiveData<String> getHomeLiveData(Integer index) {
        return homeLiveDataList.get(index);
    }

    public LiveData<String> getHomeImageLiveData(Integer index) {
        return homeImageLiveDataList.get(index);
    }

    public LiveData<String> getAwayLiveData(Integer index) {
        return awayLiveDataList.get(index);
    }

    public LiveData<String> getAwayImageLiveData(Integer index) {
        return awayImageLiveDataList.get(index);
    }

    public LiveData<Integer> getBestPlayerIDLiveData(Integer index) {
        return bestPlayerIDLiveDataList.get(index);
    }

    public LiveData<String> getBestPlayerNameLiveData(Integer index) {
        return bestPlayerNameLiveDataList.get(index);
    }

    public LiveData<String> getBestTeamLiveData(Integer index) {
        return bestTeamLiveDataList.get(index);
    }

    public LiveData<ArrayList<Event>> getEventListLiveData(Integer index) {
        return eventListLiveDataList.get(index);
    }

    final private Repository repository;

    // For Player //
    final private ArrayList<LiveData<Player>> playerLiveDataList;
    final private ArrayList<LiveData<Integer>> idLiveDataList;
    final private ArrayList<LiveData<String>> nameLiveDataList;
    final private ArrayList<LiveData<String>> positionLiveDataList;
    final private ArrayList<LiveData<String>> heightLiveDataList;
    final private ArrayList<LiveData<String>> footLiveDataList;
    final private ArrayList<LiveData<String>> birthLiveDataList;
    final private ArrayList<LiveData<String>> shirtLiveDataList;
    final private ArrayList<LiveData<Boolean>> bookmarkLiveDataList;
    final private ArrayList<LiveData<String>> teamLiveDataList;
    private Boolean sortPlayer = false;

    // For Match //
    final private ArrayList<LiveData<TableMatch>> matchLiveDataList;
    final private ArrayList<LiveData<String>> matchTitleLiveDataList;
    final private ArrayList<LiveData<String>> timeLiveDataList;
    final private ArrayList<LiveData<String>> stadiumLiveDataList;
    final private ArrayList<LiveData<String>> statusLiveDataList;
    final private ArrayList<LiveData<String>> scoreLiveDataList;
    final private ArrayList<LiveData<String>> homeLiveDataList;
    final private ArrayList<LiveData<String>> homeImageLiveDataList;
    final private ArrayList<LiveData<String>> awayLiveDataList;
    final private ArrayList<LiveData<String>> awayImageLiveDataList;
    final private ArrayList<LiveData<Integer>> bestPlayerIDLiveDataList;
    final private ArrayList<LiveData<String>> bestPlayerNameLiveDataList;
    final private ArrayList<LiveData<String>> bestTeamLiveDataList;
    final private ArrayList<LiveData<ArrayList<Event>>> eventListLiveDataList;
    private Boolean sortMatch = false;

}