package com.jk.soccer.ui.matchInfo.event;

import androidx.annotation.NonNull;

import com.jk.soccer.databinding.EventViewholderBinding;
import com.jk.soccer.ui.MyRecyclerViewAdapter;

import java.util.List;

public class EventListAdapter extends MyRecyclerViewAdapter<Event, EventViewholderBinding>{
    public EventListAdapter(List<Event> list, Integer layout) {
        super(list, layout);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int index) {
        EventViewholderBinding binding = (EventViewholderBinding) holder.getBinding();
        binding.setEvent(list.get(index));
        binding.executePendingBindings();
    }
}