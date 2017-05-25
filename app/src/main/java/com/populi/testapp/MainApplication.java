package com.populi.testapp;

import android.app.Application;

import com.populi.testapp.internal.DataManager;


/**
 * Created by Alexander Gavrikov.
 */

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        DataManager.getInstance().initialize(this);
    }
}
