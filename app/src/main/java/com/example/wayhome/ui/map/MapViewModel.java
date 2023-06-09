package com.example.wayhome.ui.map;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wayhome.data.repository.HomeRepository;
import com.example.wayhome.data.room.MyMy;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MapViewModel extends ViewModel {
    private MutableLiveData<List<MyMy>> locationDataList;
    private MutableLiveData<CameraPosition> cameraPosition;
    private MutableLiveData<Boolean> firstTime;
    private LiveData<List<MyMy>> homeItemList;
    private HomeRepository homeRepository;

    public MapViewModel() {
        homeRepository = new HomeRepository();
    }

    public LiveData<List<MyMy>> getLocationDataList() {
        if (locationDataList == null) {
            locationDataList = new MutableLiveData<>();
            loadLocationData();
        }
        return locationDataList;
    }

    public LiveData<CameraPosition> getCameraPosition() {
        if (cameraPosition == null) {
            cameraPosition = new MutableLiveData<>();
        }
        return cameraPosition;
    }

    public void setCameraPosition(CameraPosition position) {
        if (cameraPosition != null) {
            cameraPosition.setValue(position);
        }
    }

    public LiveData<Boolean> getFirstTime() {
        if (firstTime == null) {
            firstTime = new MutableLiveData<>();
        }
        return firstTime;
    }

    public void setFirstTime(boolean val) {
        if (firstTime != null) {
            firstTime.setValue(val);
        }
    }

    private void loadLocationData() {
        List<MyMy> data = new ArrayList<>();
        locationDataList.setValue(data);
    }
    public void updateLocationData(List<MyMy> data) {
        locationDataList.setValue(data);
    }


    public LiveData<List<MyMy>> getHomeItemList() {
        if (homeItemList == null) {
            homeItemList = homeRepository.getHomeItemList();
        }
        return homeItemList;
    }
}
