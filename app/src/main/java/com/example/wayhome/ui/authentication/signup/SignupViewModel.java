package com.example.wayhome.ui.authentication.signup;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SignupViewModel extends ViewModel {
    private MutableLiveData<String> loginText;
    private MutableLiveData<String> passwordText;

    public SignupViewModel() {
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
