package com.example.barcodereader.helpers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class DataHelper {

    public static String getDate(long millis){
        // Creating date format
        DateFormat simple = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.GERMANY);

        // Creating date from milliseconds
        // using Date() constructor
        Date result = new Date(millis);

        return simple.format(result);
    }

    public static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

}
