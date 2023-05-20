package com.example.wayhome.ui.additionals.settings;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
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
import com.example.wayhome.ui.additionals.feedback.FeedbackViewModel;
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
    FirebaseAuth mAuth;
    DatabaseReference usersRef;
    Person person;
    SettingsViewModel viewModel;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        mAuth= FirebaseAuth.getInstance();
        usersRef = FirebaseDatabase.getInstance().getReference("Users");
        viewModel = new ViewModelProvider(this).get(SettingsViewModel.class);
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }
    private void setUpViews(String uid){
        usersRef.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    person = dataSnapshot.getValue(Person.class);
                    viewModel.setName(person.getName());
                    viewModel.setEmail(person.getEmail());
                    viewModel.setPhone(person.getPhone());
                    binding.edit1.setText(viewModel.getName().getValue());
                    binding.edit2.setText(viewModel.getPhone().getValue());
                    binding.edit3.setText(viewModel.getEmail().getValue());
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
                    viewModel.setHaveMenu(false);
                    person.setIs_toggle(binding.materialSwitch.isChecked()?1:0);
                    person.setPhone(binding.edit2.getText().toString());
                    person.setName(binding.edit1.getText().toString());
                    usersRef.child(mAuth.getCurrentUser().getUid()).setValue(person);

                    break;
            }
            return false;
        });

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpViews(Objects.requireNonNull(mAuth.getCurrentUser()).getUid());
        setUpMenu(view);

        binding.materialSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            activateCheck();
        });
        binding.edit3.setEnabled(false);
        binding.edit1.addTextChangedListener(tw);
        binding.edit2.addTextChangedListener(tw);


    }



    public void activateCheck(){
        if (!viewModel.getPhone().getValue().equals(binding.edit2.getText().toString()) || !person.getName().equals(binding.edit1.getText().toString()) || person.getIs_toggle()!=(binding.materialSwitch.isChecked()?1:0)) {
            if (Boolean.FALSE.equals(viewModel.isHaveMenu().getValue())){
                toolbar.inflateMenu(R.menu.temp_menu);
                viewModel.setHaveMenu(true);

            }
        }
        else{
            toolbar.getMenu().clear();
            viewModel.setHaveMenu(false);
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