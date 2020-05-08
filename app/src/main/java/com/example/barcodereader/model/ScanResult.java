package com.example.barcodereader.model;

import android.location.Location;

import com.example.barcodereader.helpers.DataHelper;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import java.io.Serializable;
import java.util.Comparator;

public class ScanResult implements Serializable {

    private long id;
    private String value;
    private String url;
    private BarcodeFormat format;
    private long timestamp;

    private long idModule;
    private int iconId;
    private int color;

    private double latitude;
    private double longitude;

    public static ScanResult create(Result rawResult, Module module, Location location, String url){
        ScanResult result = new ScanResult();
        result.id = DataHelper.getRandomLong();
        result.value = rawResult.getText();
        result.format = rawResult.getBarcodeFormat();
        result.timestamp = rawResult.getTimestamp();
        result.url = url;

        if(module != null){
            result.idModule = module.getId();
            result.iconId = module.getIconId();
            result.color = module.getColor();
        }

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

    public long getIdModule() {
        return idModule;
    }

    public void setIdModule(long idModule) {
        this.idModule = idModule;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
