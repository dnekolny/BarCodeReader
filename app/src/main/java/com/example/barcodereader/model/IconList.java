package com.example.barcodereader.model;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.maltaisn.icondialog.pack.IconPack;
import com.maltaisn.icondialog.pack.IconPackLoader;
import com.maltaisn.iconpack.defaultpack.IconPackDefault;

import androidx.annotation.Nullable;

//singleton icon list
public class IconList {

    private static IconList single_instance = null;

    @Nullable
    private IconPack iconPack;
    private Context context;

    // private constructor restricted to this class itself
    private IconList(Context context)
    {
        this.context = context;
        loadIconPack(context);
    }

    // static method to create instance of Singleton class
    public static IconList getInstance(Context context)
    {
        if (single_instance == null)
            single_instance = new IconList(context);

        return single_instance;
    }

    @Nullable
    public IconPack getIconPack() {
        return iconPack != null ? iconPack : loadIconPack(context);
    }

    private IconPack loadIconPack(Context context) {
        // Create an icon pack loader with application context.
        IconPackLoader loader = new IconPackLoader(context);

        // Create an icon pack and load all drawables.
        iconPack = IconPackDefault.createDefaultIconPack(loader);
        iconPack.loadDrawables(loader.getDrawableLoader());

        return iconPack;
    }

    public Drawable getIconDrawable(int idIcon){
        return getIconPack().getIcon(idIcon).getDrawable();
    }
}
