package com.jk.soccer.etc;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.jk.soccer.BR;
import com.jk.soccer.viewModel.SearchViewModel;

import java.util.List;

public class NewRecyclerViewAdapter <BINDING extends ViewDataBinding>
        extends RecyclerView.Adapter<NewRecyclerViewAdapter.NewViewHolder<BINDING>>{

    public NewRecyclerViewAdapter(SearchViewModel.Handler handler, Integer layout) {
        this.handler = handler;
        this.layout = layout;
    }

    public void setList(List<?> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NewViewHolder<BINDING> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new NewViewHolder<>(inflater.inflate(layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NewViewHolder<BINDING> holder, int index){
        holder.bind(list, index, handler);
    }

    @Override
    public int getItemCount(){
        if (list == null)
            return 0;
        return list.size();
    }

    public static class NewViewHolder<BINDING extends ViewDataBinding>
            extends RecyclerView.ViewHolder{
        final private BINDING binding;

        public NewViewHolder(@NonNull View itemView) {
            super(itemView);
            this.binding = DataBindingUtil.bind(itemView);
        }

        public void bind(List<?> list, Integer index, SearchViewModel.Handler handler){
            binding.setVariable(BR.item, list.get(index));
            binding.setVariable(BR.index, index);
            binding.setVariable(BR.handler, handler);
        }
    }

    private List<?> list;
    final private SearchViewModel.Handler handler;
    final private Integer layout;
}
