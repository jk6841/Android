package com.jk.soccer.ui.playerList;

import androidx.annotation.NonNull;

import com.jk.soccer.data.local.TablePlayer;
import com.jk.soccer.databinding.PlayerViewholderBinding;
import com.jk.soccer.ui.MyHandler;
import com.jk.soccer.ui.MyRecyclerViewAdapter;

import java.util.List;

public class PlayerListAdapter extends MyRecyclerViewAdapter<TablePlayer, PlayerViewholderBinding> {
    public PlayerListAdapter(List<TablePlayer> list, Integer layout) {
        super(list, layout);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int index) {
        PlayerViewholderBinding binding = (PlayerViewholderBinding) holder.getBinding();
        binding.setPlayer(list.get(index));
        binding.setPos(index);
        binding.setHandlers(new MyHandler());
        binding.executePendingBindings();
    }
}
