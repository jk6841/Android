package com.jk.soccer.etc;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class MyBindingAdapter {

    @BindingAdapter({"glideUrl"})
    public static void imgLoad(ImageView imageView, String url){
        Glide.with(imageView.getContext())
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(imageView);
    }

}