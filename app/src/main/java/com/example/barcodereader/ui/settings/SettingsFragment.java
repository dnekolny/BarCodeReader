package com.example.barcodereader.ui.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.example.barcodereader.R;
import com.example.barcodereader.helpers.DataAccess;
import com.example.barcodereader.model.CameraItem;
import com.example.barcodereader.model.Setting;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class SettingsFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private Spinner spinnerCamera;
    private Switch switchSound, switchVibration, switchChromeTabs, switchUseUrl;
    private TextView tvVersion;
    private ListView listViewSearch;

    private SharedPreferences sharedPref;
    private Setting setting;
    private List<CameraItem> cameras;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_settings, container, false);

        sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        try {
            setting = DataAccess.getSettingByModule(sharedPref.getLong(getString(R.string.sp_active_module_id), -1), getContext());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        spinnerCamera = root.findViewById(R.id.spinnerCamera);
        switchSound = root.findViewById(R.id.switchSound);
        switchVibration = root.findViewById(R.id.switchVibration);
        switchChromeTabs = root.findViewById(R.id.switchChromeTabs);
        switchUseUrl = root.findViewById(R.id.switchUrl);
        tvVersion = root.findViewById(R.id.textViewAppVersion);
        listViewSearch = root.findViewById(R.id.listViewSettingSearch);

        switchSound.setOnClickListener(this);
        switchVibration.setOnClickListener(this);
        switchChromeTabs.setOnClickListener(this);
        switchUseUrl.setOnClickListener(this);

        loadCameras();
        refreshUi();

        return root;
    }

    private void refreshUi(){
        //CAMERA
        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, cameras);
        spinnerCamera.setAdapter(adapter);
        spinnerCamera.setSelection(adapter.getPosition(CameraItem.getCameraByIdOrFirst(setting.getIdCamera(), cameras)));
        spinnerCamera.setOnItemSelectedListener(this);

        //SWITCHES
        switchSound.setChecked(setting.isSound());
        switchVibration.setChecked(setting.isVibration());
        switchChromeTabs.setChecked(setting.isChromeTabs());
        switchUseUrl.setChecked(setting.isUseUrl());

        //LIST VIEW SEARCH
        listViewSearch.setAdapter(new SettingSearchListAdapter(getContext(), 0, setting.getSearchSettings()));

        //VERSION
        try {
            PackageInfo pInfo = getContext().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
            tvVersion.setText(pInfo.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadCameras() {
        cameras = new ArrayList<>();
        CameraManager cameraManager = (CameraManager) getContext().getSystemService(Context.CAMERA_SERVICE);

        try {
            String[] cameraIds = cameraManager.getCameraIdList();
            for (String cameraId :
                    cameraIds) {
                CameraCharacteristics characteristics = cameraManager.getCameraCharacteristics(cameraId);

                String camName = "Camera ID: " + cameraId;
                Integer facing = characteristics.get(CameraCharacteristics.LENS_FACING);
                if (facing != null) {
                    if (facing == CameraCharacteristics.LENS_FACING_FRONT) {
                        camName = "Front Facing";
                    } else if (facing == CameraCharacteristics.LENS_FACING_BACK) {
                        camName = "Back Facing";
                    }
                }

                cameras.add(new CameraItem(cameraId, camName));
            }

        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    //SWITCH click
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.switchSound:
                setting.setSound(switchSound.isChecked());
                break;
            case R.id.switchVibration:
                setting.setVibration(switchVibration.isChecked());
                break;
            case R.id.switchChromeTabs:
                setting.setChromeTabs(switchChromeTabs.isChecked());
                break;
            case R.id.switchUrl:
                setting.setUseUrl(switchUseUrl.isChecked());
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        setting.setIdCamera(cameras.get(position).getId());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //DO NOTHING
    }

    @Override
    public void onStop() {
        try {
            DataAccess.saveSetting(setting, getContext());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        super.onStop();
    }
}