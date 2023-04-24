package com.example.wayhome.ui.map;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.yandex.mapkit.map.MapObjectCollection;
import com.yandex.mapkit.mapview.MapView;

public class MapViewModel extends ViewModel {
    private boolean isActive = false;
    private MapObjectCollection mapObjects;
    private MapView mapView;

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
