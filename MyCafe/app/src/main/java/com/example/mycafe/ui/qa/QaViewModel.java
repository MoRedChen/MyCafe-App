package com.example.mycafe.ui.qa;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class QaViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public QaViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is qa fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}