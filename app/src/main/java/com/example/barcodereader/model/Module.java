package com.example.barcodereader.model;

public class Module {

    private long id;
    private String name;
    private int iconId;
    private int color;

    public Module(long id, String name, int iconId, int color) {
        this.id = id;
        this.name = name;
        this.iconId = iconId;
        this.color = color;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
