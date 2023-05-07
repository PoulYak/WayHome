package com.example.wayhome.ui.camera;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wayhome.data.room.AppDatabase;
import com.example.wayhome.data.room.Pet;
import com.example.wayhome.data.room.PetRepository;

import java.util.ArrayList;
import java.util.List;

public class CameraViewModel extends AndroidViewModel {
    private MutableLiveData<String> mText = new MutableLiveData<>();
    ArrayList<Uri> imageUries = new ArrayList<>();
    private ArrayList<Photo> arrayList = new ArrayList<>();
    private final PetRepository petRepository;
    private final MutableLiveData<List<Pet>> petsLiveData;



    boolean isActive = false;

    public CameraViewModel(@NonNull Application application) {
        super(application);
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        this.petRepository = new PetRepository(appDatabase);
        this.petsLiveData = new MutableLiveData<>();
    }

    public void insertPet(Pet pet) {
        petRepository.insertPet(pet);
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
}
