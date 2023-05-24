package com.example.wayhome.ui.camera;

import android.app.Activity;
import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.wayhome.data.room.AppDatabase;
import com.example.wayhome.data.room.MyMyRepository;
import com.example.wayhome.data.room.MyMy;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CameraViewModel extends AndroidViewModel {
    private MutableLiveData<String> mText = new MutableLiveData<>();
    private final MutableLiveData<List<MyMy>> petsLiveData;
    DatabaseReference petsRef;
    public StorageReference storageRef;
    private double longitude = 0.0f;
    private double latitude = 0.0f;
    private boolean isSelected = false;
    private Uri imageUri;
    private String path;
    private boolean isPhoto = false;
    private boolean isMap = false;

    public boolean isMap() {
        return isMap;
    }

    public void setMap(boolean map) {
        isMap = map;
    }

    public boolean isPhoto() {
        return isPhoto;
    }

    public void setPhoto(boolean photo) {
        isPhoto = photo;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    long amountOfPets=0;


    boolean isActive = false;

    public CameraViewModel(@NonNull Application application) {
        super(application);
        this.petsLiveData = new MutableLiveData<>();
        petsRef = FirebaseDatabase.getInstance().getReference("Pets");

        storageRef = FirebaseStorage.getInstance().getReference();
        petsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                amountOfPets = dataSnapshot.getChildrenCount();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle the error
            }
        });
    }


    public void insertPet(MyMy pet) {
        pet.setId("pet"+amountOfPets);
        pet.setImage_path(path);
        petsRef.child(pet.getId()).setValue(pet);
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public LiveData<String> getText() {
        return mText;
    }

    public void setText(String text) {
        mText.setValue(text);
    }

    public String getPath() {
        return path;
    }



    public void setPath(String path) {
        this.path = path;
    }

    public void uploadFile(Context context, Activity activity){
        if (imageUri!=null){
            path = "uploads/pet"+amountOfPets+"."+getFileExtension(imageUri, activity);
            StorageReference fileRef = storageRef.child(path);
            fileRef.putFile(imageUri)
                    .addOnSuccessListener(taskSnapshot -> {
                    }).addOnFailureListener(e -> {
                    });
        }else{
            Toast.makeText(context, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }
    private String getFileExtension(Uri uri, Activity activity){
        ContentResolver cR = activity.getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
}
