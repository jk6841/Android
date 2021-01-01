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
import com.jk.soccer.etc.enumeration.Role;
import com.jk.soccer.etc.enumeration.Type;

import java.util.List;

public class MyBindingAdapter {

    @BindingAdapter(value = {"objectID", "type"})
    public static void imgLoad(ImageView imageView, String ID, Type type){
        String url;
        StringBuilder sb = new StringBuilder();
        Resources resources = imageView.getContext().getResources();
        switch (type){
            case LEAGUE:
                sb.append(resources.getString(R.string.league_image_url));
                sb.append(ID);
                url = sb.toString();
                break;
            case TEAM:
                sb.append(resources.getString(R.string.team_image_url));
                sb.append(ID);
                url = sb.toString();
                break;
            case PERSON:
                sb.append(resources.getString(R.string.player_image_url));
                sb.append(ID);
                sb.append(resources.getString(R.string.png));
                url = sb.toString();
                break;
            case COUNTRY:
                sb.append(resources.getString(R.string.country_image_url));
                sb.append(ID);
                sb.append(resources.getString(R.string.png));
                url = sb.toString();
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
        RecyclerViewAdapter<?> adapter = (RecyclerViewAdapter<?>)(view.getAdapter());
        if (adapter == null){
            adapter = new RecyclerViewAdapter<>(handler, holder);
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