package com.jk.soccer.etc;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.jk.soccer.R;
import com.jk.soccer.etc.enumeration.Type;
import com.jk.soccer.view.activity.MainActivity;

public class InfoHandler implements Handler {

    final private Activity activity;;

    public InfoHandler(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onClick(View v, Object... params) {
        NavController navController = Navigation.findNavController(v);
        Bundle args = new Bundle();
        Integer ID = (Integer)params[0];
        Integer parentID = (Integer)params[1];
        String name = (String)params[2];
        Type type = (Type)params[3];
        Integer nav = null;
        args.putInt("id", ID);
        args.putInt("parent", parentID);
        args.putString("name", name);
        switch (type){
            case PERSON:
                nav = R.id.action_nav_search_to_nav_playerInfo;
                break;
            case TEAM:
                nav = R.id.action_nav_search_to_nav_teamInfo;
                break;
            case LEAGUE:
                nav = R.id.action_nav_search_to_nav_leagueInfo;
            default:
                break;
        }
        if (nav != null) {
            ((MainActivity) activity).hideKeyboard();
            navController.navigate(nav, args);
        }
    }
}
