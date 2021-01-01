package com.jk.soccer.view.activity;

import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;

import com.google.android.material.navigation.NavigationView;
import com.jk.soccer.R;
import com.jk.soccer.viewModel.PlayerInfoViewModel;
import com.jk.soccer.viewModel.SearchViewModel;
import com.jk.soccer.viewModel.TeamInfoViewModel;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private MainViewModel mainVM;
    private SearchViewModel searchViewModel;
    private PlayerInfoViewModel playerInfoViewModel;
    private TeamInfoViewModel teamInfoViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NavigationView navigationView = findViewById(R.id.nav_view);
        NavController navController = Navigation.findNavController(
                this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navigationView, navController);
        mainVM = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication()))
                .get(MainViewModel.class);
        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        playerInfoViewModel = new ViewModelProvider(this).get(PlayerInfoViewModel.class);
        teamInfoViewModel = new ViewModelProvider(this).get(TeamInfoViewModel.class);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainVM.onCleared();
    }

    @Override
    public boolean onSupportNavigateUp() {
        return super.onSupportNavigateUp();
    }

    public SearchViewModel getSearchViewModel() {
        return searchViewModel;
    }

    public PlayerInfoViewModel getPlayerInfoViewModel() {
        return playerInfoViewModel;
    }

    public TeamInfoViewModel getTeamInfoViewModel() {
        return teamInfoViewModel;
    }

    public void hideKeyboard() {
        InputMethodManager manager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null)
            manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }
}