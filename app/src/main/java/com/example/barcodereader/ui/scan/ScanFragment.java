package com.example.barcodereader.ui.scan;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.barcodereader.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class ScanFragment extends Fragment {

    private ScanViewModel scanViewModel;
    private ZXingScannerFragment scanner;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        scanViewModel = ViewModelProviders.of(this).get(ScanViewModel.class);

        View root = inflater.inflate(R.layout.fragment_scan, container, false);
        //final TextView textView = root.findViewById(R.id.text_scan);

        scanViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });

        scanner = (ZXingScannerFragment)getChildFragmentManager().findFragmentById(R.id.zxingScannerFragment);

        return root;
    }

}