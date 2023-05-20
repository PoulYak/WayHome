package com.example.wayhome.ui.additionals.feedback;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FeedbackViewModel extends ViewModel {
    private MutableLiveData<String> feedbackText;

    public FeedbackViewModel() {
        feedbackText = new MutableLiveData<>();
    }

    public LiveData<String> getFeedbackText() {
        return feedbackText;
    }

    public void setFeedbackText(String text) {
        feedbackText.setValue(text);
    }
}

