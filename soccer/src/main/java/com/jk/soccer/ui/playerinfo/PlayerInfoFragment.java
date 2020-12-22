package com.jk.soccer.ui.playerinfo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.jk.soccer.R;
import com.jk.soccer.databinding.FragmentPlayerinfoBinding;
import com.jk.soccer.ui.MainActivity;
import com.jk.soccer.ui.MyViewModel;

public class PlayerInfoFragment extends Fragment {

    private Integer index;
    private MyViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        this.index = args.getInt("index", 0);
        viewModel = ((MainActivity) getActivity()).getViewModel();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentPlayerinfoBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_playerinfo, container, false);
        binding.setLifecycleOwner(this);
        binding.setViewModel(viewModel);
        binding.setIndex(index);
        return binding.getRoot();
    }
}
