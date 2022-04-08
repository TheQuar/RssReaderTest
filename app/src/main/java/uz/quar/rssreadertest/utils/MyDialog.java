package uz.quar.rssreadertest.utils;

import android.content.Context;

public class MyDialog {

    public static void show(Context context, String title, String message) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", null);
        builder.show();
    }
}
