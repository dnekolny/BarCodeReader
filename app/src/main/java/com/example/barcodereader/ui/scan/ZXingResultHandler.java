package com.example.barcodereader.ui.scan;

import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

import com.example.barcodereader.R;
import com.example.barcodereader.helpers.FileHelper;
import com.example.barcodereader.model.ScanResult;
import com.google.zxing.Result;

import java.io.IOException;

import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ZXingResultHandler implements ZXingScannerView.ResultHandler {

    private Context context;

    ZXingResultHandler(Context context) {
        this.context = context;
    }

    @Override
    public void handleResult(Result rawResult) {
        Toast.makeText(context, rawResult.getText() + " - " + rawResult.getBarcodeFormat(), Toast.LENGTH_LONG).show();

        try {
            FileHelper.writeResult(ScanResult.create(rawResult), context);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Error while saving result", Toast.LENGTH_LONG).show();
        }

        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.setToolbarColor(ContextCompat.getColor(context, R.color.colorPrimary));

        String url = "https://www.google.com/search?q=" + rawResult.getText();
        CustomTabsIntent intent = builder.build();
        intent.launchUrl(context, Uri.parse(url));
    }
}
