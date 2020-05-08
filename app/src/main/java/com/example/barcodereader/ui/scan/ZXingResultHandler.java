package com.example.barcodereader.ui.scan;

import android.content.Context;
import android.location.Location;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.net.Uri;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Patterns;

import com.example.barcodereader.R;
import com.example.barcodereader.helpers.DataAccess;
import com.example.barcodereader.model.Module;
import com.example.barcodereader.model.ScanResult;
import com.example.barcodereader.model.Setting;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.zxing.Result;


import androidx.annotation.NonNull;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ZXingResultHandler implements ZXingScannerView.ResultHandler {

    private Context context;
    private FusedLocationProviderClient fusedLocationClient;
    private Setting setting;
    private Module module;

    ZXingResultHandler(Setting setting, Module module, Context context) {
        this.setting = setting;
        this.module = module;
        this.context = context;
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
    }

    @Override
    public void handleResult(final Result rawResult) {
        //SOUND
        if (setting.isSound()) {
            ToneGenerator toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, ToneGenerator.MAX_VOLUME);
            toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP, 150);
        }

        //VIBRATION
        if (setting.isVibration()) {
            Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            // Vibrate for 500 milliseconds
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                //deprecated in API 26
                v.vibrate(200);
            }
        }

        showResult(rawResult);
        saveResultAsync(rawResult);
    }

    private void showResult(Result rawResult) {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.setToolbarColor(ContextCompat.getColor(context, R.color.colorPrimary));

        String value = rawResult.getText();
        String url;

        //URL
        if(setting.isUseUrl() && Patterns.WEB_URL.matcher(value).matches()){
            url = value;
        }
        else {
            url = setting.findCorrectUrl(rawResult.getBarcodeFormat()).replace("{code}", rawResult.getText());
        }

        CustomTabsIntent intent = builder.build();
        intent.launchUrl(context, Uri.parse(url));
    }

    private void saveResultAsync(final Result rawResult) {

        Thread thread = new Thread() {
            @Override
            public void run() {
                fusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {

                        try {
                            DataAccess.saveResult(ScanResult.create(rawResult, module, location), context);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        };

        thread.start();

    }
}
