package com.example.barcodereader;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;

import com.example.barcodereader.helpers.DataAccess;
import com.example.barcodereader.helpers.DataHelper;
import com.example.barcodereader.model.ScanResult;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    private GoogleMap mMap;
    private SharedPreferences sharedPref;
    private List<ScanResult> results;
    private List<Pair<String, ScanResult>> resultsWithMarkerIds = new ArrayList<>();
    private LatLng startPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        sharedPref = getSharedPreferences(getString(R.string.sp_name), Context.MODE_PRIVATE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarMap);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setTitle("History map");
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        boolean onlyCurrentModule = sharedPref.getBoolean(getString(R.string.sp_history_current_module), false);
        long moduleId = sharedPref.getLong(getString(R.string.sp_active_module_id), -1);

        results = new ArrayList<>();
        try {
            if (onlyCurrentModule) {
                results = DataAccess.getResultsByModuleId(moduleId, this);
            } else {
                results = DataAccess.getResults(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        googleMap.setOnInfoWindowClickListener(this);

        LatLngBounds bounds = addMarkersToMap(googleMap);

        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        int padding = (int) (width * 0.10); // offset from edges of the map 10% of screen

        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);
        googleMap.moveCamera(cu);
        //googleMap.animateCamera(cu);
    }

    private LatLngBounds addMarkersToMap(GoogleMap map) {

        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        boolean first = true;

        for (ScanResult result :
                results) {
            if (result.getLatitude() > 0 && result.getLongitude() > 0) {
                LatLng position = new LatLng(result.getLatitude(), result.getLongitude());

                if (first) {
                    startPos = new LatLng(result.getLatitude(), result.getLongitude());
                    first = false;
                }

                Marker marker = map.addMarker(new MarkerOptions()
                        .position(position)
                        .title(result.getValue())
                        .snippet(DataHelper.getDate(result.getTimestamp()))
                );
                builder.include(position);

                resultsWithMarkerIds.add(new Pair<>(
                        marker.getId(),
                        result
                ));
            }
        }
        return builder.build();
    }

    //INFO WINDOW CLICK
    @Override
    public void onInfoWindowClick(Marker marker) {
        for (Pair<String, ScanResult> resultPair
                : resultsWithMarkerIds) {
            if(resultPair.first.equals(marker.getId())){
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                builder.setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimary));
                CustomTabsIntent intent = builder.build();
                intent.launchUrl(this, Uri.parse(resultPair.second.getUrl()));
            }
        }
    }
}









