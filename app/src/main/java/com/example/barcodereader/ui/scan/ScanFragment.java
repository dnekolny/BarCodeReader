package com.example.barcodereader.ui.scan;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.barcodereader.R;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class ScanFragment extends Fragment {

    private ZXingScannerFragment scanner;
    private SharedPreferences sharedPref;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_scan, container, false);
        scanner = (ZXingScannerFragment)getChildFragmentManager().findFragmentById(R.id.zxingScannerFragment);

        sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        long moduleId = sharedPref.getLong(getString(R.string.sp_active_module_id), 0);
        Toast.makeText(getContext(), "Active module: " + moduleId, Toast.LENGTH_SHORT).show();

        return root;
    }

}