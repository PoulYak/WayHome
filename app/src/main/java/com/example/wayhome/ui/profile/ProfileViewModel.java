package com.example.wayhome.ui.profile;

import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wayhome.R;
import com.example.wayhome.data.room.MyMy;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

public class ProfileViewModel extends ViewModel {
    private MutableLiveData<ArrayList<MyMy>> itemList;


    public ProfileViewModel() {
        itemList = new MutableLiveData<>();
    }
    public LiveData<ArrayList<MyMy>> getItemList() {
        if (itemList == null) {
            itemList = new MutableLiveData<>();


// Загрузите данные элементов здесь из вашего репозитория или другого источника данных
        }
        return itemList;

    }
    private void loadItems() {
        ArrayList<MyMy> items = new ArrayList<>();
        items.add(new MyMy(R.drawable.pets, "her", "GER", "MER"));
        itemList.setValue(items);
    }

    public void updateData(DataSnapshot snapshot){
        ArrayList<MyMy> arrayList = new ArrayList<>();
        for (DataSnapshot dataSnapshot: snapshot.getChildren()){
            MyMy u = dataSnapshot.getValue(MyMy.class);
            arrayList.add(u);
        }
        itemList.setValue(arrayList);

    }
}
