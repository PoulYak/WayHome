package com.example.wayhome.ui.authentication;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wayhome.R;
import com.example.wayhome.databinding.FragmentAuthenticationStep2Binding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Objects;
import java.util.concurrent.TimeUnit;



public class AuthenticationStep2Fragment extends Fragment {

    private FragmentAuthenticationStep2Binding binding;
    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAuthenticationStep2Binding.inflate(inflater, container, false);
        mAuth = FirebaseAuth.getInstance();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.buttonConfirm.setOnClickListener(v -> {
            String email = binding.mailInput.getText().toString().trim();
            String password = binding.passwordInput.getText().toString().trim();
            if (email.isEmpty() || password.isEmpty())
                Snackbar.make(requireView(), "Заполните поля", Snackbar.LENGTH_SHORT).setActionTextColor(getResources().getColor(R.color.red)).show();
            else{
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Snackbar.make(requireView(), "Успешный вход", Snackbar.LENGTH_SHORT).show();
                                Navigation.findNavController(requireView()).navigate(R.id.action_authenticationStep2Fragment2_to_appFragment);

                            } else {
                                // Login failed, handle accordingly
                                Snackbar.make(requireView(), "Неправильный пароль", Snackbar.LENGTH_SHORT).setActionTextColor(getResources().getColor(R.color.red)).show();
                            }
                        });
            }



        });


    }
}
