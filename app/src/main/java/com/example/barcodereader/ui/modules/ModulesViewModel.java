package com.example.barcodereader.ui.modules;

import com.example.barcodereader.helpers.DataHelper;
import com.example.barcodereader.model.Module;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ModulesViewModel extends ViewModel {

    private MutableLiveData<Module> moduleLiveData;

    public ModulesViewModel() {
        moduleLiveData = new MutableLiveData<>();
        setDefaultModule();
    }

    public void setDefaultModule(){
        Module module = new Module(
                Math.round(Math.random() * 9999999999L),
                "NAME",
                DataHelper.getRandomNumberInRange(0, 976),
                0xFFFFFFFF,
                false);
        moduleLiveData.setValue(module);
    }

    public void setModule(Module module) {
        moduleLiveData.setValue(module);
    }

    public LiveData<Module> getModule() {
        return moduleLiveData;
    }

    public void changeIconId(int id) {
        Module module = moduleLiveData.getValue();
        module.setIconId(id);
        moduleLiveData.setValue(module);
    }
}