package uz.quar.rssreadertest.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utils {
    public static String getFormattedTimeStamp(String sourceDateString) {
        try {
            if (sourceDateString != null) {
                SimpleDateFormat sourceSdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
                Date date = sourceSdf.parse(sourceDateString);
                if (date != null) {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
                    return sdf.format(date);
                } else return "";
            } else return "";
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }

    }

    public static String getInitialsFromText(String text) {
        if (text.length() < 2) return text;
        String[] words = text.split(" ");
        if (words.length > 1) return words[0].substring(0, 1) + words[1].substring(0, 1);
        else return text.substring(0, 2);
    }


    public static void writeFirstOpen(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(Constants.SHARED_P_NAME, context.MODE_PRIVATE).edit();
        editor.putBoolean(Constants.FIRST_OPEN, true);
        editor.apply();
    }

    public static boolean isFirstOpen(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.SHARED_P_NAME, context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(Constants.FIRST_OPEN, false);
    }





}
