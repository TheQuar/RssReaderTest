package uz.quar.rssreadertest.utils;

import android.app.Application;

import com.prof.rssparser.Channel;
import com.prof.rssparser.Parser;

import kotlin.coroutines.Continuation;

public class FeedHelper {

    public static String convertUrlToFormat(String url) {
        String newUrl = url.toLowerCase();
        newUrl = newUrl.replace("https", "http");
        newUrl = newUrl.replace("www.", "");
        if (newUrl.endsWith("/") || newUrl.endsWith("\\")) {
            newUrl = newUrl.substring(0, newUrl.length() - 1);
        }
        return newUrl;
    }


}
