package com.example.barcodereader.model;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import java.io.Serializable;
import java.util.List;

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

    public String findCorrectUrl(BarcodeFormat format){
        BarcodeType type = BarcodeType.getTypeFromFormat(format);

        for (int i = searchSettings.size() - 1; i >= 0; i--) {
            SearchSetting searchSetting = searchSettings.get(i);

            if(searchSetting.getCodeType().equals(type)){
                return searchSetting.getUrl();
            }
        }
        return searchSettings.get(0).getUrl();
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
