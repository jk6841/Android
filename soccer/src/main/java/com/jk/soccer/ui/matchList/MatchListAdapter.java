package com.jk.soccer.ui.matchList;

import androidx.annotation.NonNull;

import com.jk.soccer.data.local.TableMatch;
import com.jk.soccer.databinding.MatchViewholderBinding;
import com.jk.soccer.ui.MyHandler;
import com.jk.soccer.ui.MyRecyclerViewAdapter;

import java.util.List;

public class MatchListAdapter extends MyRecyclerViewAdapter<TableMatch, MatchViewholderBinding>{
    public MatchListAdapter(List<TableMatch> list, Integer layout) {
        super(list, layout);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int index) {
        MatchViewholderBinding binding = (MatchViewholderBinding) holder.getBinding();
        binding.setMatch(list.get(index));
        binding.setPos(index);
        binding.setHandlers(new MyHandler());
        binding.executePendingBindings();
    }
}