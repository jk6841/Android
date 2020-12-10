package com.jk.soccer.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.jk.soccer.R;
import com.jk.soccer.data.local.Player;
import com.jk.soccer.databinding.TextviewBinding;
import com.jk.soccer.viewmodel.HomeViewModel;

import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

    private HomeViewModel homeViewModel;

    private Integer[] colors;

    private static OnItemClickListener mListener = null;

    public void setPlayerList(List<Player> playerList) {
        this.playerList = playerList;
    }

    private List<Player> playerList;

    public HomeAdapter(HomeViewModel homeViewModel, Integer... colors) {
        this.homeViewModel = homeViewModel;
        colors = new Integer[colors.length];
        this.colors = colors;
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
        TextviewBinding binding = TextviewBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int pos) {
        if (holder instanceof MyViewHolder)
            holder.bind(homeViewModel, pos);
    }

    @Override
    public int getItemCount() {
        return playerList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextviewBinding binding;
        public MyViewHolder(TextviewBinding binding) {
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
