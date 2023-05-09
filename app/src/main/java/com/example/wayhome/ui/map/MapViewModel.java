package com.example.wayhome.ui.map;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wayhome.R;
import com.example.wayhome.ui.profile.MyMy;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.MapObjectCollection;
import com.yandex.mapkit.map.MapObjectDragListener;
import com.yandex.mapkit.map.MapObjectTapListener;
import com.yandex.mapkit.map.PlacemarkMapObject;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.runtime.image.ImageProvider;

public class MapViewModel extends ViewModel {
    private boolean isActive = false;
    private MapObjectCollection mapObjects;
    private MapView mapView;

    public void addPoint(MyMy m, MapObjectTapListener tapListener, MapObjectDragListener dragListener, Context context){
        Point point = new Point(m.getLatitude(), m.getLongitude());
        PlacemarkMapObject mark = mapObjects.addPlacemark(point);
        mark.setOpacity(0.7f);
        mark.setIcon(ImageProvider.fromResource(context, R.drawable.dog1));
        isActive = true;
        mark.setDragListener(dragListener);
        mark.addTapListener(tapListener);
        mark.setUserData(m);

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
