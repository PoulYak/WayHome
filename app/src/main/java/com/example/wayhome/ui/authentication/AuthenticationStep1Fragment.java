package com.example.wayhome.ui.authentication;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.wayhome.R;
import com.example.wayhome.databinding.FragmentAuthenticationStep1Binding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ru.tinkoff.decoro.MaskImpl;
import ru.tinkoff.decoro.parser.UnderscoreDigitSlotsParser;
import ru.tinkoff.decoro.slots.Slot;
import ru.tinkoff.decoro.watchers.FormatWatcher;
import ru.tinkoff.decoro.watchers.MaskFormatWatcher;


public class AuthenticationStep1Fragment extends Fragment {
    FragmentAuthenticationStep1Binding mBinding;


//    public AuthenticationStep1Fragment() {
//        // Required empty public constructor
//    }
//
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        mBinding = FragmentAuthenticationStep1Binding.inflate(inflater, container, false);
//        return mBinding.getRoot();
//    }
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        addMask();
//        setOnClickListener();
//    }
//
//    private boolean isBadNumber(String number) {
//        if (TextUtils.isEmpty(number)) return true;
//        Pattern pattern = Pattern.compile("[^0-9]*");
//
//        Matcher matcher = pattern.matcher(number);
//        if (matcher.replaceAll("").length() != 11)
//            return true;
//        return false;
//    }
//
//    private void addMask(){
//        Slot[] slots = new UnderscoreDigitSlotsParser().parseSlots("+7 (___) ___-__-__");
//        FormatWatcher formatWatcher = new MaskFormatWatcher( // форматировать текст будет вот он
//                MaskImpl.createTerminated(slots)
//        );
//        formatWatcher.installOn(mBinding.textInput);
//    }
//
//    private void setOnClickListener(){
//        mBinding.nextButton.setOnClickListener(v -> {
//
//            String number_str = String.valueOf(mBinding.textInput.getText());
//
//            if (isBadNumber(number_str)) {
//                Toast.makeText(getActivity(), "Неправильно набран номер", Toast.LENGTH_SHORT).show();
//            } else {
//                // Переходим к отправке кода
//                Bundle bundle = new Bundle();
//                bundle.putString("myData", Objects.requireNonNull(mBinding.textInput.getText()).toString());
//                Navigation.findNavController(v).navigate(R.id.action_authenticationStep1Fragment_to_authenticationStep2Fragment2, bundle);
//            }
//        });
//
//
//
//
//    }

    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
                mBinding = FragmentAuthenticationStep1Binding.inflate(inflater, container, false);

        mAuth = FirebaseAuth.getInstance();



        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBinding.nextButton.setOnClickListener(v -> {

            String email = mBinding.mailInput.getText().toString().trim();
            String password = mBinding.passwordInput.getText().toString().trim();
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
                                Snackbar.make(requireView(), "Почта занята", Snackbar.LENGTH_SHORT).setActionTextColor(getResources().getColor(R.color.red)).show();
                            }
                        });
            }


        });
    }

}