package com.jk.soccer.etc;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.jk.soccer.BR;

import java.util.List;

public class MyRecyclerViewAdapter<T, BINDING extends ViewDataBinding>
        extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {
    protected List<T> list;
    protected Integer layout;

    public void setList(List<T> list){
        this.list = list;
        notifyDataSetChanged();
    }

    public void setLayoutId(Integer layout) {
        this.layout = layout;
    }

    public MyRecyclerViewAdapter(List<T> list, Integer layout){
        setList(list);
        setLayoutId(layout);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new MyViewHolder<BINDING>(inflater.inflate(layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int index){
        BINDING binding = (BINDING) (holder.getBinding());
        binding.setVariable(BR.item, list.get(index));
        binding.setVariable(BR.index, index);
        binding.setVariable(BR.handler, new MyHandler());
        binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        if (list == null)
            return 0;
        return list.size();
    }

    public static class MyViewHolder<BINDING extends ViewDataBinding>
            extends RecyclerView.ViewHolder {

        final private BINDING binding;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.binding = DataBindingUtil.bind(itemView);
        }

        public BINDING getBinding(){
            return binding;
        }
    }
}
