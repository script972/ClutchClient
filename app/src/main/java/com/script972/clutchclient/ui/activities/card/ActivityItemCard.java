package com.script972.clutchclient.ui.activities.card;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.pkmmte.view.CircularImageView;
import com.script972.clutchclient.R;
import com.script972.clutchclient.helpers.DataTransferHelper;
import com.script972.clutchclient.helpers.DialogHelper;
import com.script972.clutchclient.helpers.IntentHelpers;
import com.script972.clutchclient.ui.activities.BaseActivity;
import com.script972.clutchclient.ui.activities.DiscountMapsActivity;
import com.script972.clutchclient.ui.adapters.TabAdapterInfoCard;
import com.script972.clutchclient.ui.fragment.BarcodeFragment;
import com.script972.clutchclient.ui.fragment.InfoFragment;
import com.script972.clutchclient.ui.model.CardItem;
import com.script972.clutchclient.viewmodels.AddCardViewModel;


public class ActivityItemCard extends BaseActivity implements OnMapReadyCallback {

    //outlets
    private ImageView arrowback;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TextView cardTitle;
    private CircularImageView iconCompany;

    private GoogleMap googleMap;


    private AddCardViewModel viewModel;
    private CardItem globalItemCard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_card);
        viewModel = ViewModelProviders.of(this).get(AddCardViewModel.class);
        initView();
        getDataFromIntent();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item_card_toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_update:
                IntentHelpers.INSTANCE.pushAddCardActivity(ActivityItemCard.this, globalItemCard);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initViewModel(long cardId) {
        viewModel.observeLiveData().observe(this, pair -> {
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                IntentHelpers.INSTANCE.pushBarcodeLandScape(this, pair.second.getCardNumber());
                return;
            }
            globalItemCard = pair.second;
            fillData(globalItemCard);
        });
        viewModel.findOneCardById(cardId);
    }

    /**
     * Get cardItem from intent
     */
    private void getDataFromIntent() {
        long cardId = getIntent().getExtras().getLong(IntentHelpers.INSTANCE.getCARD_ITEM());
        initViewModel(cardId);
        //this.cardItem = new Gson().fromJson(json, CardItem.class);
    }

    /**
     * Method for initial view
     */
    private void initView() {
        initToolbar();
        arrowback = findViewById(R.id.arrowback);
        cardTitle = findViewById(R.id.card_title);
        iconCompany = findViewById(R.id.icon_company);
        arrowback.setOnClickListener(v -> onBackPressed());
        initTabView();
        initMaps();
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back));
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> super.onBackPressed());

    }

    private void initMaps() {
        SupportMapFragment maps = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        maps.getMapAsync(this);
    }

    /**
     * Method for initial tab view
     */
    private void initTabView() {
        viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        TabAdapterInfoCard adapter = new TabAdapterInfoCard(getSupportFragmentManager());
        adapter.addFragment(BarcodeFragment.getInstance(), getResources().getString(R.string.t_barcode));

        adapter.addFragment(InfoFragment.Companion.instance(), getResources().getString(R.string.t_info));
        viewPager.setAdapter(adapter);
    }


    /**
     * Method for fill data of card
     *
     * @param cardItem
     */
    private void fillData(CardItem cardItem) {
        cardTitle.setText(cardItem.getTitle() != null ? cardItem.getTitle() : "");
        cardTitle.setVisibility((cardItem.getTitle() != null && !cardItem.getTitle().isEmpty()) ?
                View.VISIBLE : View.GONE);
        Bundle bundle = new Bundle();
        bundle.putString("number", cardItem.getCardNumber());
        BarcodeFragment.getInstance().setArguments(bundle);

        Bundle bundleTwo = new Bundle();
        bundleTwo.putString("cardItem", DataTransferHelper.convertToJson(cardItem));
        InfoFragment.Companion.instance().setArguments(bundleTwo);



    }



    /**
     * Method wich open Maps Activity
     */
    private void openActivity() {
        Intent intent = new Intent(this, DiscountMapsActivity.class);
        // intent.putExtra("cardid", this.cardItem.get());
        startActivity(intent);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.getUiSettings().setScrollGesturesEnabled(false);
        googleMap.getUiSettings().setZoomControlsEnabled(false);
        googleMap.getUiSettings().setZoomGesturesEnabled(false);
        googleMap.getUiSettings().setRotateGesturesEnabled(false);
        googleMap.getUiSettings().setCompassEnabled(false);

        LatLng sydney = new LatLng(-34, 151);
        googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13.0f));

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                openAgreeWindow();
            }
        });
    }

    private void openAgreeWindow() {
        AlertDialog.Builder builder = DialogHelper.getAlertDialogBuilder(this);
        builder.setTitle(getResources().getString(R.string.t_open_window))
                .setMessage(getResources().getString(R.string.t_agree_open))
                //.setIcon(R.drawable.ic_android_cat)
                .setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.b_yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       /* Intent intent = new Intent(ActivityItemCard.this, DiscountMapsActivity.class);
                        intent.putExtra("carditem", DataTransferHelper.convertToJson(cardItem));
                        startActivity(intent);*/
                    }
                })
                .setNegativeButton(getResources().getString(R.string.b_no),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
