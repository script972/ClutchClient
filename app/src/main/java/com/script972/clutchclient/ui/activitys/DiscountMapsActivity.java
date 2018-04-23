package com.script972.clutchclient.ui.activitys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


import com.google.android.gms.maps.SupportMapFragment;
import com.script972.clutchclient.R;
import com.script972.clutchclient.controller.DiscountMapsController;


public class DiscountMapsActivity extends AppCompatActivity {

    private SupportMapFragment mapFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount_maps);
        initView();

        DiscountMapsController mapsG=new DiscountMapsController(this, mapFragment);
    }

    private void initView() {
        //Google
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
    }
}
