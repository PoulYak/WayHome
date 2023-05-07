package com.example.wayhome.ui.profile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.wayhome.R;
import com.example.wayhome.data.room.AppDatabase;
import com.example.wayhome.data.room.Pet;
import com.example.wayhome.databinding.FragmentProfileBinding;
import com.example.wayhome.ui.profile.MyMy;
import com.example.wayhome.ui.profile.RecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {
    FragmentProfileBinding mBinding;
    RecyclerAdapter recyclerAdapter;
    private AppDatabase appDatabase;


    private ArrayList<MyMy> arrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        appDatabase = AppDatabase.getInstance(requireContext());
        mBinding = FragmentProfileBinding.inflate(inflater, container, false);

        arrayList = new ArrayList<>();

        arrayList.add(new MyMy(R.drawable.camera, "Jack", "Потерян", "Гончая"));
        arrayList.add(new MyMy(R.drawable.camera, "Jack", "Потерян", "Гончая"));
        arrayList.add(new MyMy(R.drawable.camera, "Jack", "Потерян", "Гончая"));
        arrayList.add(new MyMy(R.drawable.camera, "Jack", "Потерян", "Гончая"));
        arrayList.add(new MyMy(R.drawable.camera, "Jack", "Потерян", "Гончая"));
        recyclerAdapter = new RecyclerAdapter(arrayList);

        mBinding.recyclerView.setAdapter(recyclerAdapter);
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        loadPetsFromDatabase();

        return mBinding.getRoot();

    }

//    public LiveData<List<Pet>> getPetsByOwnerId(int ownerId) {
//        new Thread(() -> {
//            List<Pet> pets = petRepository.getPetsByOwnerId(ownerId);
//            petsLiveData.postValue(pets);
//        }).start();
//        return petsLiveData;
//    }


    private void loadPetsFromDatabase() {
        new Thread(() -> {
            arrayList.clear();
            for (Pet p: appDatabase.petDao().getPetsByOwnerId(3))
                arrayList.add(new MyMy(R.drawable.camera, p.getName(), String.valueOf(p.getOwnerId()), String.valueOf(p.getAge()) ));

            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(() -> recyclerAdapter.notifyDataSetChanged());
        }).start();
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