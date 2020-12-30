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
import com.jk.soccer.databinding.FragmentMatchinfoBinding;

import com.jk.soccer.view.activity.MainActivity;
import com.jk.soccer.etc.MyHandler;


public class MatchInfoFragment extends Fragment {

    private Integer index;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null)
            index = args.getInt("index", 0);
        MainActivity mainActivity = (MainActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        FragmentMatchinfoBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_matchinfo, container, false);

        binding.setLifecycleOwner(this);
        binding.setHandler(new MyHandler());

//        MyRecyclerViewAdapter<ViewholderEventBinding> rvEventAdapter
//                = new MyRecyclerViewAdapter<>(viewModel, R.layout.viewholder_event, viewModel.countEvents(index), index);
//        binding.subLayoutEvent.matchInfoEvent.setAdapter(rvEventAdapter);

        binding.setIndex(index);

        return binding.getRoot();
    }
}
