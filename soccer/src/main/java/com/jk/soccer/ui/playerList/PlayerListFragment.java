package com.jk.soccer.ui.playerList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.jk.soccer.R;
import com.jk.soccer.databinding.FragmentPlayerlistBinding;
import com.jk.soccer.ui.MainActivity;
import com.jk.soccer.ui.MyViewModel;

public class PlayerListFragment extends Fragment {

    private MyViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ((MainActivity) getActivity()).getViewModel();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FragmentPlayerlistBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_playerlist, container, false);
        binding.setLifecycleOwner(this);
        viewModel.initPlayer();
        binding.homeRec.setAdapter(new PlayerListAdapter(viewModel.getPlayers(), R.layout.player_viewholder));
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }
}