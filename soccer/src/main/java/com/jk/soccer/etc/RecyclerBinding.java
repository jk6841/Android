package com.jk.soccer.etc;

import android.content.Context;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerBinding {

    @BindingAdapter("recyclerAdapter")
    public static void recyclerAdapter(RecyclerView recyclerView,
                                       RecyclerView.Adapter<?> adapter){
        Context context = recyclerView.getContext();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

}
