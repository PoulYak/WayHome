package com.example.wayhome.ui.additionals;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wayhome.R;
import com.example.wayhome.databinding.FragmentCameraBinding;
import com.example.wayhome.databinding.FragmentShareBinding;


public class ShareFragment extends Fragment {
    FragmentShareBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentShareBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,"Помоги найтись питомцу c приложением: https://github.com/PoulYak/WayHome");
                sendIntent.setType("text/plain");
//                sendIntent.setType("")
                Intent.createChooser(sendIntent,"Share via");
                startActivity(sendIntent);
            }
        });
        binding.closeBtn.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_shareFragment_to_appFragment);
        });
    }
}