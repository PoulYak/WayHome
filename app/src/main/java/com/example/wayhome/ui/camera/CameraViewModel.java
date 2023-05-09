package com.example.wayhome.ui.camera;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wayhome.data.room.AppDatabase;
import com.example.wayhome.data.room.Pet;
import com.example.wayhome.data.room.PetRepository;
import com.example.wayhome.data.room.User;
import com.example.wayhome.ui.profile.MyMy;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class CameraViewModel extends AndroidViewModel {
    private MutableLiveData<String> mText = new MutableLiveData<>();
    ArrayList<Uri> imageUries = new ArrayList<>();
    private ArrayList<Photo> arrayList = new ArrayList<>();
    private final PetRepository petRepository;
    private final MutableLiveData<List<Pet>> petsLiveData;
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
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        this.petRepository = new PetRepository(appDatabase);
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

//    public void insertPet(Pet pet) {
//        petRepository.insertPet(pet);
//    }


    public void insertPet(MyMy pet) {
        pet.setId("pet"+amountOfPets);
        pet.setImage_path(path);
        petsRef.child(pet.getId()).setValue(pet);
    }

    public LiveData<List<Pet>> getPetsByOwnerId(int ownerId) {
        new Thread(() -> {
            List<Pet> pets = petRepository.getPetsByOwnerId(ownerId);
            petsLiveData.postValue(pets);
        }).start();

        return petsLiveData;
    }

    public void clearImages(){
        imageUries.clear();
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

    public void addImageUri(Uri imageUri) {
        this.imageUries.add(imageUri);
    }

    public ArrayList<Uri> getImageUri() {
        return imageUries;
    }

    public Uri getImageUriLast() {
        return imageUries.get(imageUries.size()-1);
    }


    public ArrayList<Photo> getPhotos() {
        return arrayList;
    }

    public void addPhoto(Photo photo) {
        arrayList.add(photo);
    }

    public int getSize() {
        return arrayList.size();
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
                        Toast.makeText(context, "SUCCESS UPLOAD", Toast.LENGTH_SHORT).show();
                    }).addOnFailureListener(e -> {
                        Toast.makeText(context, "FAIL", Toast.LENGTH_SHORT).show();
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
