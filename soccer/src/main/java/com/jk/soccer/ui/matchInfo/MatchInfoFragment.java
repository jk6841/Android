package com.jk.soccer.ui.matchInfo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.jk.soccer.R;
import com.jk.soccer.databinding.EventViewholderBinding;
import com.jk.soccer.databinding.FragmentMatchinfoBinding;
import com.jk.soccer.databinding.LineupViewholderBinding;
import com.jk.soccer.ui.MainActivity;
import com.jk.soccer.ui.MyHandler;
import com.jk.soccer.ui.MyRecyclerViewAdapter;
import com.jk.soccer.ui.MyViewModel;
import com.jk.soccer.ui.matchInfo.event.Event;
import com.jk.soccer.ui.matchInfo.lineup.Lineup;

import java.util.List;

public class MatchInfoFragment extends Fragment {

    private Integer index;
    MyViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        index = args.getInt("index", 0);
        viewModel = ((MainActivity) getActivity()).getViewModel();
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

        List<Lineup> homeLineup = viewModel.getHomeLineupLiveData(index).getValue();
        MyRecyclerViewAdapter<Lineup, LineupViewholderBinding> rvHomeAdapter
                = new MyRecyclerViewAdapter<>(homeLineup, R.layout.lineup_viewholder);
        binding.subLayoutLineup.lineupHome.setAdapter(rvHomeAdapter);

        List<Lineup> awayLineup = viewModel.getAwayLineupLiveData(index).getValue();
        MyRecyclerViewAdapter<Lineup, LineupViewholderBinding> rvAwayAdapter
                = new MyRecyclerViewAdapter<>(awayLineup, R.layout.lineup_viewholder);
        binding.subLayoutLineup.lineupAway.setAdapter(rvAwayAdapter);

        List<Event> eventList = viewModel.getEventListLiveData(index).getValue();
        MyRecyclerViewAdapter<Event, EventViewholderBinding> rvEventAdapter
                = new MyRecyclerViewAdapter<>(eventList, R.layout.event_viewholder);
        binding.subLayoutEvent.matchInfoEvent.setAdapter(rvEventAdapter);


        binding.subLayoutLineup.setViewModel(viewModel);
        binding.subLayoutEvent.setViewModel(viewModel);
        binding.subLayoutMOM.setViewModel(viewModel);

        binding.subLayoutLineup.setIndex(index);
        binding.subLayoutEvent.setIndex(index);
        binding.subLayoutMOM.setIndex(index);

        binding.setIndex(index);

        return binding.getRoot();
    }
}
