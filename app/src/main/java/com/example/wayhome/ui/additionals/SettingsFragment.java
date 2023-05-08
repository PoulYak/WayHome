package com.example.wayhome.ui.additionals;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.wayhome.R;
import com.example.wayhome.data.room.AppDatabase;
import com.example.wayhome.data.room.Pet;
import com.example.wayhome.data.room.User;
import com.example.wayhome.databinding.FragmentSettingsBinding;
import com.example.wayhome.ui.profile.MyMy;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class SettingsFragment extends Fragment {
    FragmentSettingsBinding binding;
    Toolbar toolbar;
    boolean haveMenu = false;
    FirebaseAuth mAuth;
    private AppDatabase appDatabase;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        mAuth= FirebaseAuth.getInstance();
        Log.d("Firebase", Objects.requireNonNull(mAuth.getCurrentUser()).getPhoneNumber());
        appDatabase = AppDatabase.getInstance(requireContext());
        return binding.getRoot();
    }





    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (loadUserFromDatabase()==null)
            Snackbar.make(requireView(), "Добавляем юзера в базу", Snackbar.LENGTH_SHORT).show();

        NavController navController = Navigation.findNavController(requireView());
        AppBarConfiguration appBarConfiguration =
                new AppBarConfiguration.Builder(navController.getGraph()).build();
        toolbar = view.findViewById(R.id.toolbar);
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);
        toolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()){
                case (R.id.saveProfile):
                    toolbar.getMenu().clear();
                    haveMenu= false;
                    break;
            }
            return false;
        });
        binding.materialSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {if (isChecked) {
            activateCheck();
        } else {
            activateCheck();
        }
        });
        binding.edit2.setText(Objects.requireNonNull(mAuth.getCurrentUser()).getPhoneNumber());
        binding.edit2.setEnabled(false);
        binding.edit1.addTextChangedListener(tw);

        binding.edit3.addTextChangedListener(tw);


    }

    private User loadUserFromDatabase() {
            for (User u: appDatabase.userDao().getUserByPhone(Objects.requireNonNull(mAuth.getCurrentUser()).getPhoneNumber()))
                return u;
            return null;
    }


    public void activateCheck(){
            if (!haveMenu){
                toolbar.inflateMenu(R.menu.temp_menu);
                haveMenu=true;

            }
    }

    TextWatcher tw = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            activateCheck();
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };



}

//
//    NavHostFragment navHostFragment = (NavHostFragment) getChildFragmentManager().findFragmentById(R.id.fragmentContainerView);
//
//        if (navHostFragment != null) {
//                NavController navController = navHostFragment.getNavController();
//                BottomNavigationView navigationBar = binding.bottomNav;
//                AppBarConfiguration appBarConfiguration =
//                new AppBarConfiguration.Builder(navController.getGraph()).build();
//                Toolbar toolbar = view.findViewById(R.id.toolbar);
//                toolbar.inflateMenu(R.menu.toolbar_menu);
//