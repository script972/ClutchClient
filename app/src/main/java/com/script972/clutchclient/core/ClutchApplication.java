package com.script972.clutchclient.core;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.artlite.bslibrary.core.BSInstance;
import com.script972.clutchclient.BuildConfig;
import com.script972.clutchclient.api.NetworkLoggingSettings;
import com.script972.clutchclient.ui.activitys.BaseActivity;


/**
 * CLass which provide the application functional
 */
public final class ClutchApplication extends MultiDexApplication {

    private static BaseActivity currentActivity;
    private static ClutchApplication application;

    /**
     * Method which provide the action when the base {@link Context} was attached
     *
     * @param base instance of the {@link Context}
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    /**
     * Method which provide the action when the application created
     */
    @Override
    public void onCreate() {
        super.onCreate();
        this.onInitLibraries();
        application=this;


        initDebugMode();

    }

    private void initDebugMode() {
        if(BuildConfig.DEBUG) {
            NetworkLoggingSettings.init(this);

        }
    }

    /**
     * Method which provide the init of the libraries
     */
    protected void onInitLibraries() {
        BSInstance.init(this);
    }

    public static ClutchApplication getApplication() {
        return application;
    }

    public static BaseActivity getCurrentActivity() {
        return currentActivity;
    }

}
