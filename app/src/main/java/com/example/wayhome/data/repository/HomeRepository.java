package com.example.wayhome.data.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.wayhome.data.room.MyMy;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeRepository {
    private MutableLiveData<List<MyMy>> homeItemList;
    private MutableLiveData<List<MyMy>> myItemList;
    private DatabaseReference databaseRef;

    public LiveData<List<MyMy>> getHomeItemList() {
        if (homeItemList == null) {
            homeItemList = new MutableLiveData<>();
            databaseRef = FirebaseDatabase.getInstance().getReference("Pets");
            loadHomeItems();
        }
        return homeItemList;
    }

    public LiveData<List<MyMy>> getMyItemList(String email) {
        if (myItemList == null) {
            myItemList = new MutableLiveData<>();
            databaseRef = FirebaseDatabase.getInstance().getReference("Pets");
            loadMyItems(email);
        }
        return myItemList;
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

    private void loadMyItems(String email) {
        Query query = databaseRef.orderByChild("owner_mail").equalTo(email);
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<MyMy> items = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    MyMy item = snapshot.getValue(MyMy.class);
                    items.add(item);
                }
                myItemList.setValue(items);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Обработка ошибок при загрузке данных из Firebase
            }
        };

        query.addValueEventListener(valueEventListener);

    }
}