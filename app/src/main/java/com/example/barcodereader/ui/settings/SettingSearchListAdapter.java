package com.example.barcodereader.ui.settings;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.barcodereader.R;
import com.example.barcodereader.model.BarcodeType;
import com.example.barcodereader.model.SearchSetting;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SettingSearchListAdapter extends BaseAdapter {

    private List<SearchSetting> items;
    private Context context;

    public SettingSearchListAdapter(Context context, List<SearchSetting> items) {
        this.items = items;
        this.context = context;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public SearchSetting getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return items.get(position).getId();
    }

    public List<SearchSetting> getItems(){
        return items;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final SearchSetting item = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.listview_item_setting, parent, false);
        }

        Spinner spinnerCodeType = convertView.findViewById(R.id.spinnerSettingCodeTypes);
        final EditText editTextUrl = convertView.findViewById(R.id.editTextSettingSearchUrl);

        final ArrayAdapter<BarcodeType> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, BarcodeType.values());
        spinnerCodeType.setAdapter(adapter);
        spinnerCodeType.setSelection(adapter.getPosition(item.getCodeType()));

        if(item.isDefault()){
            //disable first search setting
            spinnerCodeType.setEnabled(false);
        }

        //SPINNER
        spinnerCodeType.setSelection(adapter.getPosition(item.getCodeType()));
        spinnerCodeType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                item.setCodeType(adapter.getItem(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //EDIT TEXT
        editTextUrl.setText(item.getUrl());
        editTextUrl.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                item.setUrl(editTextUrl.getText().toString());
            }
        });

        return convertView;
    }
}
