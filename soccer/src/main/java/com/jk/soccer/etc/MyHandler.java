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

    public void onEventClick(View view, MyViewModel viewModel){
        viewModel.setMatchTab(1);
    }

    public void onRecyclerViewClick(View view, SearchViewModel viewModel){
        Toast.makeText(view.getContext(), "aaaa", Toast.LENGTH_SHORT).show();
    }

    public void onLeagueClick(View view, SearchViewModel viewModel, Integer index){
        viewModel.setLeagueIndex(index);
    }

    public void onTeamClick(View view, SearchViewModel viewModel, Integer index){
        viewModel.setTeamIndex(index);
    }
}
