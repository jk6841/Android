package com.jk.soccer.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.jk.soccer.R;

public class MyHandler {

    public void onPlayerListClick(View view, Integer index){
        NavController navController = Navigation.findNavController(view);
        Bundle args = new Bundle();
        args.putInt("index", index);
        navController.navigate(R.id.action_nav_home_to_nav_info, args);
    }

    public void onMatchListClick(View view, Integer index){
        Toast.makeText(view.getContext(), "click" , Toast.LENGTH_SHORT).show();
    }

}
