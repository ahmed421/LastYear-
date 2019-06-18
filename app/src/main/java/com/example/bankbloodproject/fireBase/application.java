package com.example.bankbloodproject.fireBase;

import android.app.Application;
import android.content.res.Configuration;

import com.google.firebase.FirebaseApp;

public class application extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
}
