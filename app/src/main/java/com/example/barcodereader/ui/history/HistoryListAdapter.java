package com.example.barcodereader.ui.history;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.barcodereader.R;
import com.example.barcodereader.helpers.DateHelper;
import com.example.barcodereader.model.ScanResult;

import java.util.List;

import androidx.annotation.NonNull;

public class HistoryListAdapter extends ArrayAdapter<ScanResult> {


    public HistoryListAdapter(@NonNull Context context, int resource, @NonNull List<ScanResult> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ScanResult item = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview_item_history, parent, false);
        }

        TextView tvValue = (TextView) convertView.findViewById(R.id.tvHistoryResultValue);
        TextView tvDate = (TextView) convertView.findViewById(R.id.tvHistoryResultDate);

        tvValue.setText(item.getValue());
        tvDate.setText(DateHelper.getDate(item.getTimestamp()) + " / " + item.getLatitude() + " / " + item.getLongitude());

        return convertView;
    }
}
