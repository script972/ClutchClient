package com.script972.clutchclient.mvp.contracts;


import android.location.LocationListener;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.script972.clutchclient.domain.api.model.api.Company;

public interface MapsContract {

    interface Presenter extends OnMapReadyCallback,
            GoogleMap.OnCameraMoveStartedListener,
            GoogleMap.OnCameraMoveListener,
            GoogleMap.OnCameraIdleListener,
            GoogleMap.OnCameraMoveCanceledListener,
            LocationListener
    {

        /**
         * Method wich focus on comapany marker on the map
         *
         * @param company
         */
        void focusOnCompany(Company company);
    }

    interface View{

        void onMapReady();

        /**
         * Method wich open gps on window
         *
         */
        void askGPSOn();

        /**
         * Click on the marker map, focus on current company in the list
         *
         * @param marker - clicker
         */
        void controllClickOnMapsMarker(Company marker);
    }


}
