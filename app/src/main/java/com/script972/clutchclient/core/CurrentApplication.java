package com.script972.clutchclient.core;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.artlite.bslibrary.core.BSInstance;



/**
 * CLass which provide the application functional
 * Created by dlernatovich on 1/4/18.
 */
public final class CurrentApplication extends MultiDexApplication {

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
    }

    /**
     * Method which provide the init of the libraries
     */
    protected void onInitLibraries() {
        BSInstance.init(this);
        /*SQDatabase.init(this);
        JodaTimeAndroid.init(this);*/
    }
}
