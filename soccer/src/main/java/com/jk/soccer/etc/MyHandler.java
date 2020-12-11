package com.jk.soccer.etc;

import android.os.Bundle;
import android.view.View;
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
}
