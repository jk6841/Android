package com.jk.soccer.view.fragment;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.jk.soccer.R;
import com.jk.soccer.databinding.FragmentSearchBinding;
import com.jk.soccer.etc.Handler;
import com.jk.soccer.etc.InfoHandler;
import com.jk.soccer.etc.enumeration.Type;
import com.jk.soccer.view.activity.MainActivity;
import com.jk.soccer.viewModel.SearchViewModel;

public class SearchFragment extends Fragment {

    private SearchViewModel viewModel;
    private Activity activity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Application application = activity.getApplication();
        viewModel = new ViewModelProvider(getActivity(),
                new ViewModelProvider.AndroidViewModelFactory(application))
                .get(SearchViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        FragmentSearchBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_search, container, false);
        binding.setLifecycleOwner(this);
        binding.setViewModel(viewModel);
        binding.setHandler(new InfoHandler(activity));
        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity){
            activity = (Activity) context;
        }
    }

    @Override
    public void onDestroy() {
        activity = null;
        super.onDestroy();
    }
}
