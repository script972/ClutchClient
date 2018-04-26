package com.script972.clutchclient.mvp.contracts;

import com.script972.clutchclient.model.api.Company;
import com.script972.clutchclient.ui.adapters.GeoListAdapter;

import java.util.List;

public interface GeoListContract {

    interface Presenter{

        /**
         * Method wich get list from server
         */
        void getListGeoCompany();
    }

    interface View{

        void onMapReady();

        /**
         * Method wich filling company list
         * @param geoListAdapter
         * @param lists
         */
        void fillListGeoCompany(GeoListAdapter geoListAdapter, List<Company> lists);
    }

}
