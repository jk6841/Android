package com.jk.soccer.ui.matchInfo.lineup;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jk.soccer.databinding.LineupViewholderBinding;

import java.util.List;

public class LineupAdapter extends RecyclerView.Adapter<LineupAdapter.MyViewHolder>{

    private List<Lineup> lineupList;

    public LineupAdapter(List<Lineup> lineupList){
        this.lineupList = lineupList;
    }

    public void setLineup(List<Lineup> lineupList){
        this.lineupList = lineupList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LineupAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LineupViewholderBinding binding = LineupViewholderBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new LineupAdapter.MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull LineupAdapter.MyViewHolder holder, int index) {
        Lineup lineup = lineupList.get(index);
        holder.binding.setRole(lineup.getPosition());
        holder.binding.setShirt(lineup.getShirt());
        holder.binding.setName(lineup.getName());
        holder.binding.setIndex(index);
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        if (lineupList == null)
            return 0;
        return lineupList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private LineupViewholderBinding binding;
        public MyViewHolder(LineupViewholderBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
