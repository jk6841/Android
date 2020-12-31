package com.jk.soccer.model;

import androidx.lifecycle.MutableLiveData;

import com.jk.soccer.etc.MyCallback;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class UpdateCallback implements MyCallback<Boolean> {
    final private String today;
    final private MutableLiveData<String> progress;

    public UpdateCallback(MutableLiveData<String> progress) {
        this.progress = progress;
        today = "최근 업데이트: " + new SimpleDateFormat("yyyy년 MM월 dd일",Locale.KOREA)
                .format(new Date());
    }

    @Override
    public void onComplete(Boolean result) {
        progress.postValue(result? today : "데이터 다운로드 실패");
    }

    public void onProgress(String count){
        progress.postValue(count + "개 작업 남음");
    }
}
