package com.example.wayhome.ui.camera;

import android.net.Uri;

public class Photo {
    private Uri imageUri;

    public Photo(Uri imageUri) {
        this.imageUri = imageUri;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }
}
