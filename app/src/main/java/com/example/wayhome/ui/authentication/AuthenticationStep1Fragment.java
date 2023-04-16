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

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ru.tinkoff.decoro.MaskImpl;
import ru.tinkoff.decoro.parser.UnderscoreDigitSlotsParser;
import ru.tinkoff.decoro.slots.Slot;
import ru.tinkoff.decoro.watchers.FormatWatcher;
import ru.tinkoff.decoro.watchers.MaskFormatWatcher;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AuthenticationStep1Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AuthenticationStep1Fragment extends Fragment {

    FragmentAuthenticationStep1Binding mBinding;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AuthenticationStep1Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AuthenticationStep1Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AuthenticationStep1Fragment newInstance(String param1, String param2) {
        AuthenticationStep1Fragment fragment = new AuthenticationStep1Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentAuthenticationStep1Binding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addMask();
        setOnClickListener();
    }

    private boolean isBadNumber(String number) {
        if (TextUtils.isEmpty(number)) return true;
        Pattern pattern = Pattern.compile("[^0-9]*");

        Matcher matcher = pattern.matcher(number);
        if (matcher.replaceAll("").length() != 11)
            return true;
        return false;
    }

    private void addMask(){
        Slot[] slots = new UnderscoreDigitSlotsParser().parseSlots("+7 (___) ___-__-__");
        FormatWatcher formatWatcher = new MaskFormatWatcher( // форматировать текст будет вот он
                MaskImpl.createTerminated(slots)
        );
        formatWatcher.installOn(mBinding.textInput);
    }

    private void setOnClickListener(){
        mBinding.nextButton.setOnClickListener(v -> {

            String number_str = String.valueOf(mBinding.textInput.getText());

            if (isBadNumber(number_str)) {
                Toast.makeText(getActivity(), "Неправильно набран номер", Toast.LENGTH_SHORT).show();
            } else {
                // Переходим к отправке кода
                Bundle bundle = new Bundle();
                bundle.putString("myData", Objects.requireNonNull(mBinding.textInput.getText()).toString());
                Navigation.findNavController(v).navigate(R.id.action_authenticationStep1Fragment_to_authenticationStep2Fragment2, bundle);
            }
        });




    }

}