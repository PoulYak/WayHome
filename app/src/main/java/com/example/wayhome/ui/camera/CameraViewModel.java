package com.example.wayhome.ui.camera;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.yandex.mapkit.map.MapObjectCollection;
import com.yandex.mapkit.mapview.MapView;

import java.util.ArrayList;

public class CameraViewModel extends ViewModel {
    private MutableLiveData<String> mText = new MutableLiveData<>();
    ArrayList<Uri> imageUries = new ArrayList<>();

    boolean isActive = false;

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
}
