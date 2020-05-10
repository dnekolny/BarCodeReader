package com.example.barcodereader;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.example.barcodereader.biometric.BiometricCallback;
import com.example.barcodereader.biometric.BiometricManager;
import com.example.barcodereader.helpers.DataAccess;
import com.example.barcodereader.model.Module;
import com.example.barcodereader.ui.scan.ScanFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity implements BiometricCallback {

    public static final int MAIN_REQUEST_CODE = 5485;

    private static NavController navController;
    private SharedPreferences sharedPref;
    BiometricManager mBiometricManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //THEME
        //setTheme(R.style.AppThemeLight);

        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);

        sharedPref = getSharedPreferences(getString(R.string.sp_name), Context.MODE_PRIVATE);

        checkPermissions();

        boolean fingerprintEnable = sharedPref.getBoolean(getString(R.string.sp_fingerprint_enable), false);
        if (fingerprintEnable) {
            showFingerPrintDialog();
        }
        else {
            setAuth(true);
        }

        //SET DEFAULT MODULE
        long moduleId = sharedPref.getLong(getString(R.string.sp_active_module_id), -1);
        if (moduleId == -1) {
            try {
                List<Module> modules = DataAccess.getModules(this);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putLong(getString(R.string.sp_active_module_id), modules.get(0).getId());
                editor.commit();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void showFingerPrintDialog() {
        mBiometricManager = new BiometricManager.BiometricBuilder(MainActivity.this)
                .setTitle(getString(R.string.biometric_title))
                .setSubtitle(getString(R.string.biometric_subtitle))
                .setDescription(getString(R.string.biometric_description))
                .setNegativeButtonText(getString(R.string.biometric_negative_button_text))
                .build();

        //start authentication
        mBiometricManager.authenticate(MainActivity.this);
    }

    private void checkPermissions() {
        List<String> permissions = new ArrayList<>();

        //CAMERA PERMISSION
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            permissions.add(Manifest.permission.CAMERA);
        }

        //GPS PERMISSION
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED) {
            permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }

        if (permissions.size() > 0) {
            ActivityCompat.requestPermissions(this, permissions.toArray(new String[0]), MAIN_REQUEST_CODE);
        }
    }

    private void enableScan() {
        setAuth(true);

        try {
            NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
            ScanFragment scanF = (ScanFragment) navHostFragment.getChildFragmentManager().getFragments().get(0);
            scanF.enableScan();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setAuth(boolean value){
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(getString(R.string.sp_is_auth), value);
        editor.apply();
    }

    @Override
    protected void onDestroy() {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(getString(R.string.sp_is_auth), false);
        editor.commit();
        super.onDestroy();
    }

    //FINGER PRINT
    @Override
    public void onSdkVersionNotSupported() {
    }

    @Override
    public void onBiometricAuthenticationNotSupported() {
    }

    @Override
    public void onBiometricAuthenticationNotAvailable() {
    }

    @Override
    public void onBiometricAuthenticationPermissionNotGranted() {
    }

    @Override
    public void onBiometricAuthenticationInternalError(String error) {
    }

    @Override
    public void onAuthenticationFailed() {
    }

    @Override
    public void onAuthenticationCancelled() {
        finish();
    }

    @Override
    public void onAuthenticationSuccessful() {
        enableScan();
    }

    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
    }

    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {
    }

}
