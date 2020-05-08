package com.example.barcodereader.ui.history;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.barcodereader.R;
import com.example.barcodereader.helpers.DataHelper;
import com.example.barcodereader.model.IconList;
import com.example.barcodereader.model.ScanResult;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;

public class HistoryListAdapter extends ArrayAdapter<ScanResult> {

    private IconList iconList;

    public HistoryListAdapter(@NonNull Context context, int resource, @NonNull List<ScanResult> objects) {
        super(context, resource, objects);
        iconList = IconList.getInstance(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ScanResult item = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview_item_history, parent, false);
        }

        TextView tvValue = convertView.findViewById(R.id.tvHistoryResultValue);
        TextView tvDate = convertView.findViewById(R.id.tvHistoryResultDate);
        ImageView imgIcon = convertView.findViewById(R.id.imageViewHistoryIcon);
        ImageView btnCopy = convertView.findViewById(R.id.btnHistoryCopy);
        ImageView btnLink = convertView.findViewById(R.id.btnHistoryLink);

        btnCopy.setColorFilter(ContextCompat.getColor(getContext(), R.color.colorTextLight));
        btnLink.setColorFilter(ContextCompat.getColor(getContext(), R.color.colorAccent));

        //COPY
        btnCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager)getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Bar code value", item.getValue());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getContext(), "Value copied to clipboard", Toast.LENGTH_SHORT).show();
            }
        });

        //LINK
        btnLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                builder.setToolbarColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
                CustomTabsIntent intent = builder.build();
                intent.launchUrl(getContext(), Uri.parse(item.getUrl()));
            }
        });

        tvValue.setText(item.getValue());
        tvDate.setText(DataHelper.getDate(item.getTimestamp()));

        imgIcon.setImageDrawable(iconList.getIconDrawable(item.getIconId()));
        imgIcon.setColorFilter(item.getColor());

        return convertView;
    }
}
