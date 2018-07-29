package com.script972.clutchclient.helpers;


import com.facebook.stetho.Stetho;
import com.script972.clutchclient.core.CurrentApplication;

public class DebugInfoInitializer {

    /**
     * Method wich config Stetho
     *
     * @param appContext
     */
    public static void configNetworkAnalyzer(CurrentApplication appContext) {
        Stetho.initialize(
                Stetho.newInitializerBuilder(appContext)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(appContext))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(appContext))
                        .build());
    }


}
