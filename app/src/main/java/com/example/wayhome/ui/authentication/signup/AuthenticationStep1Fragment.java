package com.example.wayhome.ui.authentication.signup;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wayhome.R;
import com.example.wayhome.databinding.FragmentAuthenticationStep1Binding;
import com.example.wayhome.ui.additionals.feedback.FeedbackViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

public class AuthenticationStep1Fragment extends Fragment {
    FragmentAuthenticationStep1Binding binding;
    SignupViewModel viewModel;

    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
                binding = FragmentAuthenticationStep1Binding.inflate(inflater, container, false);
        mAuth = FirebaseAuth.getInstance();
        viewModel = new ViewModelProvider(this).get(SignupViewModel.class);
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.getLoginText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String newData) {
                binding.mailInput.setText(newData);
            }
        });
        viewModel.getPasswordText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String newData) {
                binding.passwordInput.setText(newData);
            }
        });



        binding.nextButton.setOnClickListener(v -> {

            String email = binding.mailInput.getText().toString().trim();
            String password = binding.passwordInput.getText().toString().trim();
            if (email.isEmpty() || password.isEmpty())
                Snackbar.make(requireView(), "Заполните поля", Snackbar.LENGTH_SHORT).setActionTextColor(getResources().getColor(R.color.red)).show();
            else{

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                // Registration success, handle accordingly
                                Snackbar.make(requireView(), "Успешная регистрация", Snackbar.LENGTH_SHORT).show();
                                Navigation.findNavController(requireView()).navigate(R.id.action_authenticationStep1Fragment_to_appFragment);
                            } else {
                                Snackbar.make(requireView(), "Не получилось зарегистрироваться(", Snackbar.LENGTH_SHORT).setActionTextColor(getResources().getColor(R.color.red)).show();
                            }
                        });
            }


        });
    }

}