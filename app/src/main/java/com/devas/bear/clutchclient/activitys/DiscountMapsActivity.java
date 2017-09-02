package com.devas.bear.clutchclient.activitys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.devas.bear.clutchclient.R;
import com.devas.bear.clutchclient.controller.DiscountMapsController;
import com.google.android.gms.maps.SupportMapFragment;


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
