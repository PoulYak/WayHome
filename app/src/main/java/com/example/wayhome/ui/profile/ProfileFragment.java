package com.example.wayhome.ui.profile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wayhome.R;
import com.example.wayhome.databinding.FragmentProfileBinding;
import com.example.wayhome.ui.profile.MyMy;
import com.example.wayhome.ui.profile.RecyclerAdapter;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {
    FragmentProfileBinding mBinding;

    RecyclerView recyclerView;
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
}