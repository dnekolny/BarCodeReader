package com.example.barcodereader.model;

import com.example.barcodereader.helpers.DataHelper;

import java.io.Serializable;

public class SearchSetting implements Serializable {

    private long id;
    private boolean isDefault;
    private BarcodeType codeType;
    private String url;

    public SearchSetting(long id, boolean isDefault, BarcodeType codeType, String url) {
        this.id = id;
        this.isDefault = isDefault;
        this.codeType = codeType;
        this.url = url;
    }

    public static SearchSetting getDefaultSearchSetting(boolean isDefault) {
        return new SearchSetting(
                DataHelper.getRandomLong(),
                isDefault,
                BarcodeType.ALL,
                "https://www.google.com/search?q={code}");
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BarcodeType getCodeType() {
        return codeType;
    }

    public void setCodeType(BarcodeType codeType) {
        this.codeType = codeType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }
}
