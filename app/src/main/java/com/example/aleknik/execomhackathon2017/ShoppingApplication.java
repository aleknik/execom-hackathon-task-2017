package com.example.aleknik.execomhackathon2017;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

import org.androidannotations.annotations.EApplication;

@EApplication
public class ShoppingApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
