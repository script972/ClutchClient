package com.script972.clutchclient.mvp.contracts;


import android.location.LocationListener;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

public interface MapsContract {

    interface Presenter extends OnMapReadyCallback,
            GoogleMap.OnCameraMoveStartedListener,
            GoogleMap.OnCameraMoveListener,
            GoogleMap.OnCameraIdleListener,
            GoogleMap.OnCameraMoveCanceledListener,
            LocationListener
    {


    }

    interface View{

        void onMapReady();

    }


}
