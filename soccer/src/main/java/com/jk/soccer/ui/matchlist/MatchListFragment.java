package com.jk.soccer.ui.matchlist;

import android.app.Application;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.jk.soccer.R;
import com.jk.soccer.data.local.Match;
import com.jk.soccer.databinding.FragmentMatchlistBinding;
import com.jk.soccer.ui.match.MatchViewModel;

import java.util.List;

public class MatchListFragment extends Fragment {

    private MatchViewModel matchViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Application application = getActivity().getApplication();
        matchViewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(application))
                .get(MatchViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentMatchlistBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_matchlist, container, false);
        binding.setLifecycleOwner(this);
        binding.recMatch.setAdapter(new MatchListAdapter(matchViewModel.getMatches()));
        matchViewModel.getLiveMatches().observe(getViewLifecycleOwner(), new Observer<List<Match>>() {
            @Override
            public void onChanged(List<Match> matches) {
                ((MatchListAdapter)binding.recMatch.getAdapter()).setMatchList(matches);
            }
        });
        return binding.getRoot();
    }


}
