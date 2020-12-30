package com.jk.soccer.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.jk.soccer.etc.RepositoryCallback;
import com.jk.soccer.model.Repository;

import org.json.JSONObject;
import static com.jk.soccer.etc.MyJson.myJSONObject;
import static com.jk.soccer.etc.MyJson.myJSONString;

public class TeamInfoViewModel extends AndroidViewModel {
    public TeamInfoViewModel(@NonNull Application application) {
        super(application);
        repository = Repository.getInstance(application);
        name = new MutableLiveData<>();
        name.setValue("");
    }

    public void getTeamInfoAsync(Integer ID){
        repository.getTeamInfoAsync(ID, result -> {
            JSONObject jsonObject = myJSONObject(result);
            JSONObject jsonDetails = myJSONObject(jsonObject, "details");
            name.postValue(myJSONString(jsonDetails, "name"));
        });
    }

    public MutableLiveData<String> getName() {
        return name;
    }

    final private Repository repository;
    final private MutableLiveData<String> name;
}
