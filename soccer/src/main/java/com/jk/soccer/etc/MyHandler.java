package com.jk.soccer.etc;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.jk.soccer.R;
import com.jk.soccer.viewModel.MyViewModel;
import com.jk.soccer.viewModel.SearchViewModel;

public class MyHandler {

    public void onPlayerListClick(View view, Integer index){
        NavController navController = Navigation.findNavController(view);
        Bundle args = new Bundle();
        args.putInt("index", index);
        navController.navigate(R.id.action_nav_playerList_to_nav_playerInfo, args);
    }

    public void onMatchListClick(View view, Integer index){
        NavController navController = Navigation.findNavController(view);
        Bundle args = new Bundle();
        args.putInt("index", index);
        navController.navigate(R.id.action_nav_matchList_to_nav_matchInfo, args);
    }

}
