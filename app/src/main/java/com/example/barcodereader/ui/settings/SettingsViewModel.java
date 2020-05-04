package com.example.barcodereader.ui.settings;

import com.example.barcodereader.helpers.DataHelper;
import com.example.barcodereader.model.Module;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SettingsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SettingsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is SETTINGS fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}