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

    private static class InfoHandler implements Handler {

        final private Activity activity;;

        public InfoHandler(Activity activity) {
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
                ((MainActivity) activity).hideKeyboard();
                navController.navigate(nav, args);
            }
        }
    }

}
