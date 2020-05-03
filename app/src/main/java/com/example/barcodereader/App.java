package com.example.barcodereader;

import android.app.Application;

import com.maltaisn.icondialog.pack.IconPack;
import com.maltaisn.icondialog.pack.IconPackLoader;
import com.maltaisn.iconpack.defaultpack.IconPackDefault;

import androidx.annotation.Nullable;

public class App extends Application {

    private static Application myApp;

    @Nullable
    private IconPack iconPack;

    public App() {
        myApp = this;
    }

    public static Application getApp(){
        return myApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // Load the icon pack on application start.
        loadIconPack();
    }

    @Nullable
    public IconPack getIconPack() {
        return iconPack != null ? iconPack : loadIconPack();
    }

    private IconPack loadIconPack() {
        // Create an icon pack loader with application context.
        IconPackLoader loader = new IconPackLoader(this);

        // Create an icon pack and load all drawables.
        iconPack = IconPackDefault.createDefaultIconPack(loader);
        iconPack.loadDrawables(loader.getDrawableLoader());

        return iconPack;
    }
}
