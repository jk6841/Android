package com.jk.soccer.ui.home;

import android.app.Application;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.jk.soccer.databinding.FragmentPlayerlistBinding;
import com.jk.soccer.R;
import com.jk.soccer.etc.MyHandler;

public class PlayerListFragment extends Fragment {

    private PlayerListViewModel playerListViewModel;
    private Application application;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        application = getActivity().getApplication();
        playerListViewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(application))
                .get(PlayerListViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        FragmentPlayerlistBinding binding =  DataBindingUtil.inflate(
                inflater, R.layout.fragment_playerlist, container, false);

        binding.setLifecycleOwner(this);
        binding.setViewModel(playerListViewModel);
        binding.setHandlers(new MyHandler());
        PlayerListAdapter playerListAdapter = new PlayerListAdapter(playerListViewModel);
        playerListViewModel.setColors(
                R.color.white,
                R.color.lightGreen,
                R.color.white,
                R.color.skyBlue);
        playerListViewModel.setAdapter(playerListAdapter);
        binding.homeRec.setAdapter(playerListAdapter);
        playerListViewModel.getLivePlayers().observe(getViewLifecycleOwner(), players -> {
            playerListAdapter.setPlayerList(players);
            playerListAdapter.notifyDataSetChanged();
        });

//        binding.button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                NavController navController = Navigation.findNavController(v);
//                Bundle args = new Bundle();
//                args.putInt("index", 3);
//                navController.navigate(R.id.action_nav_home_to_nav_info, args);
//            }
//        });

        return binding.getRoot();
    }
}