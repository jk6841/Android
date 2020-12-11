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

import com.jk.soccer.databinding.FragmentHomeBinding;
import com.jk.soccer.R;
import com.jk.soccer.viewmodel.HomeViewModel;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private Application application;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        application = getActivity().getApplication();
        homeViewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(application))
                .get(HomeViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        FragmentHomeBinding binding =  DataBindingUtil.inflate(
                inflater, R.layout.fragment_home, container, false);

        binding.setLifecycleOwner(this);
        binding.setViewModel(homeViewModel);
        HomeAdapter homeAdapter = new HomeAdapter(homeViewModel);
        homeViewModel.setColors(
                R.color.lightgreen,
                R.color.white,
                R.color.black,
                R.color.teal_200);
        homeViewModel.setAdapter(homeAdapter);
        binding.homeRec.setAdapter(homeAdapter);
        homeViewModel.getLivePlayers().observe(getViewLifecycleOwner(), players -> {
            homeAdapter.setPlayerList(players);
            homeAdapter.notifyDataSetChanged();
        });

        return binding.getRoot();
    }
}