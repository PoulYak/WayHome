package com.example.wayhome.ui.additionals.feedback;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wayhome.R;
import com.example.wayhome.databinding.FragmentFeedBackBinding;
import com.example.wayhome.data.room.Support;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FeedBackFragment extends Fragment {
    FragmentFeedBackBinding binding;
    DatabaseReference supportRef;
    FirebaseAuth mAuth;
    FeedbackViewModel viewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFeedBackBinding.inflate(inflater, container, false);
        supportRef = FirebaseDatabase.getInstance().getReference("Supports");
        mAuth= FirebaseAuth.getInstance();
        viewModel = new ViewModelProvider(this).get(FeedbackViewModel.class);
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setUpToolBar();
        setSendButtonClickListener();
        addTgButton();
        viewModel.getFeedbackText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String newData) {
                binding.textInput.setText(newData);
            }
        });

    }
    private void updateData(String newData) {
        viewModel.setFeedbackText(newData);
    }

    private void setSendButtonClickListener() {
        binding.sendButton.setOnClickListener(v -> {
            viewModel.setFeedbackText(binding.textInput.getText().toString());
            if (binding.textInput.getText().length()<3)
                Snackbar.make(requireView(), "Слишком короткое сообщение", Snackbar.LENGTH_SHORT).show();
            else{
                Support s = new Support();
                s.setPhone(mAuth.getCurrentUser().getPhoneNumber());
                s.setMessage(binding.textInput.getText().toString());
                supportRef.push().setValue(s);
                binding.textInput.setText("");
                updateData("");
                Snackbar.make(requireView(), "Ваше сообщение успешно отправлено в поддержку", Snackbar.LENGTH_SHORT).show();

            }

        });
    }

    private void setUpToolBar() {
        NavController navController = Navigation.findNavController(requireView());
        AppBarConfiguration appBarConfiguration =
                new AppBarConfiguration.Builder(navController.getGraph()).build();
        Toolbar toolbar = requireView().findViewById(R.id.toolbar);
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);
        toolbar.setTitle(R.string.aid);
    }

    private void addTgButton(){
        binding.mail.setOnClickListener(v -> {
            Uri address= Uri.parse("https://t.me/poulyak");
            Intent browser= new Intent(Intent.ACTION_VIEW, address);
            startActivity(browser);
        });
    }
}