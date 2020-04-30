package com.example.barcodereader.ui.modules;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ModulesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ModulesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is MODULES fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}