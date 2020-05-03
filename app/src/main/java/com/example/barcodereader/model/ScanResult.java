package com.example.barcodereader.model;

import android.location.Location;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import java.io.Serializable;

public class ScanResult implements Serializable {

    private long id;
    private String value;
    private BarcodeFormat format;
    private long timestamp;

    private double latitude;
    private double longitude;

    public static ScanResult create(Result rawResult, Location location){
        ScanResult result = new ScanResult();
        result.id = Math.round(Math.random() * 99999999999L);
        result.value = rawResult.getText();
        result.format = rawResult.getBarcodeFormat();
        result.timestamp = rawResult.getTimestamp();
        if(location != null){
            result.longitude = location.getLongitude();
            result.latitude = location.getLatitude();
        }
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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
