package com.example.wayhome.ui.card;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CardViewModel extends ViewModel {
    private MutableLiveData<String> cardNumber = new MutableLiveData<>();


    public LiveData<String> getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String number) {
        cardNumber.setValue(number);
    }
}
