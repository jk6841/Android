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
        binding.setHandlers(new MyHandler());
        binding.subLayoutLineup.lineupHome.setAdapter(new MyRecyclerViewAdapter<Lineup, LineupViewholderBinding>(viewModel.getHomeLineupLiveData(index).getValue(), R.layout.lineup_viewholder));
        binding.subLayoutLineup.lineupAway.setAdapter(new MyRecyclerViewAdapter<Lineup, LineupViewholderBinding>(viewModel.getAwayLineupLiveData(index).getValue(), R.layout.lineup_viewholder));
        binding.subLayoutLineup.setViewModel(viewModel);
        binding.subLayoutEvent.setViewModel(viewModel);
        binding.subLayoutMOM.setViewModel(viewModel);
        binding.subLayoutEvent.matchInfoEvent.setAdapter(new MyRecyclerViewAdapter<Event, EventViewholderBinding>(viewModel.getEventListLiveData(index).getValue(), R.layout.event_viewholder));
        binding.subLayoutLineup.setIndex(index);
        binding.subLayoutEvent.setIndex(index);
        binding.subLayoutMOM.setIndex(index);
        binding.setIndex(index);
        return binding.getRoot();
    }
}
