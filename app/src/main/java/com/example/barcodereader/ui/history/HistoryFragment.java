package com.example.barcodereader.ui.history;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.barcodereader.R;
import com.example.barcodereader.helpers.DataAccess;
import com.example.barcodereader.model.ScanResult;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HistoryFragment extends Fragment {


    private ListView listView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_history, container, false);

        listView = root.findViewById(R.id.listViewHistory);
        try {
            List<ScanResult> results = DataAccess.getResults(getContext());
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

        return root;
    }
}