/*package com.example.barcodereader.ui.scan;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.barcodereader.R;
import com.example.barcodereader.helpers.DataAccess;
import com.example.barcodereader.model.Setting;

import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import me.dm7.barcodescanner.core.IViewFinder;
import me.dm7.barcodescanner.core.ViewFinderView;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ZXingScannerFragment extends Fragment {

    private ZXingScannerView scanner;
    private SharedPreferences sharedPref;
    private Setting setting;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        long moduleId = sharedPref.getLong(getString(R.string.sp_active_module_id), 0);

        try {
            setting = DataAccess.getSettingByModule(moduleId, getContext());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        scanner = new ZXingScannerView(getContext()) {
            @Override
            protected IViewFinder createViewFinderView(Context context) {
                ViewFinderView vfv = new ViewFinderView(context);
                vfv.setBorderColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
                vfv.setSquareViewFinder(true);
                vfv.setBorderAlpha(1);
                return vfv;
            }
        };

        scanner.setAspectTolerance(0.8f);

        return scanner;
    }


    @Override
    public void onResume() {
        super.onResume();
        scanner.setResultHandler(new ZXingResultHandler(setting, getContext()));
        scanner.startCamera();
    }

    @Override
    public void onDestroy() {
        scanner.stopCamera();
        super.onDestroy();
    }

}*/