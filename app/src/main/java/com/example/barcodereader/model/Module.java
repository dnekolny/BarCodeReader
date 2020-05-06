package com.example.barcodereader.model;

import android.content.Context;

import com.example.barcodereader.helpers.DataHelper;
import com.example.barcodereader.helpers.DataAccess;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Module implements Serializable {

    private long id;
    private String name;
    private int iconId;
    private int color;
    private boolean favorite;

    public Module() {}

    public Module(long id, String name, int iconId, int color, boolean favorite) {
        this.id = id;
        this.name = name;
        this.iconId = iconId;
        this.color = color;
        this.favorite = favorite;
    }

    public static Module getById(long id, Context context) throws IOException, ClassNotFoundException {
        List<Module> modules = DataAccess.getModules(context);
        for (Module m :
                modules) {
            if (m.getId() == id) {
                return m;
            }
        }
        return null;
    }

    public static List<Module> getDefaultModules(){
        List<Module> modules = new ArrayList<>();
        modules.add(new Module(
                DataHelper.getRandomLong(),
                "Default",
                213,
                0xFFFFFFFF,
                true
        ));
        modules.add(new Module(
                DataHelper.getRandomLong(),
                "Other",
                665,
                0xFFFFFFFF,
                false
        ));
        return modules;
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

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
