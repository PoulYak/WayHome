package com.example.wayhome.ui.additionals;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.wayhome.R;
import com.example.wayhome.databinding.FragmentFeedBackBinding;
import com.example.wayhome.databinding.FragmentShareBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class FeedBackFragment extends Fragment {
    FragmentFeedBackBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFeedBackBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NavController navController = Navigation.findNavController(requireView());
        AppBarConfiguration appBarConfiguration =
                new AppBarConfiguration.Builder(navController.getGraph()).build();
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        NavigationUI.setupWithNavController(
                toolbar, navController, appBarConfiguration);
        toolbar.setTitle(R.string.aid);

        binding.sendButton.setOnClickListener(v -> {
            if (binding.textInput.getText().length()<3)
                Toast.makeText(requireContext(), "Слишком короткое сообщение", Toast.LENGTH_SHORT).show();
            else{
                //todo Добавлять вопрос в бд на firebase
                binding.textInput.setText("");
                Toast.makeText(requireContext(), "Ваше сообщение успешно отправлено в поддержку", Toast.LENGTH_SHORT).show();

            }

        });
        binding.mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri adress= Uri.parse("https://t.me/poulyak");
                Intent browser= new Intent(Intent.ACTION_VIEW, adress);
                startActivity(browser);
            }
        });
    }
}