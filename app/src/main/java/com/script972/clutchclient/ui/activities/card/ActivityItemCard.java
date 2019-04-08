package com.script972.clutchclient.ui.activities.card;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.pkmmte.view.CircularImageView;
import com.script972.clutchclient.R;
import com.script972.clutchclient.helpers.DataTransferHelper;
import com.script972.clutchclient.helpers.DialogHelper;
import com.script972.clutchclient.domain.api.model.api.CardItem;
import com.script972.clutchclient.ui.activities.DiscountMapsActivity;
import com.script972.clutchclient.ui.adapters.TabAdapterInfoCard;
import com.script972.clutchclient.ui.fragment.BarcodeFragment;
import com.script972.clutchclient.ui.fragment.InfoFragment;
import com.squareup.picasso.Picasso;


public class ActivityItemCard extends AppCompatActivity implements OnMapReadyCallback {

    //outlets
    private ImageView arrowback;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TextView cardTitle;
    private CircularImageView iconCompany;

    private GoogleMap googleMap;



    //objects
    private CardItem cardItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_card);
        getDataFromIntent();
        initView();
        fillData();


    }

    /**
     * Get cardItem from intent
     */
    private void getDataFromIntent() {
        String json = String.valueOf(getIntent().getExtras().getString("cardItem"));
        this.cardItem = new Gson().fromJson(json, CardItem.class);
    }

    /**
     * Method for initial view
     */
    private void initView() {
        initMaps();
        arrowback = findViewById(R.id.arrowback);
        cardTitle = findViewById(R.id.card_title);
        iconCompany = findViewById(R.id.icon_company);

        arrowback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        initTabView();
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
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        TabAdapterInfoCard adapter = new TabAdapterInfoCard(getSupportFragmentManager());
        BarcodeFragment barcodeFragment = new BarcodeFragment();
        Bundle bundle=new Bundle();
        bundle.putString("number", cardItem.getNumber());
        barcodeFragment.setArguments(bundle);
        adapter.addFragment(barcodeFragment, getResources().getString(R.string.t_barcode));



        InfoFragment infoFragment = new InfoFragment();
        Bundle bundleInfo = new Bundle();
        bundleInfo.putString("backphoto", cardItem.getBackPhoto());
        bundleInfo.putString("facephoto", cardItem.getFacePhoto());
        infoFragment.setArguments(bundle);
        adapter.addFragment(infoFragment, getResources().getString(R.string.t_info));
        viewPager.setAdapter(adapter);
    }


    /**
     * Method for fill data of card
     */
    private void fillData() {
        cardTitle.setText(this.cardItem.getTitle());
        if(this.cardItem.getCompany()!=null && this.cardItem.getCompany().getLogo()!=null) {
            Picasso.get()
                    .load(this.cardItem.getCompany().getLogo())
                    .placeholder(R.drawable.cardtemplate)
                    .error(R.drawable.ic_earth)
                    .into(iconCompany);
        }

    }

    /**
     * Initialize the contents of the Activity's standard options main_toolbar_menu.  You
     * should place your main_toolbar_menu items in to <var>main_toolbar_menu</var>.
     * <p>
     * <p>This is only called once, the first time the options main_toolbar_menu is
     * displayed.  To update the main_toolbar_menu every time it is displayed, see
     * {@link #onPrepareOptionsMenu}.
     * <p>
     * <p>The default implementation populates the main_toolbar_menu with standard system
     * main_toolbar_menu items.  These are placed in the {@link Menu#CATEGORY_SYSTEM} group so that
     * they will be correctly ordered with application-defined main_toolbar_menu items.
     * Deriving classes should always call through to the base implementation.
     * <p>
     * <p>You can safely hold on to <var>main_toolbar_menu</var> (and any items created
     * from it), making modifications to it as desired, until the next
     * time onCreateOptionsMenu() is called.
     * <p>
     * <p>When you add items to the main_toolbar_menu, you can implement the Activity's
     * {@link #onOptionsItemSelected} method to handle them there.
     *
     * @param menu The options main_toolbar_menu in which you place your items.
     * @return You must return true for the main_toolbar_menu to be displayed;
     * if you return false it will not be shown.
     * @see #onPrepareOptionsMenu
     * @see #onOptionsItemSelected
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item_car_toolbar_menu, menu);
        return true;
    }

    /**
     * This hook is called whenever an item in your options main_toolbar_menu is selected.
     * The default implementation simply returns false to have the normal
     * processing happen (calling the item's Runnable or sending a message to
     * its Handler as appropriate).  You can use this method for any items
     * for which you would like to do processing without those other
     * facilities.
     * <p>
     * <p>Derived classes should call through to the base class for it to
     * perform the default main_toolbar_menu handling.</p>
     *
     * @param item The main_toolbar_menu item that was selected.
     * @return boolean Return false to allow normal main_toolbar_menu processing to
     * proceed, true to consume it here.
     * @see #onCreateOptionsMenu
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.shoping_maps: openActivity(); break;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Method wich open Maps Activity
     *
     */
    private void openActivity() {
        Intent intent = new Intent(this, DiscountMapsActivity.class);
        intent.putExtra("cardid", this.cardItem.getId());
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
                        Intent intent = new Intent(ActivityItemCard.this, DiscountMapsActivity.class);
                        intent.putExtra("carditem", DataTransferHelper.convertToJson(cardItem));
                        startActivity(intent);
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
