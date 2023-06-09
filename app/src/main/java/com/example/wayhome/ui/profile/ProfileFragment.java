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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {
    FragmentProfileBinding binding;
    RecyclerAdapter recyclerAdapter;
    ProfileViewModel viewModel;
    FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        mAuth = FirebaseAuth.getInstance();
        recyclerAdapter = new RecyclerAdapter(requireContext()); //todo
        binding.recyclerView.setAdapter(recyclerAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        viewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        binding.setViewModel(viewModel);
        return binding.getRoot();

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        subscribeToHomeItemList();
    }


    private void subscribeToHomeItemList() {
        viewModel.getMyItemList(mAuth.getCurrentUser().getUid()).observe(getViewLifecycleOwner(), new Observer<List<MyMy>>() {
            @Override
            public void onChanged(List<MyMy> homeItems) {
                if (homeItems.size()>0)
                    binding.noLost.setVisibility(View.INVISIBLE);
                else
                    binding.noLost.setVisibility(View.VISIBLE);
                recyclerAdapter.setData((ArrayList<MyMy>) homeItems);
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(() -> recyclerAdapter.notifyDataSetChanged());
            }
        });
    }
}