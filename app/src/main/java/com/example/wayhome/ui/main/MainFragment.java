package com.example.wayhome.ui.main;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.wayhome.R;
import com.example.wayhome.databinding.FragmentMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class MainFragment extends Fragment {
    private FragmentMainBinding mBinding;
    private MainViewModel mViewModel;
    FirebaseUser currentUser;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        mBinding = FragmentMainBinding.inflate(inflater, container, false);
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mBinding.setViewModel(mViewModel);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (currentUser!=null){
            Toast.makeText(getActivity(), "Login Successful", Toast.LENGTH_SHORT).show();
            Navigation.findNavController(requireView()).navigate(R.id.action_mainFragment_to_appFragment);
        }
        mViewModel.setText("Hello, world!");
        mBinding.setLifecycleOwner(getViewLifecycleOwner());
        mBinding.loginButton.setOnClickListener(v -> {
//            Navigation.findNavController(v).navigate(R.id.action_mainFragment_to_authenticationStep1Fragment);
            Navigation.findNavController(v).navigate(R.id.action_mainFragment_to_appFragment);
//            Navigation.findNavController(v).navigate(R.id.action_mainFragment_to_formFragment);
        });
        mBinding.signupButton.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_mainFragment_to_authenticationStep1Fragment);
//            Navigation.findNavController(v).navigate(R.id.action_mainFragment_to_appFragment);
        });

    }

}