package com.example.wayhome.data.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.wayhome.data.room.MyMy;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeRepository {
    private MutableLiveData<List<MyMy>> homeItemList;
    private DatabaseReference databaseRef;

    public LiveData<List<MyMy>> getHomeItemList() {
        if (homeItemList == null) {
            homeItemList = new MutableLiveData<>();
            databaseRef = FirebaseDatabase.getInstance().getReference("Pets");
            loadHomeItems();
        }
        return homeItemList;
    }

    private void loadHomeItems() {
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<MyMy> items = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    MyMy item = snapshot.getValue(MyMy.class);
                    items.add(item);
                }
                homeItemList.setValue(items);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Обработка ошибок при загрузке данных из Firebase
            }
        };

        databaseRef.addValueEventListener(valueEventListener);
    }
}