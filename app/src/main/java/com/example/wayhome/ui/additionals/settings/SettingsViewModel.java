package com.example.wayhome.ui.additionals.settings;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SettingsViewModel extends ViewModel {
    private MutableLiveData<String> name;
    private MutableLiveData<String> phone;
    private MutableLiveData<String> email;
    private MutableLiveData<Boolean> haveMenu;

    public SettingsViewModel() {
        name = new MutableLiveData<>();
        phone = new MutableLiveData<>();
        email = new MutableLiveData<>();
        haveMenu = new MutableLiveData<>();
        haveMenu.setValue(false);

    }

    public LiveData<String> getName() {
        return name;
    }

    public void setName(String text) {
        name.setValue(text);
    }

    public void setEmail(String text) {
        email.setValue(text);
    }
    public LiveData<String> getEmail() {
        return email;
    }


    public LiveData<String> getPhone() {
        return phone;
    }

    public void setPhone(String text) {
        phone.setValue(text);
    }

    public LiveData<Boolean> isHaveMenu() {
        return haveMenu;
    }

    public void setHaveMenu(Boolean text) {
        haveMenu.setValue(text);
    }
}
