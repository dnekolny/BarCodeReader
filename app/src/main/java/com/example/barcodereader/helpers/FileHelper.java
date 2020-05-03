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

public class FileHelper {

    private static final String RESULTS_FILE_NAME = "results.obj";

    public static void writeResults(List<ScanResult> results, Context context) throws IOException {

        try (FileOutputStream fos = context.openFileOutput(RESULTS_FILE_NAME, Context.MODE_PRIVATE)) {
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(results);
            oos.close();
        }
    }

    public static List<ScanResult> readResults(Context context) throws IOException, ClassNotFoundException {

        try {
            FileInputStream fis = context.openFileInput(RESULTS_FILE_NAME);
            ObjectInputStream ois = new ObjectInputStream(fis);
            List<ScanResult> results = (List<ScanResult>) ois.readObject();
            ois.close();
            return results;
        }
        catch (FileNotFoundException e){
            return new ArrayList<>();
        }
        catch (InvalidClassException e){ //data v souboru neodpovídají objektu
            removeResults(context);
            return new ArrayList<>();
        }
    }

    public static void writeResult(ScanResult result, Context context) throws IOException, ClassNotFoundException {
        List<ScanResult> results = readResults(context);
        results.add(result);
        writeResults(results, context);
    }

    public static boolean removeResults(Context context){
        return context.deleteFile(RESULTS_FILE_NAME);
    }

    public static List<Module> readModules(Context context) {
        return getTestModules();
    }

    private static List<Module> getTestModules(){
        List<Module> modules = new ArrayList<>();

        modules.add(new Module(1, "MODULE1", 0, 0xFFFFFFFF));
        modules.add(new Module(2, "MODULE2", 1, 0xFFFF0000));
        modules.add(new Module(3, "MODULE3", 15, 0xFF00FF00));
        modules.add(new Module(4, "MODULE4", 42, 0xFF0000FF));
        modules.add(new Module(5, "MODULE5", 142, 0xFFFFFFFF));
        modules.add(new Module(6, "MODULE6", 202, 0xFFFFFFFF));
        modules.add(new Module(7, "MODULE7", 201, 0xFFFFFFFF));

        return modules;
    }
}
