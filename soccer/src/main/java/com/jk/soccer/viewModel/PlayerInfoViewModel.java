package com.jk.soccer.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jk.soccer.model.Repository;

import static com.jk.soccer.etc.MyJson.myJSONArray;
import static com.jk.soccer.etc.MyJson.myJSONObject;
import static com.jk.soccer.etc.MyJson.myJSONString;

public class PlayerInfoViewModel extends ViewModel {
    public PlayerInfoViewModel() {
        repository = Repository.getInstance();
        name = new MutableLiveData<>();
        teamID = new MutableLiveData<>();
        team = new MutableLiveData<>();
        shirt = new MutableLiveData<>();
        position = new MutableLiveData<>();
        foot = new MutableLiveData<>();
        height = new MutableLiveData<>();
        age = new MutableLiveData<>();
        country = new MutableLiveData<>();
        countryID = new MutableLiveData<>();
    }

    public void init(){
        name.setValue(emptyString);
        teamID.setValue(emptyString);
        team.setValue(emptyString);
        shirt.setValue(emptyString);
        position.setValue(emptyString);
        foot.setValue(emptyString);
        height.setValue(emptyString);
        age.setValue(emptyString);
        country.setValue(emptyString);
        countryID.setValue(emptyString);
    }

    public void getPlayerInfoAsync(Integer ID){
        repository.getPlayerInfoAsync(ID, result -> {
            name.postValue(result.getName());
            teamID.postValue(result.getTeamID());
            team.postValue(result.getTeamName());
            position.postValue(result.getPosition());
            height.postValue(result.getHeight());
            foot.postValue(result.getFoot());
            age.postValue(result.getAge());
            country.postValue(result.getCountryName());
            countryID.postValue(result.getCountryID());
            shirt.postValue(result.getShirt());
        });
    }

//    public void getPlayerInfoAsync(Integer ID){
//        repository.getPlayerInfoAsync(ID, result -> {
//            JSONObject jsonObject = myJSONObject(result);
//            name.postValue(myJSONString(jsonObject, "name"));
//            JSONObject jsonOrigin = myJSONObject(jsonObject, "origin");
//            teamID.postValue(myJSONString(jsonOrigin, "teamId"));
//            team.postValue(myJSONString(jsonOrigin, "teamName"));
//            JSONObject jsonPositionDesc = myJSONObject(jsonOrigin, "positionDesc");
//            position.postValue(myJSONString(jsonPositionDesc, "primaryPosition"));
//            JSONArray jsonPlayerProps = myJSONArray(jsonObject, "playerProps");
//            for (int i = 0; i < jsonPlayerProps.length(); i++){
//                JSONObject jsonItem = myJSONObject(jsonPlayerProps, i);
//                switch (myJSONString(jsonItem, "title")){
//                    case "Height":
//                        height.postValue(myJSONString(jsonItem, "value"));
//                        break;
//                    case "Preferred foot":
//                        foot.postValue(myJSONString(jsonItem, "value"));
//                        break;
//                    case "Age":
//                        age.postValue(myJSONString(jsonItem, "value"));
//                        break;
//                    case "Country":
//                        country.postValue(myJSONString(jsonItem, "value"));
//                        JSONObject jsonIcon = myJSONObject(jsonItem, "icon");
//                        countryID.postValue(myJSONString(jsonIcon, "id").toLowerCase());
//                        break;
//                    case "Shirt":
//                        shirt.postValue(myJSONString(jsonItem, "value"));
//                        break;
//                    default:
//                        break;
//                }
//            }
//        });
//    }



//    public void getPlayerInfo(Integer ID){
//        Thread t = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                String playerString = repository.getPlayerInfo(ID);
//                JSONObject jsonObject = myJSONObject(playerString);
//                name.postValue(myJSONString(jsonObject, "name"));
//                JSONObject jsonOrigin = myJSONObject(jsonObject, "origin");
//                teamID.postValue(myJSONInt(jsonOrigin, "teamId"));
//                team.postValue(myJSONString(jsonOrigin, "teamName"));
//                JSONObject jsonPositionDesc = myJSONObject(jsonOrigin, "positionDesc");
//                position.postValue(myJSONString(jsonPositionDesc, "primaryPosition"));
//                JSONArray jsonPlayerProps = myJSONArray(jsonObject, "playerProps");
//                for (int i = 0; i < jsonPlayerProps.length(); i++){
//                    JSONObject jsonItem = myJSONObject(jsonPlayerProps, i);
//                    switch (myJSONString(jsonItem, "title")){
//                        case "Height":
//                            height.postValue(myJSONString(jsonItem, "value"));
//                            break;
//                        case "Preferred foot":
//                            foot.postValue(myJSONString(jsonItem, "value"));
//                            break;
//                        case "Age":
//                            age.postValue(myJSONInt(jsonItem, "value"));
//                            break;
//                        case "Country":
//                            country.postValue(myJSONString(jsonItem, "value"));
//                            JSONObject jsonIcon = myJSONObject(jsonItem, "icon");
//                            countryID.postValue(myJSONString(jsonIcon, "id").toLowerCase());
//                            break;
//                        case "Shirt":
//                            shirt.postValue(myJSONInt(jsonItem, "value"));
//                            break;
//                        default:
//                            break;
//                    }
//                }
//            }
//        });
//        t.start();
//        try {
//            t.join();
//        } catch (Exception e){
//        }
//    }

    public MutableLiveData<String> getName() {
        return name;
    }

    public MutableLiveData<String> getTeamID() {
        return teamID;
    }

    public MutableLiveData<String> getTeam() {
        return team;
    }

    public MutableLiveData<String> getShirt() {
        return shirt;
    }

    public MutableLiveData<String> getPosition() {
        return position;
    }

    public MutableLiveData<String> getFoot() {
        return foot;
    }

    public MutableLiveData<String> getHeight() {
        return height;
    }

    public MutableLiveData<String> getAge() {
        return age;
    }

    public MutableLiveData<String> getCountry() {
        return country;
    }

    public MutableLiveData<String> getCountryID() {
        return countryID;
    }

    final private Repository repository;
    final private String emptyString = "";

    final private MutableLiveData<String> name;
    final private MutableLiveData<String> teamID;
    final private MutableLiveData<String> team ;
    final private MutableLiveData<String> shirt;
    final private MutableLiveData<String> position;
    final private MutableLiveData<String> foot;
    final private MutableLiveData<String> height;
    final private MutableLiveData<String> age;
    final private MutableLiveData<String> country;
    final private MutableLiveData<String> countryID;
}
