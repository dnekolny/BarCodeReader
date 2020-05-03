package com.example.barcodereader.ui.history;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.barcodereader.R;
import com.example.barcodereader.helpers.FileHelper;

import java.io.IOException;

public class HistoryFragment extends Fragment {

    private HistoryViewModel historyViewModel;

    private ListView listView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        historyViewModel =
                ViewModelProviders.of(this).get(HistoryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_history, container, false);
        //final TextView textView = root.findViewById(R.id.text_history);
        historyViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });

        listView = root.findViewById(R.id.historyListView);
        try {
            listView.setAdapter(new HistoryListAdapter(getContext(), 0, FileHelper.readResults(getContext())));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return root;
    }
}