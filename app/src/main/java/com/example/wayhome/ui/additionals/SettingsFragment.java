package com.example.wayhome.ui.additionals;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wayhome.R;
import com.example.wayhome.databinding.FragmentSettingsBinding;
import com.example.wayhome.ui.Person;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class SettingsFragment extends Fragment {
    FragmentSettingsBinding binding;
    Toolbar toolbar;
    boolean haveMenu = false;
    FirebaseAuth mAuth;
    DatabaseReference usersRef;
    Person person;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        mAuth= FirebaseAuth.getInstance();
        usersRef = FirebaseDatabase.getInstance().getReference("Users");

        return binding.getRoot();
    }
    private void setUpViews(String phone_number){
        usersRef.child(phone_number).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    person = dataSnapshot.getValue(Person.class);
                    binding.edit1.setText(person.getName());
                    binding.edit2.setText(person.getPhone());
                    binding.edit3.setText(person.getEmail());
                    if (person.getIs_toggle()==1)
                        binding.materialSwitch.setChecked(true);

                } else {
                    // Object with the specified ID does not exist
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error
            }
        });
    }

    private void setUpMenu(View view){
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
                    person.setIs_toggle(binding.materialSwitch.isChecked()?1:0);
                    person.setEmail(binding.edit3.getText().toString());
                    person.setName(binding.edit1.getText().toString());
                    usersRef.child(person.getPhone()).setValue(person);

                    break;
            }
            return false;
        });

    }





    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpViews(Objects.requireNonNull(mAuth.getCurrentUser()).getPhoneNumber());
        setUpMenu(view);

        binding.materialSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            activateCheck();
        });
        binding.edit2.setEnabled(false);
        binding.edit1.addTextChangedListener(tw);
        binding.edit3.addTextChangedListener(tw);


    }



    public void activateCheck(){
        if (!person.getEmail().equals(binding.edit3.getText().toString()) || !person.getName().equals(binding.edit1.getText().toString()) || person.getIs_toggle()!=(binding.materialSwitch.isChecked()?1:0)) {
            if (!haveMenu){
                toolbar.inflateMenu(R.menu.temp_menu);
                haveMenu=true;

            }
        }
        else{
            toolbar.getMenu().clear();
            haveMenu=false;
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