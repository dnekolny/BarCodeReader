package com.example.barcodereader.ui.modules;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.example.barcodereader.App;
import com.example.barcodereader.R;
import com.example.barcodereader.helpers.FileHelper;
import com.example.barcodereader.ui.history.HistoryListAdapter;
import com.maltaisn.icondialog.IconDialog;
import com.maltaisn.icondialog.IconDialogSettings;
import com.maltaisn.icondialog.data.Icon;
import com.maltaisn.icondialog.pack.IconPack;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.w3c.dom.Text;

import java.util.List;

public class ModulesFragment extends Fragment implements IconDialog.Callback {

    private static final String ICON_DIALOG_TAG = "icon-dialog";

    private SharedPreferences sharedPref;
    private GridView gridView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);

        View root = inflater.inflate(R.layout.fragment_modules, container, false);

        gridView = root.findViewById(R.id.gridViewModules);
        try {
            gridView.setAdapter(new ModulesGridAdapter(getContext(), 0, FileHelper.readModules(getContext())));
        } catch (Exception e) {
            e.printStackTrace();
        }

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tvId = ((TextView) (view.findViewById(R.id.tvModuleId)));
                long moduleId = Long.parseLong(tvId.getText().toString());

                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putLong(getString(R.string.sp_active_module_id), moduleId);
                editor.commit();

                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.navigation_scan);
            }
        });


        //ICONS TEST
        // If dialog is already added to fragment manager, get it. If not, create a new instance.
        IconDialog dialog = (IconDialog) getChildFragmentManager().findFragmentByTag(ICON_DIALOG_TAG);
        final IconDialog iconDialog = dialog != null ? dialog
                : IconDialog.newInstance(new IconDialogSettings.Builder().build());

        Button btn = root.findViewById(R.id.btn_open_dialog);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open icon dialog
                iconDialog.show(getChildFragmentManager(), ICON_DIALOG_TAG);
            }
        });

        return root;
    }

    @Nullable
    @Override
    public IconPack getIconDialogIconPack() {
        return ((App) getActivity().getApplication()).getIconPack();
    }

    @Override
    public void onIconDialogCancelled() {
    }

    @Override
    public void onIconDialogIconsSelected(@NotNull IconDialog iconDialog, @NotNull List<Icon> icons) {
        // Show a toast with the list of selected icon IDs.
        StringBuilder sb = new StringBuilder();
        for (Icon icon : icons) {
            sb.append(icon.getId());
            sb.append(", ");
        }
        sb.delete(sb.length() - 2, sb.length());
        Toast.makeText(getContext(), "Icons selected: " + sb, Toast.LENGTH_SHORT).show();
    }
}