package com.script972.clutchclient.ui.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.script972.clutchclient.R;
import com.script972.clutchclient.helpers.PrefHelper;
import com.script972.clutchclient.model.LocationPosition;


public class SettingActivity extends BaseActivity implements View.OnClickListener {

    private Toolbar tollbar;


    private TextView txtCurrentCountry;

    private final int REQUEST_COUNTRY=312;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
    }

    private void initView() {
        initToolbar();
        txtCurrentCountry=findViewById(R.id.current_view_text_view);
        findViewById(R.id.current_location_layout).setOnClickListener(this);
        LocationPosition location = PrefHelper.getLocation(this);
        if(location!=null)
            txtCurrentCountry.setText(location.getCountry());
    }

    private void initToolbar() {
        tollbar = (Toolbar) findViewById(R.id.toolbar);

        tollbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp));
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
}
