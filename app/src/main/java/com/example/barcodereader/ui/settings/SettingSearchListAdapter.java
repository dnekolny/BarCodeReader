package com.example.barcodereader.ui.settings;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.barcodereader.R;
import com.example.barcodereader.helpers.DataAccess;
import com.example.barcodereader.model.BarcodeType;
import com.example.barcodereader.model.SearchSetting;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SettingSearchListAdapter extends BaseAdapter {

    public interface SettingSearchListAdapterHandler {
        void onItemChanged();
    }

    private List<SearchSetting> items;
    private Context context;
    private SettingSearchListAdapter adapter;
    private SettingSearchListAdapterHandler handler;

    public SettingSearchListAdapter(Context context, List<SearchSetting> items, SettingSearchListAdapterHandler handler) {
        this.items = items;
        this.context = context;
        this.handler = handler;
        adapter = this;
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

        /*for (int i = 0; i < getCount(); i++) {
            getView(i, )
        }*/

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
        ImageView imgDelete = convertView.findViewById(R.id.btnDeleteSearchSetting);

        final ArrayAdapter<BarcodeType> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, BarcodeType.values());
        spinnerCodeType.setAdapter(adapter);
        spinnerCodeType.setSelection(adapter.getPosition(item.getCodeType()));

        if(item.isDefault()){
            //disable first search setting
            imgDelete.setVisibility(View.GONE);
            spinnerCodeType.setEnabled(false);
        }

        //SPINNER
        spinnerCodeType.setSelection(adapter.getPosition(item.getCodeType()));
        spinnerCodeType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                item.setCodeType(adapter.getItem(position));
                handler.onItemChanged();
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
                handler.onItemChanged();
            }
        });

        //DELETE
        if(!item.isDefault()) {
            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case DialogInterface.BUTTON_POSITIVE:
                                    items.remove(item);
                                    notifyDataSetChanged();
                                    handler.onItemChanged();
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Are you sure you want to delete this search setting ?").setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();
                }
            });
        }

        return convertView;
    }
}
