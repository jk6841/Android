package com.jk.soccer.ui.matchList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.jk.soccer.R;
import com.jk.soccer.databinding.FragmentMatchlistBinding;
import com.jk.soccer.ui.MainActivity;
import com.jk.soccer.ui.MyViewModel;

public class MatchListFragment extends Fragment {

    private MyViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ((MainActivity) getActivity()).getViewModel();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentMatchlistBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_matchlist, container, false);
        binding.setLifecycleOwner(this);
        viewModel.initMatch();
        binding.recMatch.setAdapter(new MatchListAdapter(viewModel.getMatches()));
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }
}
