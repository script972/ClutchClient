package com.script972.clutchclient.ui.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.script972.clutchclient.R;
import com.script972.clutchclient.core.ClutchApplication;
import com.script972.clutchclient.helpers.DialogHelper;
import com.script972.clutchclient.helpers.PrefHelper;
import com.script972.clutchclient.model.LocationPosition;


public class SettingActivity extends BaseActivity {

    //outlets
    private Toolbar tollbar;
    private TextView txtCurrentCountry;
    private LinearLayout displayLayout;
    private TextView displayCardStatus;

    private View exitLayout;
    private View locationLayout;

    private final int REQUEST_COUNTRY=312;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        super.initCommonView();
        initView();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case REQUEST_COUNTRY:{

                break;
            }
        }
    }

    /**
     * Method for init views
     */
    private void initView() {
        initToolbar();
        initDisplayCard();
        initLocationLayout();
        exitLayout = findViewById(R.id.exit_layout);
        exitLayout.setOnClickListener(clicker);
    }

    /**
     * init location layout
     */
    private void initLocationLayout() {
        locationLayout = findViewById(R.id.current_location_layout);
        locationLayout.setOnClickListener(clicker);
        txtCurrentCountry = findViewById(R.id.current_country_text_view);
        LocationPosition location = PrefHelper.getLocation(this);
        if(location!=null && !location.getCountry().isEmpty()) {
            txtCurrentCountry.setText(location.getCountry());
        }
    }

    /**
     * init card view display
     */
    private void initDisplayCard() {
        displayLayout=findViewById(R.id.display_layout);
        displayCardStatus = findViewById(R.id.display_card_status_text_view);
        int numberColomn = PrefHelper.getDisplayCardView(getApplicationContext());
        if(numberColomn==1) {
            displayCardStatus.setText(getResources().getString(R.string.txt_card_view_column));
        } else if (numberColomn == 2) {
            displayCardStatus.setText(getResources().getString(R.string.txt_card_view_row));
        }
        displayLayout.setOnClickListener(clicker);
    }

    /**
     * Method wich init toolbar
     */
    private void initToolbar() {
        tollbar = (Toolbar) findViewById(R.id.toolbar);
        tollbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back));
        tollbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validationData()){
                    saveData();
                    onBackPressed();
                }
            }
        });
        tollbar.setTitle(getResources().getString(R.string.toolbar_settings));
    }

    /**
     * Method wich check validation data
     */
    private boolean validationData() {
        return true;
    }

    /**
     * Method wich save new Settings
     */
    private void saveData() {

    }


    /**
     * Method  wich open Select Country Activity
     */
    private void openCountryLocation() {
        Intent intent=new Intent(this, SettingCountryActivity.class);
        startActivityForResult(intent,REQUEST_COUNTRY);
    }



    /**
     * Method wich display card view
     */
    private void clickDisplayCard() {
        if(PrefHelper.getDisplayCardView(getApplicationContext())==1){
            PrefHelper.setDisplayCardView(getApplicationContext(), 2);
            displayCardStatus.setText(getResources().getString(R.string.txt_card_view_row));
        } else  if(PrefHelper.getDisplayCardView(getApplicationContext()) == 2){
            PrefHelper.setDisplayCardView(getApplicationContext(), 1);
            displayCardStatus.setText(getResources().getString(R.string.txt_card_view_column));
        }
    }

    /**
     * Method for logOut
     */
    private void logOut() {
        DialogHelper.logOutDialog(getApplicationContext(), this);
    }



    //callbacks
    /**
     * General clicker
     */
    private final View.OnClickListener clicker = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.exit_layout: logOut(); break;
                case R.id.display_layout: clickDisplayCard(); break;
                case R.id.current_location_layout: openCountryLocation(); break;

            }
        }
    };




}
