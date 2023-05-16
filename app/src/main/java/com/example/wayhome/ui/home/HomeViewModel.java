package com.example.wayhome.ui.home;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wayhome.data.room.MyMy;

import java.util.ArrayList;

public class HomeViewModel extends ViewModel {
    MutableLiveData<ArrayList<MyMy>> petLiveData;
    ArrayList<MyMy> userArrayList;

    public HomeViewModel() {
        petLiveData = new MutableLiveData<>();
    }


}
