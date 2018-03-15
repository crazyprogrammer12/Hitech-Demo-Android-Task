package com.example.crazy.demoapp;

import android.app.Application;
import android.content.Context;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;
import com.facebook.FacebookSdk;
import com.facebook.stetho.Stetho;

/**
 * Created by mecra on 3/14/2018.
 */

public class MyApp extends Application{

    Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        Configuration config=new Configuration.Builder(this).setDatabaseName("demo.db").create();
        ActiveAndroid.initialize(config);
        Stetho.initializeWithDefaults(this);

        FacebookSdk.sdkInitialize(this);
    }
}
