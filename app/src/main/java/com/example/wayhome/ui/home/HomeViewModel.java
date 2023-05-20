package com.example.wayhome.ui.home;


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

public class HomeViewModel extends ViewModel {
    private LiveData<List<MyMy>> homeItemList;
    private HomeRepository homeRepository;


    public HomeViewModel() {
        homeRepository = new HomeRepository();
    }
    public LiveData<List<MyMy>> getHomeItemList() {
        if (homeItemList == null) {
            homeItemList = homeRepository.getHomeItemList();
        }
        return homeItemList;
    }

//    public void updateData(DataSnapshot snapshot){
//        ArrayList<MyMy> arrayList = new ArrayList<>();
//        for (DataSnapshot dataSnapshot: snapshot.getChildren()){
//            MyMy u = dataSnapshot.getValue(MyMy.class);
//            arrayList.add(u);
//        }
//        itemList.setValue(arrayList);
//
//    }
}
