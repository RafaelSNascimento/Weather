package com.example.power.myapplication;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by Power on 15/09/2016.
 */
public class FireApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }

}
