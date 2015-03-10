//package com.anthonytaormina.pasteboard;
//
//import android.content.Context;
//import android.content.SharedPreferences;
//import android.preference.PreferenceManager;
//
//public class Prefs {
//
//    private static SharedPreferences prefs;
//
//    private class Keys {
//        public static final String ENDPOINT = "ENDPOINT";
//    }
//
//    public static void init(Context context) {
//        prefs = PreferenceManager.getDefaultSharedPreferences(context);
//    }
//
//    public static String getEndpoint({
//        return prefs.getString(Keys.ENDPOINT, defaultUrl);
//    }
//
//    public static void setEndpoint(String endpoint) {
//        prefs.edit().putString(Keys.ENDPOINT, endpoint);
//    }
//
//}
