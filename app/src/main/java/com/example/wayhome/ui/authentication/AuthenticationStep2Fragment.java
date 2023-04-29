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
import android.widget.Toast;

import com.example.wayhome.R;
import com.example.wayhome.databinding.FragmentAuthenticationStep2Binding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;



public class AuthenticationStep2Fragment extends Fragment {

    private FragmentAuthenticationStep2Binding binding;
    String verificationId;
    FirebaseAuth mAuth;
    String number;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment using ViewBinding
        number = getArguments().getString("myData");
        binding = FragmentAuthenticationStep2Binding.inflate(inflater, container, false);
//        binding.numberView.setText(getText(R.string.layout_1_3_phone_text)+" "+phoneNumberStr);
        mAuth = FirebaseAuth.getInstance();
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.numberView.setText(getString(R.string.layout_1_3_phone_text) + " " + number);
        sendVerificationCode(number);


        binding.buttonConfirm.setOnClickListener(v -> {
            String number_str = String.valueOf(binding.codeInput.getText());

            if (TextUtils.isEmpty(number_str)) {
                Toast.makeText(getActivity(), "Wrong OTP entered", Toast.LENGTH_SHORT).show();
            }
            else{

                verifyCode(number_str);
            }
        });
    }
    //////////////////////////WORKED

    private void sendVerificationCode(String number_str){
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(number_str)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(getActivity())                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);


    }


    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential credential)
        {
            final String code = credential.getSmsCode();
            if (code!=null){
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(getActivity(), "auth failed", Toast.LENGTH_SHORT).show();
            Log.e("AUTH", e.toString());
        }

        @Override
        public void onCodeSent(@NonNull String s,
                               @NonNull PhoneAuthProvider.ForceResendingToken token) {
            super.onCodeSent(s, token);
            verificationId = s;
        }
    };

    private void verifyCode(String Code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, Code);
        signinbyCredentials(credential);
    }

    private void signinbyCredentials(PhoneAuthCredential credential) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(getActivity(), "Login Successful", Toast.LENGTH_SHORT).show();
                    Navigation.findNavController(requireView()).navigate(R.id.action_authenticationStep2Fragment2_to_appFragment);


                }
            }
        });
    }
}
