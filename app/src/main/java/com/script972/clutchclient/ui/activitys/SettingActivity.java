package com.script972.clutchclient.ui.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.script972.clutchclient.R;
import com.script972.clutchclient.core.ClutchApplication;
import com.script972.clutchclient.helpers.PrefHelper;
import com.script972.clutchclient.model.LocationPosition;


public class SettingActivity extends BaseActivity implements View.OnClickListener {

    //outlets
    private Toolbar tollbar;
    private TextView txtCurrentCountry;
    private LinearLayout displayLayout;
    private TextView displayCardStatus;

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

    /**
     * Method for init views
     */
    private void initView() {
        initToolbar();
        initDisplayCard();

        txtCurrentCountry=findViewById(R.id.current_country_text_view);
        findViewById(R.id.current_location_layout).setOnClickListener(this);
        LocationPosition location = PrefHelper.getLocation(this);
        if(location!=null)
            txtCurrentCountry.setText(location.getCountry());

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
        displayLayout.setOnClickListener(displayCardView);
    }

    private void initToolbar() {
        tollbar = (Toolbar) findViewById(R.id.toolbar);

        tollbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back));
        tollbar.setNavigationOnClickListener(tollbarClicker);
        tollbar.setTitle(getResources().getString(R.string.toolbar_settings));
    }

    /**
     * Method wich check validation data
     */
    private boolean validationData() {
        return true;
    }

    /**
     * Method wicg save new Settings
     */
    private void saveData() {

    }

    //callbacks
    View.OnClickListener tollbarClicker = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (validationData()){
                saveData();
                onBackPressed();
            }
        }
    };


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.current_location_layout:{
                openCountryLocation();
                break;
            }
        }
    }

    /**
     * Method  wich open Select Country Activity
     */
    private void openCountryLocation() {
        Intent intent=new Intent(this, SettingCountryActivity.class);
        startActivityForResult(intent,REQUEST_COUNTRY);
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



    //callbacks

    /**
     * Callbacks controll clicker visible of card
     */
    private View.OnClickListener displayCardView = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(PrefHelper.getDisplayCardView(getApplicationContext())==1){
                PrefHelper.setDisplayCardView(getApplicationContext(), 2);
                displayCardStatus.setText(getResources().getString(R.string.txt_card_view_row));
            } else  if(PrefHelper.getDisplayCardView(getApplicationContext()) == 2){
                PrefHelper.setDisplayCardView(getApplicationContext(), 1);
                displayCardStatus.setText(getResources().getString(R.string.txt_card_view_column));
            }
        }
    };
}
