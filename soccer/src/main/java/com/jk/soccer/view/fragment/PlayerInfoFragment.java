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
import com.jk.soccer.databinding.FragmentPlayerinfoBinding;
import com.jk.soccer.viewModel.PlayerInfoViewModel;

public class PlayerInfoFragment extends Fragment {

    private Integer ID;
    private PlayerInfoViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null)
            ID = args.getInt("id", 0);
        Application application = getActivity().getApplication();
        viewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(application))
                .get(PlayerInfoViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        FragmentPlayerinfoBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_playerinfo, container, false);
        binding.setLifecycleOwner(this);
        binding.setViewModel(viewModel);
        binding.setID(ID);
        viewModel.getPlayerInfo(ID);

        return binding.getRoot();
    }
}
