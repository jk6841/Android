package com.jk.soccer.ui.matchInfo.lineup;

import androidx.annotation.NonNull;

import com.jk.soccer.databinding.LineupViewholderBinding;
import com.jk.soccer.ui.MyRecyclerViewAdapter;

import java.util.List;

public class LineupAdapter extends MyRecyclerViewAdapter<Lineup, LineupViewholderBinding>{
    public LineupAdapter(List<Lineup> list, Integer layout) {
        super(list, layout);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int index) {
        LineupViewholderBinding binding = (LineupViewholderBinding)holder.getBinding();
        Lineup lineup = list.get(index);
        binding.setRole(lineup.getPosition());
        binding.setShirt(lineup.getShirt());
        binding.setName(lineup.getName());
        binding.setIndex(index);
        binding.executePendingBindings();
    }
}