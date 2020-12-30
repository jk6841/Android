package com.jk.soccer.viewModel;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.jk.soccer.etc.Handler;
import com.jk.soccer.model.Repository;
import com.jk.soccer.model.local.TableSearch;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SearchViewModel extends ViewModel {

    public SearchViewModel() {
        repository = Repository.getInstance();
        name = new MutableLiveData<>();
        name.setValue("");
        list = Transformations.switchMap(name, repository::search);
        progress = new MutableLiveData<>();
        progress.setValue("");
        data = new MutableLiveData<>();
        today = "최근 업데이트: "
                + new SimpleDateFormat("yyyy년 MM월 dd일", Locale.KOREA)
                .format(new Date());
        update();
    }

    public void update(){
        data.setValue("데이터를 받는 중");
        repository.updateDB(result -> {
            if (result){
                progress.postValue("성공");
                data.postValue(today);
            } else {
                progress.postValue("실패");
                data.postValue("데이터 다운로드 실패");
            }
        });
    }

    public MutableLiveData<String> getName() {
        return name;
    }

    public LiveData<String> getProgress() {
        return progress;
    }

    public class LeagueIndexManager implements Handler {
        @Override
        public void onClick(View v, Object... params) {
//            setLeagueIndex(params[0]);
//            setTeamIndex(-1);
        }
    }

    public class TeamIndexManager implements Handler {
        @Override
        public void onClick(View v, Object... params) {
//            setTeamIndex(params[0]);
        }
    }

    public MutableLiveData<String> getData() {
        return data;
    }

    public LiveData<List<TableSearch>> getList() {
        return list;
    }

    private final Repository repository;
    final private LiveData<List<TableSearch>> list;
    final private MutableLiveData<String> name;
    final private MutableLiveData<String> progress;
    final private MutableLiveData<String> data;
    final private Date date = new Date();
    final private String today;
}
