package com.jk.soccer.view.fragment;

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
import com.jk.soccer.databinding.FragmentLeagueinfoBinding;
import com.jk.soccer.databinding.FragmentPlayerinfoBinding;
import com.jk.soccer.viewModel.LeagueInfoViewModel;
import com.jk.soccer.viewModel.PlayerInfoViewModel;

public class LeagueInfoFragment extends Fragment {

    private Integer ID;
    private LeagueInfoViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null)
            ID = args.getInt("id", 0);
        Application application = getActivity().getApplication();
        viewModel = new ViewModelProvider(getActivity(),
                new ViewModelProvider.AndroidViewModelFactory(application))
                .get(LeagueInfoViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        FragmentLeagueinfoBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_leagueinfo, container, false);
        binding.setLifecycleOwner(this);
        binding.setViewModel(viewModel);
        binding.setID(ID);
        viewModel.getLeagueInfoAsync(ID);
        return binding.getRoot();
    }
}
