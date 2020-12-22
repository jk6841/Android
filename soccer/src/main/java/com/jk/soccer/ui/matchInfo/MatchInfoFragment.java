package com.jk.soccer.ui.matchInfo;

import android.app.Application;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.jk.soccer.R;
import com.jk.soccer.databinding.FragmentMatchBinding;

public class MatchInfoFragment extends Fragment {

    private Application application;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        application = getActivity().getApplication();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentMatchBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_match, container, false);
        View root = binding.getRoot();
        binding.setLifecycleOwner(this);

        return root;
    }
}
