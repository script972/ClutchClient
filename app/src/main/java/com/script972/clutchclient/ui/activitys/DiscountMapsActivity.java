package com.script972.clutchclient.ui.activitys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;


import com.artlite.bslibrary.annotations.FindViewBy;
import com.google.android.gms.maps.SupportMapFragment;
import com.script972.clutchclient.R;
import com.script972.clutchclient.callbacks.GeoListCallbacks;
import com.script972.clutchclient.model.api.Company;
import com.script972.clutchclient.mvp.contracts.MapsContract;
import com.script972.clutchclient.mvp.impl.MapsPresenterImpl;
import com.script972.clutchclient.ui.views.GeoDiscountView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DiscountMapsActivity extends AppCompatActivity implements MapsContract.View {

    //outlets
    private SupportMapFragment mapFragment;

    @BindView(R.id.geo_discount_view)
    GeoDiscountView geoDiscountView;

    @BindView( R.id.toolbar)
    Toolbar tollbar;

    //presenters
    private MapsPresenterImpl mapsPresenter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount_maps);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        //Google
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        this.mapsPresenter = new MapsPresenterImpl(mapFragment, this);
        geoDiscountView.setCallbacks(callbacksGeoList);
        tollbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    GeoListCallbacks callbacksGeoList=new GeoListCallbacks() {
        @Override
        public void returnWorkerList(List<Company> companyList) {
            mapsPresenter.fillDiscountPoints(companyList);
        }
    };


    @Override
    public void onMapReady() {
        geoDiscountView.onMapReady();
    }
}
