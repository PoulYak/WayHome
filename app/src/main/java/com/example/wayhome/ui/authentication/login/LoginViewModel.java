package com.example.wayhome.ui.authentication.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LoginViewModel extends ViewModel {
    private MutableLiveData<String> loginText;
    private MutableLiveData<String> passwordText;

    public LoginViewModel() {
        loginText = new MutableLiveData<>();
        passwordText = new MutableLiveData<>();
    }

    public LiveData<String> getLoginText() {
        return loginText;
    }

    public void setLoginText(String text) {
        loginText.setValue(text);
    }
    public LiveData<String> getPasswordText() {
        return passwordText;
    }

    public void setPasswordText(String text) {
        passwordText.setValue(text);
    }

}
