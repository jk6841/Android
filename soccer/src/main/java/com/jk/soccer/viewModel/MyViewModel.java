package com.jk.soccer.viewModel;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;


public class MyViewModel extends AndroidViewModel {

    public MyViewModel(@NonNull Application application) {
        super(application);
    }

//    public void initPlayer(){
//        playerLiveDataList = repository.getPlayerLiveData();
//        for (int i = 0; i < playerLiveDataList.size(); i++){
//            LiveData<Player> playerLiveData = playerLiveDataList.get(i);
//            LiveData<Integer> idLiveData
//                    = Transformations.map(playerLiveData, Player::getId);
//            LiveData<String> nameLiveData
//                    = Transformations.map(playerLiveData, Player::getName);
//            LiveData<String> positionLiveData
//                    = Transformations.map(playerLiveData, Player::getPosition);
//            LiveData<String> heightLiveData
//                    = Transformations.map(playerLiveData, input -> input.getHeight() + "cm");
//            LiveData<String> footLiveData
//                    = Transformations.map(playerLiveData, Player::getFoot);
//            LiveData<String> birthLiveData
//                    = Transformations.map(playerLiveData, Player::getBirth);
//            LiveData<String> shirtLiveData
//                    = Transformations.map(playerLiveData, input -> input.getShirt() + "번");
//            LiveData<Boolean> bookmarkLiveData
//                    = Transformations.map(playerLiveData, Player::getBookmark);
//            LiveData<String> teamLiveData
//                    = Transformations.map(playerLiveData, Player::getTeam);
//            if (sortPlayer){
//                idLiveDataList.set(i, idLiveData);
//                nameLiveDataList.set(i, nameLiveData);
//                positionLiveDataList.set(i, positionLiveData);
//                heightLiveDataList.set(i, heightLiveData);
//                footLiveDataList.set(i, footLiveData);
//                birthLiveDataList.set(i, birthLiveData);
//                shirtLiveDataList.set(i, shirtLiveData);
//                bookmarkLiveDataList.set(i, bookmarkLiveData);
//                teamLiveDataList.set(i, teamLiveData);
//            } else {
//                idLiveDataList.add(idLiveData);
//                nameLiveDataList.add(nameLiveData);
//                positionLiveDataList.add(positionLiveData);
//                heightLiveDataList.add(heightLiveData);
//                footLiveDataList.add(footLiveData);
//                birthLiveDataList.add(birthLiveData);
//                shirtLiveDataList.add(shirtLiveData);
//                bookmarkLiveDataList.add(bookmarkLiveData);
//                teamLiveDataList.add(teamLiveData);
//            }
//        }
//        sortPlayer = true;
//    }
//
//    public void initMatch(){
//        List<TableMatch> matches = repository.getMatch();
//        matchLiveDataList = repository.getMatchLiveData();
//        List<Integer> count = repository.count(2);
//        for (int i = 0; i < matchLiveDataList.size(); i++){
//            LiveData<TableMatch> matchLiveData = matchLiveDataList.get(i);
//            LiveData<String> matchTitleLiveData
//                    = Transformations.map(matchLiveData, TableMatch::getName);
//            LiveData<String> timeLiveData
//                    = Transformations.map(matchLiveData,
//                    input -> {
//                        String year = input.getYear().toString() + "년";
//                        String month = input.getMonth().toString() + "월";
//                        String date = input.getDate().toString() + "일";
//                        String day = input.getDay();
//                        String time = input.getTime();
//                        String[] text = new String[]{
//                                year, month, date, day, time
//                        };
//                        return TextUtils.join(" ", text);
//                    });
//            LiveData<String> stadiumLiveData
//                    = Transformations.map(matchLiveData, TableMatch::getStadium);
//            LiveData<String> statusLiveData
//                    = Transformations.map(matchLiveData,
//                    input -> {
//                        if (input.getFinished()){
//                            return "경기 종료";
//                        }
//                        if (!input.getStarted()){
//                            return "경기 시작 전";
//                        }
//                        return "";
//                    });
//            LiveData<String> scoreLiveData
//                    = Transformations.map(matchLiveData,
//                    input -> {
//                        String vs = " vs ";
//                        if (!input.getStarted()){
//                            return vs;
//                        }
//                        Integer homeScore = input.getHomeScore();
//                        Integer awayScore = input.getAwayScore();
//                        return homeScore.toString() + vs + awayScore.toString();
//                    });
//            LiveData<String> homeLiveData
//                    = Transformations.map(matchLiveData, TableMatch::getHomeName);
//            LiveData<String> homeImageLiveData
//                    = Transformations.map(matchLiveData, TableMatch::getHomeImage);
//            LiveData<String> awayLiveData
//                    = Transformations.map(matchLiveData, TableMatch::getAwayName);
//            LiveData<String> awayImageLiveData
//                    = Transformations.map(matchLiveData, TableMatch::getAwayImage);
//
//            LiveData<String> bestPlayerNameLiveData
//                    = Transformations.map(matchLiveData, TableMatch::getBestPlayerName);
//
//            LiveData<Integer> bestPlayerIDLiveData
//                    = Transformations.map(matchLiveData, TableMatch::getBestPlayerID);
//            LiveData<String> bestTeamLiveData
//                    = Transformations.map(matchLiveData, TableMatch::getBestTeam);
//
//            LiveData<List<Event>> eventListLiveData
//                    = Transformations.map(matchLiveData, TableMatch::getEvent);
//
//            ArrayList<LiveData<Event>> eventLiveDataList = new ArrayList<>();
//            for (int j = 0; j < count.get(i); j++){
//                int k = j;
//                try {
//                    LiveData<Event> event = Transformations.map(matchLiveData, input -> input.getEvent().get(k));
//                    eventLiveDataList.add(event);
//                } catch (Exception e){
//                    break;
//                }
//            }
//
//            LiveData<List<Lineup>> homeLineupLiveData
//                    = Transformations.map(matchLiveData, TableMatch::getHomeLineup);
//            LiveData<List<Lineup>> awayLineupLiveData
//                    = Transformations.map(matchLiveData, TableMatch::getAwayLineup);
//            if (i < length) {
//                matchTitleLiveDataList.set(i,matchTitleLiveData);
//                timeLiveDataList.set(i,timeLiveData);
//                stadiumLiveDataList.set(i,stadiumLiveData);
//                statusLiveDataList.set(i,statusLiveData);
//                scoreLiveDataList.set(i,scoreLiveData);
//                homeLiveDataList.set(i,homeLiveData);
//                homeImageLiveDataList.set(i,homeImageLiveData);
//                awayLiveDataList.set(i,awayLiveData);
//                awayImageLiveDataList.set(i,awayImageLiveData);
//                bestPlayerIDLiveDataList.set(i,bestPlayerIDLiveData);
//                bestPlayerNameLiveDataList.set(i,bestPlayerNameLiveData);
//                bestTeamLiveDataList.set(i,bestTeamLiveData);
//                eventListLiveDataList.set(i,eventListLiveData);
//                eventsList.set(i, eventLiveDataList);
//                homeLineupLiveDataList.set(i, homeLineupLiveData);
//                awayLineupLiveDataList.set(i, awayLineupLiveData);
//            } else{
//                matchTitleLiveDataList.add(matchTitleLiveData);
//                timeLiveDataList.add(timeLiveData);
//                stadiumLiveDataList.add(stadiumLiveData);
//                statusLiveDataList.add(statusLiveData);
//                scoreLiveDataList.add(scoreLiveData);
//                homeLiveDataList.add(homeLiveData);
//                homeImageLiveDataList.add(homeImageLiveData);
//                awayLiveDataList.add(awayLiveData);
//                awayImageLiveDataList.add(awayImageLiveData);
//                bestPlayerIDLiveDataList.add(bestPlayerIDLiveData);
//                bestPlayerNameLiveDataList.add(bestPlayerNameLiveData);
//                bestTeamLiveDataList.add(bestTeamLiveData);
//                eventListLiveDataList.add(eventListLiveData);
//                eventsList.add(eventLiveDataList);
//                homeLineupLiveDataList.add(homeLineupLiveData);
//                awayLineupLiveDataList.add(awayLineupLiveData);
//            }
//        }
//        length = matches.size();
//    }

    @Override
    protected void onCleared() {
        super.onCleared();
//        repository.close();
    }

    public void close(){
        onCleared();
    }

//    public Integer countPlayers(){
//        return playerLiveDataList.size();
//    }
//
//    public Integer countMatches(){
//        return matchLiveDataList.size();
//    }

//    public Integer countEvents(Integer index){
//        return eventsList.get(index).size();
//    }
//    public Integer countHomeLineup(Integer index){
//        return getMatches().get(index).getHomeLineup().size();
//    }
//    public Integer countAwayLineup(Integer index){
//        return getMatches().get(index).getAwayLineup().size();
//    }

//    public List<TableMatch> getMatches() { return repository.getMatch(); }

//    public MutableLiveData<Integer> getMatchTab() {
//        return matchTab;
//    }
//
//    public void setMatchTab(Integer tab){
//        matchTab.setValue(tab);
//    }

//    public List<LiveData<Player>> getPlayerLiveDataList() { return playerLiveDataList; }
//
//    public List<LiveData<TableMatch>> getMatchLiveDataList() {
//        return matchLiveDataList;
//    }

//    public LiveData<Integer> getIdLiveData(Integer index){
//        return idLiveDataList.get(index);
//    }
//
//    public LiveData<String> getNameLiveData(Integer index) {
//        return nameLiveDataList.get(index);
//    }
//
//    public LiveData<String> getPositionLiveData(Integer index) {
//        return positionLiveDataList.get(index);
//    }
//
//    public LiveData<String> getHeightLiveData(Integer index) {
//        return heightLiveDataList.get(index);
//    }
//
//    public LiveData<String> getFootLiveData(Integer index) {
//        return footLiveDataList.get(index);
//    }
//
//    public LiveData<String> getBirthLiveData(Integer index) {
//        return birthLiveDataList.get(index);
//    }
//
//    public LiveData<String> getShirtLiveData(Integer index) {
//        return shirtLiveDataList.get(index);
//    }
//
//    public LiveData<Boolean> getBookmarkLiveData(Integer index) {
//        return bookmarkLiveDataList.get(index);
    }

//    public void setBookmark(Integer index){
//        repository.bookmark(
//                !bookmarkLiveDataList.get(index).getValue(),
//                idLiveDataList.get(index).getValue());
//    }

//    public LiveData<String> getTeamLiveData(Integer index){
//        return teamLiveDataList.get(index);
//    }
//
//    public LiveData<String> getMatchTitleLiveData(Integer index) {
//        return matchTitleLiveDataList.get(index);
//    }
//
//    public LiveData<String> getTimeLiveData(Integer index) {
//        return timeLiveDataList.get(index);
//    }
//
//    public LiveData<String> getStadiumLiveData(Integer index) {
//        return stadiumLiveDataList.get(index);
//    }
//
//    public LiveData<String> getStatusLiveData(Integer index) {
//        return statusLiveDataList.get(index);
//    }
//
//    public LiveData<String> getScoreLiveData(Integer index) {
//        return scoreLiveDataList.get(index);
//    }
//
//    public LiveData<String> getHomeLiveData(Integer index) {
//        return homeLiveDataList.get(index);
//    }
//
//    public LiveData<String> getHomeImageLiveData(Integer index) {
//        return homeImageLiveDataList.get(index);
//    }
//
//    public LiveData<String> getAwayLiveData(Integer index) {
//        return awayLiveDataList.get(index);
//    }
//
//    public LiveData<String> getAwayImageLiveData(Integer index) {
//        return awayImageLiveDataList.get(index);
//    }

//    public LiveData<Integer> getBestPlayerIDLiveData(Integer index) {
//        return bestPlayerIDLiveDataList.get(index);
//    }
//
//    public LiveData<String> getBestPlayerNameLiveData(Integer index) {
//        return bestPlayerNameLiveDataList.get(index);
//    }
//
//    public LiveData<String> getBestTeamLiveData(Integer index) {
//        return bestTeamLiveDataList.get(index);
//    }
//
//    public LiveData<List<Event>> getEventListLiveData(Integer index) {
//        return null;
//    }

//    public LiveData<List<Lineup>> getHomeLineupLiveData(Integer index) {
//        return homeLineupLiveDataList.get(index);
//    }
//
//    public LiveData<List<Lineup>> getAwayLineupLiveData(Integer index) {
//        return awayLineupLiveDataList.get(index);
//    }

//    final private Repository repository;

    // For Player //
//    private List<LiveData<Player>> playerLiveDataList;
//    final private ArrayList<LiveData<Integer>> idLiveDataList;
//    final private ArrayList<LiveData<String>> nameLiveDataList;
//    final private ArrayList<LiveData<String>> positionLiveDataList;
//    final private ArrayList<LiveData<String>> heightLiveDataList;
//    final private ArrayList<LiveData<String>> footLiveDataList;
//    final private ArrayList<LiveData<String>> birthLiveDataList;
//    final private ArrayList<LiveData<String>> shirtLiveDataList;
//    final private ArrayList<LiveData<Boolean>> bookmarkLiveDataList;
//    final private ArrayList<LiveData<String>> teamLiveDataList;
//    private Boolean sortPlayer = false;
//
//    // For Match //
//    private List<LiveData<TableMatch>> matchLiveDataList;
//    final private ArrayList<LiveData<String>> matchTitleLiveDataList;
//    final private ArrayList<LiveData<String>> timeLiveDataList;
//    final private ArrayList<LiveData<String>> stadiumLiveDataList;
//    final private ArrayList<LiveData<String>> statusLiveDataList;
//    final private ArrayList<LiveData<String>> scoreLiveDataList;
//    final private ArrayList<LiveData<String>> homeLiveDataList;
//    final private ArrayList<LiveData<String>> homeImageLiveDataList;
//    final private ArrayList<LiveData<String>> awayLiveDataList;
//    final private ArrayList<LiveData<String>> awayImageLiveDataList;
//    final private ArrayList<LiveData<Integer>> bestPlayerIDLiveDataList;
//    final private ArrayList<LiveData<String>> bestPlayerNameLiveDataList;
//    final private ArrayList<LiveData<String>> bestTeamLiveDataList;
//    final private ArrayList<LiveData<List<Event>>> eventListLiveDataList;
//    final private ArrayList<ArrayList<LiveData<Event>>> eventsList;
//    final private ArrayList<LiveData<List<Lineup>>> homeLineupLiveDataList;
//    final private ArrayList<LiveData<List<Lineup>>> awayLineupLiveDataList;
//    private Integer length = 0;
//    final private MutableLiveData<Integer> matchTab;
//
//    public String ToastTest(){
//        return repository.NetworkTest();
//    }