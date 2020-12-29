package com.jk.soccer.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.jk.soccer.model.Repository;

import org.json.JSONArray;
import org.json.JSONObject;

import static com.jk.soccer.etc.MyParser.myJSONArray;
import static com.jk.soccer.etc.MyParser.myJSONInt;
import static com.jk.soccer.etc.MyParser.myJSONObject;
import static com.jk.soccer.etc.MyParser.myJSONString;

public class PlayerInfoViewModel extends AndroidViewModel {
    public PlayerInfoViewModel(@NonNull Application application) {
        super(application);
        repository = Repository.getInstance(application);
        name = new MutableLiveData<>();
        name.setValue(emptyString);
        teamID = new MutableLiveData<>();
        teamID.setValue(0);
        team = new MutableLiveData<>();
        team.setValue(emptyString);
        shirt = new MutableLiveData<>();
        shirt.setValue(0);
        position = new MutableLiveData<>();
        position.setValue(emptyString);
        foot = new MutableLiveData<>();
        foot.setValue(emptyString);
        height = new MutableLiveData<>();
        height.setValue(emptyString);
        age = new MutableLiveData<>();
        age.setValue(0);
        country = new MutableLiveData<>();
        country.setValue(emptyString);
        countryID = new MutableLiveData<>();
        countryID.setValue(emptyString);
        test = new MutableLiveData<>();
        test.setValue(emptyString);
    }

    public void getPlayerInfo(Integer ID){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                String playerString = repository.getPlayerInfo(ID);
                JSONObject jsonObject = myJSONObject(playerString);
                name.postValue(myJSONString(jsonObject, "name"));
                JSONObject jsonOrigin = myJSONObject(jsonObject, "origin");
                teamID.postValue(myJSONInt(jsonOrigin, "teamId"));
                team.postValue(myJSONString(jsonOrigin, "teamName"));
                JSONObject jsonPositionDesc = myJSONObject(jsonOrigin, "positionDesc");
                position.postValue(myJSONString(jsonPositionDesc, "primaryPosition"));
                JSONArray jsonPlayerProps = myJSONArray(jsonObject, "playerProps");
                for (int i = 0; i < jsonPlayerProps.length(); i++){
                    JSONObject jsonItem = myJSONObject(jsonPlayerProps, i);
                    switch (myJSONString(jsonItem, "title")){
                        case "Height":
                            height.postValue(myJSONString(jsonItem, "value"));
                            break;
                        case "Preferred foot":
                            foot.postValue(myJSONString(jsonItem, "value"));
                            break;
                        case "Age":
                            age.postValue(myJSONInt(jsonItem, "value"));
                            break;
                        case "Country":
                            country.postValue(myJSONString(jsonItem, "value"));
                            JSONObject jsonIcon = myJSONObject(jsonItem, "icon");
                            countryID.postValue(myJSONString(jsonIcon, "id").toLowerCase());
                            break;
                        case "Shirt":
                            shirt.postValue(myJSONInt(jsonItem, "value"));
                            break;
                        default:
                            break;
                    }
                }
            }
        });
        t.start();
        try {
            t.join();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public MutableLiveData<String> getName() {
        return name;
    }

    public MutableLiveData<Integer> getTeamID() {
        return teamID;
    }

    public MutableLiveData<String> getTeam() {
        return team;
    }

    public MutableLiveData<Integer> getShirt() {
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

    public MutableLiveData<Integer> getAge() {
        return age;
    }

    public MutableLiveData<String> getCountry() {
        return country;
    }

    public MutableLiveData<String> getCountryID() {
        return countryID;
    }

    public MutableLiveData<String> getTest() {
        return test;
    }

    final private Repository repository;
    final private String emptyString = "";

    final private MutableLiveData<String> name;
    final private MutableLiveData<Integer> teamID;
    final private MutableLiveData<String> team ;
    final private MutableLiveData<Integer> shirt;
    final private MutableLiveData<String> position;
    final private MutableLiveData<String> foot;
    final private MutableLiveData<String> height;
    final private MutableLiveData<Integer> age;
    final private MutableLiveData<String> country;
    final private MutableLiveData<String> countryID;
    final private MutableLiveData<String> test;
}
