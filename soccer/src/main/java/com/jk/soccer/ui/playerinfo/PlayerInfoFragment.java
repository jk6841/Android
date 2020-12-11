package com.jk.soccer.ui.playerinfo;

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
import com.jk.soccer.databinding.FragmentPlayerinfoBinding;

public class PlayerInfoFragment extends Fragment {

    private Integer index;
    private PlayerInfoViewModel playerInfoViewModel;
    private Application application;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        application = getActivity().getApplication();
        Bundle args = getArguments();
        this.index = args.getInt("index", 0);
        playerInfoViewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(application))
                .get(PlayerInfoViewModel.class);
        playerInfoViewModel.init(index);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentPlayerinfoBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_playerinfo, container, false);
        binding.setLifecycleOwner(this);
        binding.setViewModel(playerInfoViewModel);
        return binding.getRoot();
    }
}
