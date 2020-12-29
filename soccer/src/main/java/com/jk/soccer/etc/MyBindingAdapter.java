package com.jk.soccer.etc;

import android.content.res.Resources;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jk.soccer.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MyBindingAdapter {

    @BindingAdapter(value = {"objectID", "type"})
    public static void imgLoad(ImageView imageView, String ID, Type type){
        String url;
        Resources resources = imageView.getContext().getResources();
        switch (type){
            case LEAGUE:
                url = resources.getString(R.string.league_image_url) + ID;
                break;
            case TEAM:
                url = resources.getString(R.string.team_image_url) + ID;
                break;
            case PERSON:
                url = resources.getString(R.string.player_image_url)
                        + ID + resources.getString(R.string.png);
                break;
            case COUNTRY:
                url = resources.getString(R.string.country_image_url)
                        + ID + resources.getString(R.string.png);
                break;
            default:
                url = "";
                break;
        }
        if (!url.isEmpty())
            Glide.with(imageView.getContext())
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .into(imageView);
    }

    @BindingAdapter(value = {"list", "handler", "holder"},requireAll = false)
    public static void recyclerView(RecyclerView view, List<?> list, Handler handler, Integer holder){
        NewRecyclerViewAdapter<?> adapter = (NewRecyclerViewAdapter<?>)(view.getAdapter());
        if (adapter == null){
            adapter = new NewRecyclerViewAdapter<>(handler, holder);
            view.setAdapter(adapter);
        }
        adapter.setList(list);
    }

    @BindingAdapter(value = "roleColor")
    public static void roleColor(TextView view, Role role){
        int textColor = (role == Role.NONE)? R.color.black : R.color.white;
        int background;
        switch (role){
            case COACH:
                background = R.drawable.edge_black;
                break;
            case FW:
                background = R.drawable.edge_red;
                break;
            case MF:
                background = R.drawable.edge_lightgreen;
                break;
            case DF:
                background = R.drawable.edge_skyblue;
                break;
            case GK:
                background = R.drawable.edge_orange;
                break;
            default:
                background = R.drawable.edge;

        }
        Resources resources = view.getContext().getResources();
        view.setTextColor(resources.getColor(textColor));
        view.setBackground(ContextCompat.getDrawable(view.getContext(), background));
    }

}