package com.example.barcodereader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;

import android.net.Uri;
import android.os.Bundle;

import com.google.zxing.BarcodeFormat;

public class ResultActivity extends AppCompatActivity {

    private String result;
    private BarcodeFormat type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            result = extras.getString("result");
            type = BarcodeFormat.valueOf(extras.getString("type"));

            openChromeTabs("https://www.google.com/search?q=" + result);
        }
    }

    private void openChromeTabs(String url){
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimary));

        CustomTabsIntent intent = builder.build();
        intent.launchUrl(this, Uri.parse(url));
    }
}
