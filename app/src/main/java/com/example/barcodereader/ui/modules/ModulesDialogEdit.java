package com.example.barcodereader.ui.modules;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ToggleButton;

import com.example.barcodereader.App;
import com.example.barcodereader.R;
import com.example.barcodereader.helpers.DataAccess;
import com.example.barcodereader.model.Module;
import com.maltaisn.icondialog.IconDialog;
import com.maltaisn.icondialog.IconDialogSettings;
import com.skydoves.colorpickerview.ColorPickerView;
import com.skydoves.colorpickerview.listeners.ColorListener;

import java.io.IOException;

import androidx.fragment.app.FragmentManager;

public class ModulesDialogEdit extends Dialog implements android.view.View.OnClickListener {

    public interface ModuleDialogEditHandler {
        void onOkConfirmClick();
        void onCancelConfirmClick();
        void onModuleDelete();
    }

    private static final String ICON_DIALOG_TAG = "icon-dialog";

    private Activity activity;
    private FragmentManager fragmentManager;
    private Button btnSave, bntCancel;
    private ToggleButton btnFavorite;
    private ImageView btnDelete;
    private ImageView imgIcon;
    private EditText editTextName;
    private IconDialog iconDialog;
    private ColorPickerView colorPickerView;

    private boolean isCreated = false;
    private Module module;
    private ModuleDialogEditHandler handler;
    private ModulesDialogEdit thisDialog;

    public ModulesDialogEdit(Activity activity, FragmentManager fragmentManager, ModuleDialogEditHandler handler) {
        super(activity);
        this.activity = activity;
        this.fragmentManager = fragmentManager;
        this.handler = handler;
        thisDialog = this;
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
        btnDelete = (ImageView) findViewById(R.id.imageButtonModuleDelete);
        colorPickerView = (ColorPickerView) findViewById(R.id.colorPickerView);

        isCreated = true;
        refreshUi();

        imgIcon.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        bntCancel.setOnClickListener(this);
        btnDelete.setOnClickListener(this);

        // If dialog is already added to fragment manager, get it. If not, create a new instance.
        IconDialog dialog = (IconDialog) fragmentManager.findFragmentByTag(ICON_DIALOG_TAG);
        iconDialog = dialog != null ? dialog : IconDialog.newInstance(new IconDialogSettings.Builder().build());

        colorPickerView.setColorListener(new ColorListener() {
            @Override
            public void onColorSelected(int color, boolean fromUser) {
                if(fromUser) {
                    module.setColor(color);
                    imgIcon.setColorFilter(module.getColor());
                }
            }
        });
    }

    private void refreshUi() {
        if(isCreated && module != null){
            imgIcon.setImageDrawable(((App)activity.getApplication()).getIconDrawable(module.getIconId()));
            imgIcon.setColorFilter(module.getColor());
            colorPickerView.selectCenter();
            colorPickerView.setPureColor(module.getColor()); //nefunguje
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
                try {
                    saveModule();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                dismiss();
                handler.onOkConfirmClick();
                break;
            case R.id.btnDialogModuleCancel:
                dismiss();
                handler.onCancelConfirmClick();
                break;
            case R.id.imageViewDialogModulImage:
                // Open icon dialog
                iconDialog.show(fragmentManager, ICON_DIALOG_TAG);
                break;
            case R.id.imageButtonModuleDelete:

                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                try {
                                    DataAccess.removeModule(module.getId(), getContext());
                                } catch (IOException | ClassNotFoundException e) {
                                    e.printStackTrace();
                                }
                                handler.onModuleDelete();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                thisDialog.show();
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Are you sure you want to delete module " + module.getName() + "?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();

                break;
            default:
                break;
        }
        dismiss();
    }

    public void saveModule() throws IOException, ClassNotFoundException {
        if(isCreated) {
            module.setName(editTextName.getText().toString());
            module.setFavorite(btnFavorite.isChecked());
            DataAccess.saveModule(module, getContext());
        }
    }
}