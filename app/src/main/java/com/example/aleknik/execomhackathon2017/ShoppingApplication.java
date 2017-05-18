package com.example.aleknik.execomhackathon2017;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.stetho.Stetho;

import org.androidannotations.annotations.EApplication;

@EApplication
public class ShoppingApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(this)
                .setDownsampleEnabled(true).build();

        Fresco.initialize(this, config);
        Stetho.initializeWithDefaults(this);
    }
}
