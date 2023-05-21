package com.example.wayhome.ui.card;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wayhome.data.repository.HomeRepository;
import com.example.wayhome.data.room.MyMy;

import java.util.HashMap;
import java.util.List;

public class CardViewModel extends ViewModel {
    private MutableLiveData<String> cardNumber = new MutableLiveData<>();
    private MutableLiveData<String> shareText = new MutableLiveData<>();
    private MutableLiveData<MyMy> pet = new MutableLiveData<>();
    private LiveData<HashMap<String, MyMy>> petDict;
    private HomeRepository homeRepository;

    public CardViewModel() {
        homeRepository = new HomeRepository();
    }

    public LiveData<HashMap<String, MyMy>> getPetDict() {
        if (petDict == null) {
            petDict = homeRepository.getPetDict();
        }
        return petDict;
    }

    public LiveData<String> getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String number) {
        cardNumber.setValue(number);
    }
    public LiveData<String> getShareText() {
        return shareText;
    }

    public void setShareText(String text) {
        shareText.setValue(text);
    }
    public LiveData<MyMy> getPet() {
        return pet;
    }

    public void setPet(MyMy mymy) {
        pet.setValue(mymy);
    }



}
