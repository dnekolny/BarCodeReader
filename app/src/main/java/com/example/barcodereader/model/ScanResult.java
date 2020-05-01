package com.example.barcodereader.model;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import java.io.Serializable;

public class ScanResult implements Serializable {

    private String value;
    private BarcodeFormat format;
    private long timestamp;

    public ScanResult(Result rawResult) {
        this.value = rawResult.getText();
        this.format = rawResult.getBarcodeFormat();
        this.timestamp = rawResult.getTimestamp();
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
