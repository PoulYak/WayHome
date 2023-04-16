package com.example.wayhome.ui.main;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.wayhome.R;
import com.example.wayhome.databinding.FragmentMainBinding;

public class MainFragment extends Fragment {
    private FragmentMainBinding mBinding;
    private MainViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = FragmentMainBinding.inflate(inflater, container, false);
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mBinding.setViewModel(mViewModel);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel.setText("Hello, world!");
        mBinding.setLifecycleOwner(getViewLifecycleOwner());
        mBinding.loginButton.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_mainFragment_to_authenticationStep1Fragment);
        });
        mBinding.signupButton.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_mainFragment_to_authenticationStep1Fragment);
        });

    }
}