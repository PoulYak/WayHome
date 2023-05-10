package com.example.wayhome.data;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.wayhome.data.room.AppDatabase;
import com.example.wayhome.ui.profile.RecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FirebaseDataManager {
    private RecyclerAdapter recyclerAdapter;
    private AppDatabase appDatabase;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseStorage firebaseStorage;

    private DatabaseReference petRef;


    public FirebaseDataManager(Context context) {
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        appDatabase = AppDatabase.getInstance(context);
        petRef = firebaseDatabase.getReference("Pets");
        petRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Get the data snapshot from Firebase
                // Map the snapshot to your Room entities
                // Insert/update the entities in the Room database using the DAO

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error
            }
        });
    }

}
