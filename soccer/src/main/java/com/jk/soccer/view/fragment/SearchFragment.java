package com.jk.soccer.view.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.jk.soccer.R;
import com.jk.soccer.databinding.FragmentSearchBinding;
import com.jk.soccer.etc.Handler;
import com.jk.soccer.etc.enumeration.Type;
import com.jk.soccer.view.activity.MainActivity;
import com.jk.soccer.viewModel.SearchViewModel;

public class SearchFragment extends Fragment {

    private SearchViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ((MainActivity) getActivity()).getSearchViewModel();
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
        binding.setHandler(new InfoHandler((MainActivity)getActivity()));
        return binding.getRoot();
    }

    private static class InfoHandler implements Handler {

        final private MainActivity activity;;

        public InfoHandler(MainActivity activity) {
            this.activity = activity;
        }

        @Override
        public void onClick(View v, Object... params) {
            NavController navController = Navigation.findNavController(v);
            Bundle args = new Bundle();
            Integer ID = (Integer)params[0];
            Integer parentID = (Integer)params[1];
            String name = (String)params[2];
            Type type = (Type)params[3];
            Integer nav = null;
            args.putInt("id", ID);
            args.putInt("parent", parentID);
            args.putString("name", name);
            switch (type){
                case PERSON:
                    nav = R.id.action_nav_search_to_nav_playerInfo;
                    break;
                case TEAM:
                    nav = R.id.action_nav_search_to_nav_teamInfo;
                    break;
                case LEAGUE:
                    nav = R.id.action_nav_search_to_nav_leagueInfo;
                default:
                    break;
            }
            if (nav != null) {
                (activity).hideKeyboard();
                navController.navigate(nav, args);
            }
        }
    }

}
