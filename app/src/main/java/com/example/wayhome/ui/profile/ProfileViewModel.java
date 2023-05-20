package com.example.wayhome.ui.profile;

import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wayhome.R;
import com.example.wayhome.data.repository.HomeRepository;
import com.example.wayhome.data.room.MyMy;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

public class ProfileViewModel extends ViewModel {
    private LiveData<List<MyMy>> homeItemList;
    private HomeRepository homeRepository;


    public ProfileViewModel() {
        homeRepository = new HomeRepository();
    }
    public LiveData<List<MyMy>> getHomeItemList() {
        if (homeItemList == null) {
            homeItemList = homeRepository.getHomeItemList();
        }
        return homeItemList;
    }

}
