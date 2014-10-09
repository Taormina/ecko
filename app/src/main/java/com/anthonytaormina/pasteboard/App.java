package com.anthonytaormina.pasteboard;

import android.app.Application;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Prefs.init(this);
    }
}
