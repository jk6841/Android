package com.jk.soccer.ui.playerlist;

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

import com.jk.soccer.R;
import com.jk.soccer.databinding.FragmentPlayerlistBinding;

public class PlayerListFragment extends Fragment {

    private PlayerListViewModel playerListViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Application application = getActivity().getApplication();
        playerListViewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(application))
                .get(PlayerListViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FragmentPlayerlistBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_playerlist, container, false);
        binding.setLifecycleOwner(this);
        binding.homeRec.setAdapter(new PlayerListAdapter(playerListViewModel.getPlayers()));
        playerListViewModel.getLivePlayers().observe(getViewLifecycleOwner(),
                players -> ((PlayerListAdapter)binding.homeRec.getAdapter()).setPlayerList(players));

        return binding.getRoot();
    }
}