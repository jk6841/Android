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
import com.jk.soccer.view.activity.MainActivity;
import com.jk.soccer.etc.MyHandler;
import com.jk.soccer.etc.MyRecyclerViewAdapter;
import com.jk.soccer.viewmodel.MyViewModel;
import com.jk.soccer.etc.Event;
import com.jk.soccer.etc.Lineup;

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
        MyRecyclerViewAdapter<ViewholderLineupBinding> rvHomeAdapter
                = new MyRecyclerViewAdapter<>(homeLineup, R.layout.viewholder_lineup);
        binding.subLayoutLineup.lineupHome.setAdapter(rvHomeAdapter);

        List<Lineup> awayLineup = viewModel.getAwayLineupLiveData(index).getValue();
        MyRecyclerViewAdapter<ViewholderLineupBinding> rvAwayAdapter
                = new MyRecyclerViewAdapter<>(awayLineup, R.layout.viewholder_lineup);
        binding.subLayoutLineup.lineupAway.setAdapter(rvAwayAdapter);

        List<Event> eventList = viewModel.getEventListLiveData(index).getValue();
        MyRecyclerViewAdapter<ViewholderEventBinding> rvEventAdapter
                = new MyRecyclerViewAdapter<>(eventList, R.layout.viewholder_event);
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
