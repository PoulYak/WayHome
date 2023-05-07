package com.example.wayhome.ui.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wayhome.R;
import com.example.wayhome.data.room.AppDatabase;
import com.example.wayhome.data.room.Pet;
import com.example.wayhome.databinding.FragmentHomeBinding;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    FragmentHomeBinding binding;

    private ArrayList<MyMy> arrayList;
    RecyclerAdapter recyclerAdapter;
    private AppDatabase appDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        appDatabase = AppDatabase.getInstance(requireContext());
        arrayList = new ArrayList<>();

        recyclerAdapter = new RecyclerAdapter(arrayList);

        binding.recyclerView.setAdapter(recyclerAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));



        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadPetsFromDatabase();
    }

    private void loadPetsFromDatabase() {
        new Thread(() -> {
            arrayList.clear();
            for (Pet p: appDatabase.petDao().getAllPets())
                arrayList.add(new MyMy(R.drawable.camera, R.drawable.camera, p.getName(), String.valueOf(p.getAge()) ));

            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(() -> recyclerAdapter.notifyDataSetChanged());
        }).start();
    }
}
