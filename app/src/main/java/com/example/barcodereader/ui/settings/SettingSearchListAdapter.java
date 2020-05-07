package com.example.barcodereader.ui.settings;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.barcodereader.R;
import com.example.barcodereader.model.SearchSetting;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SettingSearchListAdapter extends ArrayAdapter<SearchSetting> {

    public SettingSearchListAdapter(@NonNull Context context, int resource, @NonNull List<SearchSetting> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        SearchSetting item = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview_item_history, parent, false);
        }

        Spinner spinnerCodeType = convertView.findViewById(R.id.spinnerSettingCodeTypes);
        EditText editTextUrl = convertView.findViewById(R.id.editTextSettingSearchUrl);

        editTextUrl.setText(item.getUrl());

        /*tvValue.setText(item.getValue());
        tvDate.setText(DataHelper.getDate(item.getTimestamp()) + " / " + item.getLatitude() + " / " + item.getLongitude());*/

        return convertView;
    }
}
