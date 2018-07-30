package com.script972.clutchclient.ui.activitys;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;


import com.google.android.gms.maps.SupportMapFragment;
import com.script972.clutchclient.R;
import com.script972.clutchclient.callbacks.GeoListCallbacks;
import com.script972.clutchclient.helpers.DataTransferHelper;
import com.script972.clutchclient.helpers.DialogHelper;
import com.script972.clutchclient.model.api.CardItem;
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

    private GeoDiscountView geoDiscountView;

    private Toolbar toolbar;

    //presenters
    private MapsPresenterImpl mapsPresenter;

    private CardItem cardItem;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount_maps);
        initView();

        getDataFromIntent();

        //TODO get cardid from intent.getExtras

    }

    /**
     * Method wich init outlets
     */
    private void initView() {
        initToolbar();
        geoDiscountView=findViewById(R.id.geo_discount_view);
        //Google
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        this.mapsPresenter = new MapsPresenterImpl(mapFragment, this);
        geoDiscountView.setCallbacks(callbacksGeoList);
    }

    /**
     * Method wich init toolbar
     */
    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setTitle(R.string.toolbar_discount_map);
    }


    @Override
    public void onMapReady() {
        geoDiscountView.onMapReady();
    }

    /**
     * Method wich open gps on window
     */
    @Override
    public void askGPSOn() {
        DialogHelper.getGpsDialog(this).show();
    }

    /**
     * Click on the marker map, focus on current company in the list
     *
     * @param marker - clicker
     */
    @Override
    public void controllClickOnMapsMarker(Company marker) {
        geoDiscountView.companyFocus(marker);
    }

    public void getDataFromIntent() {
        if(getIntent().getExtras()!=null && getIntent().getExtras().getString("cardItem") != null) {
            this.cardItem = (CardItem) DataTransferHelper.convertFromJson(CardItem.class,  getIntent().getExtras().getString("cardItem"));
        }

    }



    GeoListCallbacks callbacksGeoList=new GeoListCallbacks() {
        @Override
        public void returnWorkerList(List<Company> companyList) {
            mapsPresenter.fillDiscountPoints(companyList);
        }

        /**
         * Method wich call in click on item geo list company
         *
         * @param company
         */
        @Override
        public void chooseItemInList(Company company) {
            mapsPresenter.focusOnCompany(company);
        }
    };
}
