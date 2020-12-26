package com.jk.soccer.etc;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.recyclerview.widget.RecyclerView;

import com.jk.soccer.BR;
import com.jk.soccer.viewModel.MyViewModel;

import java.util.List;

public class MyRecyclerViewAdapter<BINDING extends ViewDataBinding>
        extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder<BINDING>>{

    protected Integer layout;
    protected MyViewModel viewModel;
    protected Integer listIndex = -1;
    protected Integer count;

    public MyRecyclerViewAdapter(MyViewModel viewModel, Integer layout, Integer count, Integer... index){
        this.viewModel = viewModel;
        this.layout = layout;
        this.count = count;
    }

    public MyRecyclerViewAdapter(MyViewModel viewModel, Integer layout, Integer count, Integer listIndex){
        this.viewModel = viewModel;
        this.layout = layout;
        this.count = count;
        this.listIndex = listIndex;
    }

    @NonNull
    @Override
    public MyViewHolder<BINDING> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        MyViewHolder<BINDING> holder = new MyViewHolder<>(inflater.inflate(layout, parent, false));
        holder.lifecycleCreate();
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int index){
        holder.bind(viewModel, index, listIndex);
    }

    @Override
    public int getItemCount() {
        return count;
    }

    @Override
    public void onViewAttachedToWindow(@NonNull MyViewHolder<BINDING> holder) {
        super.onViewAttachedToWindow(holder);
        holder.attachToWindow();
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull MyViewHolder<BINDING> holder) {
        holder.detachFromWindow();
        super.onViewDetachedFromWindow(holder);
    }

    public static class MyViewHolder<BINDING extends ViewDataBinding>
            extends RecyclerView.ViewHolder implements LifecycleOwner {

        final private BINDING binding;
        final private LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.binding = DataBindingUtil.bind(itemView);
            binding.setLifecycleOwner(this);
            lifecycleRegistry.setCurrentState(Lifecycle.State.INITIALIZED);
        }

        public void bind(MyViewModel viewModel, Integer ... indices){
            binding.setVariable(BR.viewModel, viewModel);
            binding.setVariable(BR.index, indices);
            binding.setVariable(BR.handler, new MyHandler());
            binding.executePendingBindings();
        }

        public void lifecycleCreate(){
            lifecycleRegistry.setCurrentState(Lifecycle.State.CREATED);
        }

        @NonNull
        @Override
        public Lifecycle getLifecycle() {
            return lifecycleRegistry;
        }

        public void attachToWindow(){
            lifecycleRegistry.setCurrentState(Lifecycle.State.RESUMED);
        }

        public void detachFromWindow(){
            lifecycleRegistry.setCurrentState(Lifecycle.State.STARTED);
        }

    }
}
