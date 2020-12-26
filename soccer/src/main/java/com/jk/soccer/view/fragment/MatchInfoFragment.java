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
import com.jk.soccer.databinding.ViewholderEventBinding;
import com.jk.soccer.databinding.FragmentMatchinfoBinding;
import com.jk.soccer.databinding.ViewholderLineupBinding;
import com.jk.soccer.etc.Event;
import com.jk.soccer.view.activity.MainActivity;
import com.jk.soccer.etc.MyHandler;
import com.jk.soccer.etc.MyRecyclerViewAdapter;
import com.jk.soccer.viewModel.MyViewModel;
import com.jk.soccer.etc.Lineup;

import java.util.List;

public class MatchInfoFragment extends Fragment {

    private Integer index;
    MyViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null)
            index = args.getInt("index", 0);
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null)
            viewModel = mainActivity.getViewModel();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        FragmentMatchinfoBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_matchinfo, container, false);

        binding.setLifecycleOwner(this);
        binding.setViewModel(viewModel);
        binding.setHandler(new MyHandler());

        MyRecyclerViewAdapter<ViewholderLineupBinding> rvHomeAdapter
                = new MyRecyclerViewAdapter<>(viewModel, R.layout.viewholder_lineup, viewModel.countHomeLineup(index), index);
        binding.subLayoutLineup.lineupHome.setAdapter(rvHomeAdapter);

        MyRecyclerViewAdapter<ViewholderLineupBinding> rvAwayAdapter
                = new MyRecyclerViewAdapter<>(viewModel, R.layout.viewholder_lineup, viewModel.countAwayLineup(index), index);
        binding.subLayoutLineup.lineupAway.setAdapter(rvAwayAdapter);

        MyRecyclerViewAdapter<ViewholderEventBinding> rvEventAdapter
                = new MyRecyclerViewAdapter<>(viewModel, R.layout.viewholder_event, viewModel.countEvents(index), index);
        binding.subLayoutEvent.matchInfoEvent.setAdapter(rvEventAdapter);

        binding.subLayoutEvent.setEventList(viewModel.getEvents(index));
        binding.subLayoutLineup.setHomeLineup(viewModel.getHomeLineup(index));
        binding.subLayoutLineup.setAwayLineup(viewModel.getAwayLineup(index));
        binding.subLayoutMOM.setID(viewModel.getBestPlayerIDLiveData(index).getValue());
        binding.subLayoutMOM.setName(viewModel.getBestPlayerNameLiveData(index).getValue());
        binding.subLayoutMOM.setTeam(viewModel.getBestTeamLiveData(index).getValue());

        binding.setIndex(index);

        return binding.getRoot();
    }
}
