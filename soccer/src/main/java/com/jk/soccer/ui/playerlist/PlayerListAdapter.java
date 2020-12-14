package com.jk.soccer.ui.playerlist;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jk.soccer.data.local.Player;
import com.jk.soccer.databinding.PlayerViewholderBinding;
import com.jk.soccer.etc.MyHandler;

import java.util.List;


public class PlayerListAdapter extends RecyclerView.Adapter<PlayerListAdapter.MyViewHolder> {

    private List<Player> playerList;

    public PlayerListAdapter(List<Player> playerList) {
        this.playerList = playerList;
    }

    public void setPlayerList(List<Player> playerList){
        this.playerList = playerList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PlayerListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                             int viewType) {
        PlayerViewholderBinding binding = PlayerViewholderBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int pos) {
        holder.binding.setPlayer(playerList.get(pos));
        holder.binding.setPos(pos);
        holder.binding.setHandlers(new MyHandler());
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return playerList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private PlayerViewholderBinding binding;
        public MyViewHolder(PlayerViewholderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
