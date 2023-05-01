package com.example.wayhome.ui.profile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.wayhome.R;
import com.example.wayhome.databinding.FragmentProfileBinding;
import com.example.wayhome.ui.profile.MyMy;
import com.example.wayhome.ui.profile.RecyclerAdapter;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {
    FragmentProfileBinding mBinding;

    private ArrayList<MyMy> arrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentProfileBinding.inflate(inflater, container, false);

        arrayList = new ArrayList<>();

        arrayList.add(new MyMy(R.drawable.camera, "Jack", "Потерян", "Гончая"));
        arrayList.add(new MyMy(R.drawable.camera, "Jack", "Потерян", "Гончая"));
        arrayList.add(new MyMy(R.drawable.camera, "Jack", "Потерян", "Гончая"));
        arrayList.add(new MyMy(R.drawable.camera, "Jack", "Потерян", "Гончая"));
        arrayList.add(new MyMy(R.drawable.camera, "Jack", "Потерян", "Гончая"));
        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(arrayList);

        mBinding.recyclerView.setAdapter(recyclerAdapter);
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));



        return mBinding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBinding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(requireContext(), "Открытие формы", Toast.LENGTH_SHORT).show();
            }
        });
    }
}