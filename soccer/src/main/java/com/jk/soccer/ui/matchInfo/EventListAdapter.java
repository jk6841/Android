package com.jk.soccer.ui.matchInfo;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jk.soccer.databinding.EventViewholderBinding;

import java.util.List;

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.MyViewHolder>{

    private List<Event> eventList;

    public EventListAdapter(List<Event> eventList) {
        this.eventList = eventList;
    }

    public void setEventList(List<Event> eventList){
        this.eventList = eventList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public EventListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        EventViewholderBinding binding = EventViewholderBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new EventListAdapter.MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull EventListAdapter.MyViewHolder holder, int position) {
        holder.binding.setEvent(eventList.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private EventViewholderBinding binding;
        public MyViewHolder(EventViewholderBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
