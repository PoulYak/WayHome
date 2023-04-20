package com.example.wayhome.ui.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wayhome.R;
import com.example.wayhome.databinding.FragmentHomeBinding;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    FragmentHomeBinding mBinding;

    RecyclerView recyclerView;
    private ArrayList<MyMy> arrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentHomeBinding.inflate(inflater, container, false);

        arrayList = new ArrayList<>();

        arrayList.add(new MyMy(R.drawable.camera, R.drawable.pets, "mytitle", "mymessage"));
        arrayList.add(new MyMy(R.drawable.camera, R.drawable.pets, "mytitle", "mymessage"));
        arrayList.add(new MyMy(R.drawable.camera, R.drawable.pets, "mytitle", "mymessage"));
        arrayList.add(new MyMy(R.drawable.camera, R.drawable.pets, "mytitle", "mymessage"));
        arrayList.add(new MyMy(R.drawable.camera, R.drawable.pets, "mytitle", "mymessage"));
        arrayList.add(new MyMy(R.drawable.camera, R.drawable.pets, "mytitle", "mymessage"));

        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(arrayList);

        mBinding.recyclerView.setAdapter(recyclerAdapter);
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));



        return mBinding.getRoot();

    }
}
