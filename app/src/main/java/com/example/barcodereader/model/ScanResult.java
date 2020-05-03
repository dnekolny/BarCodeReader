package com.example.barcodereader.model;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import java.io.Serializable;

public class ScanResult implements Serializable {

    private long id;
    private String value;
    private BarcodeFormat format;
    private long timestamp;

    public static ScanResult create(Result rawResult){
        ScanResult result = new ScanResult();
        result.id = Math.round(Math.random() * 99999999999L);
        result.value = rawResult.getText();
        result.format = rawResult.getBarcodeFormat();
        result.timestamp = rawResult.getTimestamp();
        return result;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public BarcodeFormat getFormat() {
        return format;
    }

    public void setFormat(BarcodeFormat format) {
        this.format = format;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
