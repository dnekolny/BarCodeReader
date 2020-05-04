package com.example.barcodereader.ui.modules;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ToggleButton;

import com.example.barcodereader.App;
import com.example.barcodereader.R;
import com.example.barcodereader.helpers.FileHelper;
import com.example.barcodereader.model.Module;
import com.maltaisn.icondialog.IconDialog;
import com.maltaisn.icondialog.IconDialogSettings;

import androidx.fragment.app.FragmentManager;

public class ModulesDialogEdit extends Dialog implements android.view.View.OnClickListener {

    private static final String ICON_DIALOG_TAG = "icon-dialog";

    private Activity activity;
    private FragmentManager fragmentManager;
    private Button btnSave, bntCancel;
    private ToggleButton btnFavorite;
    private ImageView imgIcon;
    private EditText editTextName;
    private IconDialog iconDialog;

    private boolean isCreated = false;
    private Module module;

    public ModulesDialogEdit(Activity activity, FragmentManager fragmentManager) {
        super(activity);
        this.activity = activity;
        this.fragmentManager = fragmentManager;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_modul_edit);

        btnSave = (Button) findViewById(R.id.btnDialogModuleSave);
        bntCancel = (Button) findViewById(R.id.btnDialogModuleCancel);
        imgIcon = (ImageView) findViewById(R.id.imageViewDialogModulImage);
        btnFavorite = (ToggleButton) findViewById(R.id.toggleDialogModuleFavorite);
        editTextName = (EditText) findViewById(R.id.editTextDialogModulName);

        isCreated = true;
        refreshUi();

        imgIcon.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        bntCancel.setOnClickListener(this);

        // If dialog is already added to fragment manager, get it. If not, create a new instance.
        IconDialog dialog = (IconDialog) fragmentManager.findFragmentByTag(ICON_DIALOG_TAG);
        iconDialog = dialog != null ? dialog : IconDialog.newInstance(new IconDialogSettings.Builder().build());
    }

    private void refreshUi() {
        if(isCreated && module != null){
            imgIcon.setImageDrawable(((App)activity.getApplication()).getIconDrawable(module.getIconId()));
            imgIcon.setColorFilter(module.getColor());
            editTextName.setText(module.getName());
            btnFavorite.setChecked(module.isFavorite());
        }
    }

    public void setModule(Module module) {
        this.module = module;
        refreshUi();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnDialogModuleSave:
                saveModule();
                dismiss();
                break;
            case R.id.btnDialogModuleCancel:
                dismiss();
                break;
            case R.id.imageViewDialogModulImage:
                // Open icon dialog
                iconDialog.show(fragmentManager, ICON_DIALOG_TAG);
                break;
            default:
                break;
        }
        dismiss();
    }

    public void saveModule(){
        if(isCreated) {
            module.setName(editTextName.getText().toString());
            module.setFavorite(btnFavorite.isChecked());
            //TODO module.setColor();
            //TODO module.setIconId();
            //TODO FileHelper.writeModule(module, getContext());
        }
    }
}