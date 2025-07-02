package com.example.codedex;

import android.app.Application;
import com.google.firebase.database.FirebaseDatabase;

public class MyAppConfigurations extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Modo offline de Firebase Realtime Database
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}