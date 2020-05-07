package com.example.barcodereader.model;

import com.example.barcodereader.helpers.DataHelper;

import java.io.Serializable;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class Setting implements Serializable {

    private long id;
    private long idModule;
    private String idCamera;
    private boolean sound;
    private boolean vibration;
    private boolean chromeTabs;
    private boolean useUrl;

    private List<SearchSetting> searchSettings;

    public Setting(long idSetting, long idModule, String idCamera, boolean sound, boolean vibration, boolean chromeTabs, boolean useUrl, List<SearchSetting> searchSettings) {
        this.id = idSetting;
        this.idModule = idModule;
        this.idCamera = idCamera;
        this.sound = sound;
        this.vibration = vibration;
        this.chromeTabs = chromeTabs;
        this.useUrl = useUrl;
        this.searchSettings = searchSettings;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdModule() {
        return idModule;
    }

    public void setIdModule(long idModule) {
        this.idModule = idModule;
    }

    public String getIdCamera() {
        return idCamera;
    }

    public void setIdCamera(String idCamera) {
        this.idCamera = idCamera;
    }

    public boolean isSound() {
        return sound;
    }

    public void setSound(boolean sound) {
        this.sound = sound;
    }

    public boolean isVibration() {
        return vibration;
    }

    public void setVibration(boolean vibration) {
        this.vibration = vibration;
    }

    public boolean isChromeTabs() {
        return chromeTabs;
    }

    public void setChromeTabs(boolean chromeTabs) {
        this.chromeTabs = chromeTabs;
    }

    public boolean isUseUrl() {
        return useUrl;
    }

    public void setUseUrl(boolean useUrl) {
        this.useUrl = useUrl;
    }

    public List<SearchSetting> getSearchSettings() {
        return searchSettings;
    }

    public void setSearchSettings(List<SearchSetting> searchSettings) {
        this.searchSettings = searchSettings;
    }
}
