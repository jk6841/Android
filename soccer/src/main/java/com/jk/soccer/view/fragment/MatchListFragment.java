package com.jk.soccer.view.fragment;

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
import com.jk.soccer.databinding.ViewholderMatchBinding;
import com.jk.soccer.view.activity.MainActivity;
import com.jk.soccer.etc.MyRecyclerViewAdapter;
import com.jk.soccer.viewModel.MyViewModel;

public class MatchListFragment extends Fragment {

    private MyViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            viewModel = mainActivity.getViewModel();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        FragmentMatchlistBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_matchlist, container, false);
        //viewModel.initMatch();
        binding.setLifecycleOwner(this);
//        MyRecyclerViewAdapter<ViewholderMatchBinding> rvAdapter
//                = new MyRecyclerViewAdapter<>(viewModel, R.layout.viewholder_match, viewModel.countMatches());
//        binding.recMatch.setAdapter(rvAdapter);
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }
}