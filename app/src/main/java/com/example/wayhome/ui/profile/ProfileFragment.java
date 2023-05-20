package com.example.wayhome.ui.profile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wayhome.R;
import com.example.wayhome.data.room.AppDatabase;
import com.example.wayhome.databinding.FragmentHomeBinding;
import com.example.wayhome.databinding.FragmentProfileBinding;
import com.example.wayhome.data.room.MyMy;
import com.example.wayhome.ui.home.HomeViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {
    FragmentProfileBinding binding;
    RecyclerAdapter recyclerAdapter;
    private AppDatabase appDatabase;
    DatabaseReference fireDatabase;
    ProfileViewModel viewModel;

    private ArrayList<MyMy> arrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);

        recyclerAdapter = new RecyclerAdapter(); //todo
        fireDatabase = FirebaseDatabase.getInstance().getReference("Pets");
        binding.recyclerView.setAdapter(recyclerAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        viewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        binding.setViewModel(viewModel);
        return binding.getRoot();

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        subscribeToItemList();
        updateFeed();
    }

    private void updateFeed() {
        fireDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                viewModel.updateData(snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void subscribeToItemList() {
        viewModel.getItemList().observe(getViewLifecycleOwner(), new Observer<ArrayList<MyMy>>() {
            @Override
            public void onChanged(ArrayList<MyMy> arrayList) {
                recyclerAdapter.setData(arrayList);
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(() -> recyclerAdapter.notifyDataSetChanged());
            }

        });
    }
}