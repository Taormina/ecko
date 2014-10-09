package com.anthonytaormina.pasteboard;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;

public class Prefs {

    private static String defaultUrl;
    private static SharedPreferences prefs;

    private class Keys {
        public static final String LINK = "LINK";
    }

    public static void init(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        defaultUrl = context.getResources().getString(R.string.default_link);
    }

    public static String getLink() {
        return prefs.getString(Keys.LINK, defaultUrl);
    }

    public static void setLink(String link) {
        prefs.edit().putString(Keys.LINK, link).apply();
    }

}
