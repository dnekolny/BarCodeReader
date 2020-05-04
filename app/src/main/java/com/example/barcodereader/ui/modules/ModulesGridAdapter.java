package com.example.barcodereader.ui.modules;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.barcodereader.App;
import com.example.barcodereader.R;
import com.example.barcodereader.model.Module;
import com.maltaisn.icondialog.data.Icon;

import java.util.List;

import androidx.annotation.NonNull;

public class ModulesGridAdapter extends ArrayAdapter<Module> {


    public ModulesGridAdapter(@NonNull Context context, int resource, @NonNull List<Module> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Module item = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.gridview_item_modules, parent, false);
        }

        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageViewModuleIcon);
        TextView tvName = (TextView) convertView.findViewById(R.id.tvModuleName);
        TextView tvId = (TextView) convertView.findViewById(R.id.tvModuleId);

        Icon icon = ((App) App.getApp()).getIconPack().getIcon(item.getIconId());
        if (icon != null) {
            imageView.setImageDrawable(icon.getDrawable());
        }

        imageView.setColorFilter(item.getColor());
        tvName.setText(item.getName());
        tvId.setText(String.valueOf(item.getId()));
        return convertView;
    }
}
