package com.jk.soccer.view.fragment;

import android.app.Application;
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
import com.jk.soccer.viewModel.SearchViewModel;

public class SearchFragment extends Fragment {

    private SearchViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Application application = getActivity().getApplication();
        viewModel = new ViewModelProvider(this,
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
        binding.setHandler(new PlayerInfoHandler());
        return binding.getRoot();
    }

    public static class PlayerInfoHandler implements Handler{

        @Override
        public void onClick(View v, Integer... params) {
            NavController navController = Navigation.findNavController(v);
            Bundle args = new Bundle();
            args.putInt("id", params[0]);
            navController.navigate(R.id.action_nav_search_to_nav_playerInfo, args);
        }
    }
}
