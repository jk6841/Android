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
import com.jk.soccer.view.activity.MainActivity;
import com.jk.soccer.viewModel.PlayerInfoViewModel;

public class PlayerInfoFragment extends Fragment {

    private Integer ID;
    private Integer parentID;
    private String name;
    private PlayerInfoViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            ID = args.getInt("id", 0);
            parentID = args.getInt("parent", 0);
            name = args.getString("name", "");
        }
        viewModel = ((MainActivity) getActivity()).getPlayerInfoViewModel();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewModel.getPlayerInfo(ID);
        FragmentPlayerinfoBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_playerinfo, container, false);
        binding.setLifecycleOwner(this);
        binding.setViewModel(viewModel);
        binding.setID(ID);
        binding.setName(name);
        binding.setParentID(parentID);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        viewModel.init();
        super.onDestroyView();
    }
}
