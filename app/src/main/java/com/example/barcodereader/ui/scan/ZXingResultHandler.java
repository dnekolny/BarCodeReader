package com.example.barcodereader.ui.scan;

import android.content.Context;
import android.location.Location;
import android.net.Uri;
import android.widget.Toast;

import com.example.barcodereader.R;
import com.example.barcodereader.helpers.FileHelper;
import com.example.barcodereader.model.ScanResult;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.zxing.Result;


import androidx.annotation.NonNull;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ZXingResultHandler implements ZXingScannerView.ResultHandler {

    private Context context;
    private FusedLocationProviderClient fusedLocationClient;

    ZXingResultHandler(Context context) {
        this.context = context;
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
    }

    @Override
    public void handleResult(final Result rawResult) {
        Toast.makeText(context, rawResult.getText() + " - " + rawResult.getBarcodeFormat(), Toast.LENGTH_LONG).show();

        showResult(rawResult);
        saveResult(rawResult);

    }

    private void showResult(Result rawResult) {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.setToolbarColor(ContextCompat.getColor(context, R.color.colorPrimary));

        String url = "https://www.google.com/search?q=" + rawResult.getText();
        CustomTabsIntent intent = builder.build();
        intent.launchUrl(context, Uri.parse(url));
    }

    private void saveResult(final Result rawResult){

        Thread thread = new Thread() {
            @Override
            public void run() {

                fusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {

                        try {
                            FileHelper.writeResult(ScanResult.create(rawResult, location), context);

                            Toast.makeText(context, "RESULT SAVED", Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(context, "Error while saving result", Toast.LENGTH_LONG).show();
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
