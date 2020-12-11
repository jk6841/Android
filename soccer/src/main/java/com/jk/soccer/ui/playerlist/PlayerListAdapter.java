package com.jk.soccer.ui.playerlist;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jk.soccer.data.local.Player;
import com.jk.soccer.databinding.PlayerViewholderBinding;
import com.jk.soccer.etc.MyHandler;

import java.util.ArrayList;
import java.util.List;

public class PlayerListAdapter extends RecyclerView.Adapter<PlayerListAdapter.MyViewHolder> {

    private PlayerListViewModel playerListViewModel;
    PlayerViewholderBinding binding;

    public void setPlayerList(List<Player> playerList) {
        this.playerList = playerList;
    }

    private List<Player> playerList;

    public PlayerListAdapter(PlayerListViewModel playerListViewModel) {
        this.playerListViewModel = playerListViewModel;
        playerList = new ArrayList<>();
    }


    @NonNull
    @Override
    public PlayerListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                             int viewType) {
        binding = PlayerViewholderBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int pos) {
        holder.bind(playerListViewModel, pos);
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
        public void bind(PlayerListViewModel playerListViewModel, int pos){
            binding.setViewModel(playerListViewModel);
            binding.setPos(pos);
            binding.setHandlers(new MyHandler());
            binding.executePendingBindings();
        }
    }
}
