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
import com.jk.soccer.data.local.TablePlayer;
import com.jk.soccer.databinding.FragmentPlayerlistBinding;
import com.jk.soccer.databinding.PlayerViewholderBinding;
import com.jk.soccer.ui.MainActivity;
import com.jk.soccer.ui.MyRecyclerViewAdapter;
import com.jk.soccer.ui.MyViewModel;

public class PlayerListFragment extends Fragment {

    private MyViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ((MainActivity) getActivity()).getViewModel();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        FragmentPlayerlistBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_playerlist, container, false);
        binding.setLifecycleOwner(this);
        viewModel.initPlayer();
        MyRecyclerViewAdapter<TablePlayer, PlayerViewholderBinding> rvAdapter
                = new MyRecyclerViewAdapter<>(viewModel.getPlayers(), R.layout.player_viewholder);
        binding.homeRec.setAdapter(rvAdapter);
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }
}