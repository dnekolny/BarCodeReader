package com.example.barcodereader.ui.modules;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.barcodereader.App;
import com.example.barcodereader.R;
import com.example.barcodereader.helpers.FileHelper;
import com.example.barcodereader.model.Module;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.maltaisn.icondialog.IconDialog;
import com.maltaisn.icondialog.data.Icon;
import com.maltaisn.icondialog.pack.IconPack;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class ModulesFragment extends Fragment implements IconDialog.Callback {

    private ModulesViewModel modulesViewModel;
    private SharedPreferences sharedPref;
    private GridView gridView;
    private FloatingActionButton btnAddModule;
    private ModulesDialogEdit dialogEdit;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);

        modulesViewModel = ViewModelProviders.of(this).get(ModulesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_modules, container, false);

        dialogEdit = new ModulesDialogEdit(getActivity(), getChildFragmentManager());
        modulesViewModel.getModule().observe(this, new Observer<Module>() {
            @Override
            public void onChanged(Module module) {
                dialogEdit.setModule(module);
            }
        });

        gridView = root.findViewById(R.id.gridViewModules);
        btnAddModule = root.findViewById(R.id.btnAddModule);

        try {
            gridView.setAdapter(new ModulesGridAdapter(getContext(), 0, FileHelper.readModules(getContext())));
        } catch (Exception e) {
            e.printStackTrace();
        }

        //MODULE CLICK
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tvId = ((TextView) (view.findViewById(R.id.tvModuleId)));
                long moduleId = Long.parseLong(tvId.getText().toString());

                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putLong(getString(R.string.sp_active_module_id), moduleId);
                editor.commit();

                //redirect to scan
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.navigation_scan);
            }
        });

        //MODULE LONG CLICK
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tvId = ((TextView) (view.findViewById(R.id.tvModuleId)));
                long moduleId = Long.parseLong(tvId.getText().toString());

                try {
                    modulesViewModel.setModule(FileHelper.readModules(getContext()).get((int) moduleId - 1));
                    //dialogEdit.setModule(FileHelper.readModules(getContext()).get((int)moduleId - 1));
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                showEditDialog();
                return true;
            }
        });

        //ADD MODULE
        btnAddModule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modulesViewModel.setDefaultModule();
                showEditDialog();
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
        if (icons.size() > 0) {
            Icon icon = icons.get(0);
            modulesViewModel.changeIconId(icon.getId());
        }
        showEditDialog();
    }

    private void showEditDialog() {
        if (dialogEdit == null) {
            dialogEdit = new ModulesDialogEdit(getActivity(), getChildFragmentManager());
        }
        dialogEdit.show();
    }
}