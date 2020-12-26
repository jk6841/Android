package com.jk.soccer.etc;

import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jk.soccer.model.local.TableMatch;
import com.jk.soccer.viewModel.MyViewModel;

import java.util.List;

public class MyBindingAdapter {

    @BindingAdapter({"glideUrl"})
    public static void imgLoad(ImageView imageView, String url){
        Glide.with(imageView.getContext())
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(imageView);
    }

    @BindingAdapter({"timeText"})
    public static void showDate(TextView view, TableMatch match){
        String yearStr = match.getYear().toString() + "년";
        String monthStr = match.getMonth().toString() + "월";
        String dateStr = match.getDate().toString() + "일";
        String dayStr = match.getDay();
        String timeStr = match.getTime();
        String[] text = new String[] {yearStr, monthStr, dateStr, dayStr, timeStr};
        view.setText(TextUtils.join(" ", text));
    }

    @BindingAdapter({"scoreText"})
    public static void showScore(TextView view, TableMatch match){
        String[] text;
        String vs = "vs";
        if (match.getStarted()){
            text = new String[]{
                    match.getHomeScore().toString(),
                    vs,
                    match.getAwayScore().toString()};
            view.setText(TextUtils.join("  ", text));
        }
        else
            view.setText(vs);
    }

}