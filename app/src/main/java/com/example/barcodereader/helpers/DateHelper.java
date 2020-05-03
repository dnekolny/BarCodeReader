package com.example.barcodereader.helpers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateHelper {

    public static String getDate(long millis){
        // Creating date format
        DateFormat simple = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.GERMANY);

        // Creating date from milliseconds
        // using Date() constructor
        Date result = new Date(millis);

        return simple.format(result);
    }

}
