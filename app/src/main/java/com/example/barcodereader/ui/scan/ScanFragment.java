package com.example.barcodereader.ui.scan;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.barcodereader.MapsActivity;
import com.example.barcodereader.R;
import com.example.barcodereader.helpers.DataAccess;
import com.example.barcodereader.model.Module;
import com.example.barcodereader.model.Setting;

import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import me.dm7.barcodescanner.core.IViewFinder;
import me.dm7.barcodescanner.core.ViewFinderView;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanFragment extends Fragment {

    private ZXingScannerView scanner;
    private SharedPreferences sharedPref;

    private Setting setting;
    private Module module;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        getActivity().setTitle(getString(R.string.title_scan));
        setHasOptionsMenu(true);

        sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        try {
            long moduleId = sharedPref.getLong(getString(R.string.sp_active_module_id), 0);
            module = DataAccess.getModuleById(moduleId, getContext());
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

        //scanner.setAspectTolerance(0.8f);

        return scanner;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.scan_menu_buttons, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_scan_light) {
            scanner.setFlash(!scanner.getFlash());
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        scanner.setResultHandler(new ZXingResultHandler(setting, module, getContext()));
        try{
            scanner.startCamera(Integer.parseInt(setting.getIdCamera()));
        }
        catch (NumberFormatException e) {
            scanner.startCamera();
        }
    }

    @Override
    public void onPause() {
        scanner.setFlash(false);
        super.onPause();
    }

    @Override
    public void onDestroy() {
        scanner.stopCamera();
        super.onDestroy();
    }

}