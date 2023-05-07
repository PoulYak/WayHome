package com.example.wayhome.ui.camera.getlocation;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.yandex.mapkit.map.MapObjectCollection;
import com.yandex.mapkit.mapview.MapView;

public class LocationViewModel extends AndroidViewModel {
    private boolean isActive = false;
    private MapObjectCollection mapObjects;
    private MapView mapView;

    public LocationViewModel(@NonNull Application application) {
        super(application);
    }

    public MapObjectCollection getMapObjects() {
        return mapObjects;
    }

    public void setMapObjects(MapObjectCollection mapObjects) {
        this.mapObjects = mapObjects;
    }

    public MapView getMapView() {
        return mapView;
    }

    public void setMapView(MapView mapView) {
        this.mapView = mapView;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

}
