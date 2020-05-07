package com.example.barcodereader.model;

import java.util.List;

public class CameraItem {
    private String id;
    private String name;

    public CameraItem(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public static CameraItem getCameraByIdOrFirst(String id, List<CameraItem> cameras){
        for (CameraItem c :
                cameras)
            if (c.getId().equals(id)) {
                return c;
            }
        return cameras.get(0);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;            // What to display in the Spinner list.
    }
}
