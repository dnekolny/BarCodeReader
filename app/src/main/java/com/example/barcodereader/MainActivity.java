package com.example.barcodereader;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    public static final int MAIN_REQUEST_CODE = 5485;

    private static NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarMain);
        /*MenuItem menuItem = findViewById(R.id.action_history_map);
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_map_black_24dp, null);
        drawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(drawable, ContextCompat.getColor(this, R.color.colorText));
        menuItem.setIcon(drawable);*/
        setSupportActionBar(toolbar);

        checkPermissions();
    }

    private void checkPermissions(){
        List<String> permissions = new ArrayList<>();

        //CAMERA PERMISSION
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED){
            permissions.add(Manifest.permission.CAMERA);
        }

        //GPS PERMISSION
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED){
            permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }

        if(permissions.size() > 0){
            ActivityCompat.requestPermissions(this, permissions.toArray(new String[0]), MAIN_REQUEST_CODE);
        }
    }

}
