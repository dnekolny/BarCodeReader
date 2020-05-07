package com.example.barcodereader.model;

import java.io.Serializable;

public class SearchSetting implements Serializable {

    private long id;
    private int codeTypeId;
    private String url;

    public SearchSetting(long id, int codeTypeId, String url) {
        this.id = id;
        this.codeTypeId = codeTypeId;
        this.url = url;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCodeTypeId() {
        return codeTypeId;
    }

    public void setCodeTypeId(int codeTypeId) {
        this.codeTypeId = codeTypeId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
