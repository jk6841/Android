package com.jk.soccer.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jk.soccer.data.local.Player;
import com.jk.soccer.databinding.ViewholderBinding;
import com.jk.soccer.viewmodel.HomeViewModel;

import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

    private HomeViewModel homeViewModel;

    private static OnItemClickListener mListener = null;

    public void setPlayerList(List<Player> playerList) {
        this.playerList = playerList;
    }

    private List<Player> playerList;

    public HomeAdapter(HomeViewModel homeViewModel) {
        this.homeViewModel = homeViewModel;
        playerList = new ArrayList<>();
    }

    public interface OnItemClickListener{
        void onItemClick(View v, int pos);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener = listener;
    }

    @NonNull
    @Override
    public HomeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                       int viewType) {
        ViewholderBinding binding = ViewholderBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int pos) {
        if (holder instanceof MyViewHolder) {
            holder.bind(homeViewModel, pos);
        }
    }

    @Override
    public int getItemCount() {
        return playerList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private ViewholderBinding binding;
        public MyViewHolder(ViewholderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bind(HomeViewModel homeViewModel, int pos){
            binding.setViewModel(homeViewModel);
            binding.setPos(pos);
            binding.executePendingBindings();
        }
    }
}
