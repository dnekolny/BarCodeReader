package com.example.barcodereader.ui.history;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Switch;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.barcodereader.MapsActivity;
import com.example.barcodereader.R;
import com.example.barcodereader.helpers.DataAccess;
import com.example.barcodereader.model.ScanResult;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HistoryFragment extends Fragment {

    private SharedPreferences sharedPref;
    private Switch switchCurrentModule;
    private ListView listView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        getActivity().setTitle(getString(R.string.title_history));

        View root = inflater.inflate(R.layout.fragment_history, container, false);
        setHasOptionsMenu(true);

        switchCurrentModule = root.findViewById(R.id.switchHistoryCurrentModule);
        listView = root.findViewById(R.id.listViewHistory);

        boolean currentModule = sharedPref.getBoolean(getString(R.string.sp_history_current_module), false);
        refreshListView(currentModule);

        switchCurrentModule.setChecked(currentModule);
        switchCurrentModule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putBoolean(getString(R.string.sp_history_current_module), switchCurrentModule.isChecked());
                editor.apply();
                refreshListView(switchCurrentModule.isChecked());
            }
        });

        return root;
    }

    private void refreshListView(boolean currentModule) {
        try {
            List<ScanResult> results;

            if(currentModule){
                long currentModuleId = sharedPref.getLong(getString(R.string.sp_active_module_id), -1);
                results = DataAccess.getResultsByModuleId(currentModuleId, getContext());
            }
            else {
                results = DataAccess.getResults(getContext());
            }

            //sort by date
            Collections.sort(results, new Comparator<ScanResult>() {
                @Override
                public int compare(ScanResult s1, ScanResult s2) {
                    return Long.compare(s2.getTimestamp(), s1.getTimestamp());
                }
            });

            listView.setAdapter(new HistoryListAdapter(getContext(), 0, results));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.history_menu_buttons, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.action_history_map){
            Intent map = new Intent(getActivity(), MapsActivity.class);
            startActivity(map);
        }
        return super.onOptionsItemSelected(item);
    }
}