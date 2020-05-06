package com.example.barcodereader.helpers;

import android.content.Context;

import com.example.barcodereader.model.Module;
import com.example.barcodereader.model.ScanResult;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import androidx.core.content.ContextCompat;

public class DataAccess {

    private static final String RESULTS_FILE_NAME = "results.obj";
    private static final String MODULES_FILE_NAME = "modules.obj";

    /**RESULTS*/
    public static void saveResults(List<ScanResult> results, Context context) throws IOException {

        try (FileOutputStream fos = context.openFileOutput(RESULTS_FILE_NAME, Context.MODE_PRIVATE)) {
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(results);
            oos.close();
        }
    }

    public static List<ScanResult> getResults(Context context) throws IOException, ClassNotFoundException {

        try {
            FileInputStream fis = context.openFileInput(RESULTS_FILE_NAME);
            ObjectInputStream ois = new ObjectInputStream(fis);
            List<ScanResult> results = (List<ScanResult>) ois.readObject();
            ois.close();
            return results;
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        } catch (InvalidClassException e) { //data v souboru neodpovídají objektu
            removeResults(context);
            return new ArrayList<>();
        }
    }

    public static void saveResult(ScanResult result, Context context) throws IOException, ClassNotFoundException {
        List<ScanResult> results = getResults(context);
        results.add(result);
        saveResults(results, context);
    }

    public static boolean removeResults(Context context) {
        return context.deleteFile(RESULTS_FILE_NAME);
    }


    /**MODULES*/
    public static void saveModules(List<Module> modules, Context context) throws IOException {

        try (FileOutputStream fos = context.openFileOutput(MODULES_FILE_NAME, Context.MODE_PRIVATE)) {
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(modules);
            oos.close();
        }
    }

    public static List<Module> getModules(Context context) throws IOException, ClassNotFoundException {

        //return getTestModules();
        try {
            FileInputStream fis = context.openFileInput(MODULES_FILE_NAME);
            ObjectInputStream ois = new ObjectInputStream(fis);
            List<Module> modules = (List<Module>) ois.readObject();
            if(modules.size() < 1){
                modules = saveDefaultModules(context);
            }
            ois.close();
            return modules;
        } catch (FileNotFoundException e) {
            return saveDefaultModules(context);
        } catch (InvalidClassException e) { //data v souboru neodpovídají objektu
            removeModules(context);
            return new ArrayList<>();
        }
    }

    public static List<Module> getModulesByFavorites(boolean favorite, Context context) throws IOException, ClassNotFoundException {
        List<Module> all = getModules(context);
        List<Module> modules = new ArrayList<>();

        for (Module m :
                all) {
            if(m.isFavorite() == favorite){
                modules.add(m);
            }
        }
        return modules;
    }

    ///Save or Update module
    public static void saveModule(Module module, Context context) throws IOException, ClassNotFoundException {
        List<Module> modules = getModules(context);

        boolean mFind = false;
        for (int i = 0; i < modules.size() && !mFind; i++) {
            Module m = modules.get(i);
            if(m.getId() == module.getId()){
                modules.set(i, module);
                mFind = true;
            }
        }
        if(!mFind){
            modules.add(module);
        }
        saveModules(modules, context);
    }

    public static void removeModule(long id, Context context) throws IOException, ClassNotFoundException {
        List<Module> modules = getModules(context);

        boolean mFind = false;
        for (int i = 0; i < modules.size() && !mFind; i++) {
            Module m = modules.get(i);
            if(m.getId() == id){
                modules.remove(i);
                mFind = true;
            }
        }
        saveModules(modules, context);
    }

    public static boolean removeModules(Context context) {
        return context.deleteFile(MODULES_FILE_NAME);
    }

    private static List<Module> saveDefaultModules(Context context) throws IOException {
        List<Module> modules = Module.getDefaultModules();
        saveModules(modules, context);
        return modules;
    }

    private static List<Module> getTestModules() {
        List<Module> modules = new ArrayList<>();

        modules.add(new Module(1, "MODULE1", 0, 0xFFFFFFFF, false));
        modules.add(new Module(2, "MODULE2", 1, 0xFFFF0000, true));
        modules.add(new Module(3, "MODULE3", 15, 0xFF00FF00, false));
        modules.add(new Module(4, "MODULE4", 42, 0xFF0000FF, true));
        modules.add(new Module(5, "MODULE5", 142, 0xFFFFFFFF, false));
        modules.add(new Module(6, "MODULE6", 202, 0xFFFFFFFF, false));
        modules.add(new Module(7, "MODULE7", 201, 0xFFFFFFFF, false));

        return modules;
    }
}