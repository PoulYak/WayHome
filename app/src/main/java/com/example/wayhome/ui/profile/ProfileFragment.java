package com.example.wayhome.ui.profile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wayhome.R;
import com.example.wayhome.data.room.AppDatabase;
import com.example.wayhome.databinding.FragmentProfileBinding;
import com.example.wayhome.data.room.MyMy;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {
    FragmentProfileBinding mBinding;
    RecyclerAdapter recyclerAdapter;
    private AppDatabase appDatabase;
    DatabaseReference fireDatabase;




    private ArrayList<MyMy> arrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        appDatabase = AppDatabase.getInstance(requireContext());
        fireDatabase = FirebaseDatabase.getInstance().getReference("Pets");
        mBinding = FragmentProfileBinding.inflate(inflater, container, false);

        arrayList = new ArrayList<>();

        recyclerAdapter = new RecyclerAdapter(arrayList);

        mBinding.recyclerView.setAdapter(recyclerAdapter);
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


//        String userId = "unique_user_id"; // Generate a unique ID for the user

//        loadPetsFromDatabase();
        fireDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    MyMy u = dataSnapshot.getValue(MyMy.class);
                    arrayList.add(u);
                }
                recyclerAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return mBinding.getRoot();

    }



//    private void loadPetsFromDatabase() {
//        new Thread(() -> {
//            arrayList.clear();
//            for (Pet p: appDatabase.petDao().getPetsByOwnerId(3))
//                arrayList.add(new MyMy(R.drawable.camera, p.getName(), String.valueOf(p.getOwnerId()), String.valueOf(p.getAge()) ));
//
//            Handler handler = new Handler(Looper.getMainLooper());
//            handler.post(() -> recyclerAdapter.notifyDataSetChanged());
//        }).start();
//    }


}