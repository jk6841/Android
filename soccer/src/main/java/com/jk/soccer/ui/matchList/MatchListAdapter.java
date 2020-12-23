package com.jk.soccer.ui.matchList;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jk.soccer.data.local.TableMatch;
import com.jk.soccer.databinding.MatchViewholderBinding;
import com.jk.soccer.ui.MyHandler;

import java.util.List;

public class MatchListAdapter extends RecyclerView.Adapter<MatchListAdapter.MyViewHolder> {

    private List<TableMatch> matchList;

    public MatchListAdapter(List<TableMatch> matchList) {
        this.matchList = matchList;
    }

    public void setMatchList(List<TableMatch> matchList){
        this.matchList = matchList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MatchListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                             int viewType) {
        MatchViewholderBinding binding = MatchViewholderBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new MatchListAdapter.MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MatchListAdapter.MyViewHolder holder, int pos) {
        holder.binding.setMatch(matchList.get(pos));
        holder.binding.setPos(pos);
        holder.binding.setHandlers(new MyHandler());
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return matchList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private MatchViewholderBinding binding;
        public MyViewHolder(MatchViewholderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
