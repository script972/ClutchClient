package com.script972.clutchclient.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.script972.clutchclient.R
import com.script972.clutchclient.helpers.PrefHelper
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.android.synthetic.main.toolbar.*

class SettingActivity : BaseActivity() {

    //outlets
    /*  private TextView txtCurrentCountry;
    private LinearLayout displayLayout;
    private TextView displayCardStatus;

    private View exitLayout;
    private View locationLayout;
  */

    companion object {
        val REQUEST_COUNTRY = 312
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        initView()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_COUNTRY -> print("2")
            else -> {
                print("")
            }
        }
    }

    fun initView() {
        initToolbar()
        initDisplayCard()
        //initLocationLayout();
        //exitLayout = findViewById(R.id.exit_layout);
        //exitLayout.setOnClickListener(clicker);
    }

    /**
     * Method wich init toolbar
     */
    fun initToolbar() {
        toolbar.title = resources.getString(R.string.toolbar_settings)
        toolbar.navigationIcon = resources.getDrawable(R.drawable.ic_arrow_back)
        toolbar.setNavigationOnClickListener({
            /*if (validationData()){
                saveData();
                onBackPressed();
            }*/
            onBackPressed()
        })


    }

    fun initDisplayCard() {

        val numberColomn = PrefHelper.getDisplayCardView(applicationContext)
        if (numberColomn == 1) {
            display_card_status_text_view.text = resources.getString(R.string.txt_card_view_column)
        } else if (numberColomn == 2) {
            display_card_status_text_view.text = resources.getString(R.string.txt_card_view_row)
        }
        display_layout.setOnClickListener {
            if (PrefHelper.getDisplayCardView(applicationContext) == 1) {
                PrefHelper.setDisplayCardView(applicationContext, 2)
                display_card_status_text_view.text = resources.getString(R.string.txt_card_view_row)
            } else if (PrefHelper.getDisplayCardView(applicationContext) == 2) {
                PrefHelper.setDisplayCardView(applicationContext, 1)
                display_card_status_text_view.text = resources.getString(R.string.txt_card_view_column)
            }
        }

    }


}


/*
/**
 * Method for init views
 *//*


    */
    /**
     * init location layout
     *//*
    private void initLocationLayout() {
        locationLayout = findViewById(R.id.current_location_layout);
        locationLayout.setOnClickListener(clicker);
        txtCurrentCountry = findViewById(R.id.current_country_text_view);
        LocationPosition location = PrefHelper.getLocation(this);
        if(location!=null && !location.getCountry().isEmpty()) {
            txtCurrentCountry.setText(location.getCountry());
        }
    }

    */
    *
    /**
     * init card view display
     */



/**
 * Method wich check validation data
 *//*
    private boolean validationData() {
        return true;
    }

    */
/**
 * Method wich save new Settings
 *//*
    private void saveData() {

    }


    */
/**
 * Method  wich open Select Country Activity
 *//*
    private void openCountryLocation() {
        Intent intent=new Intent(this, SettingCountryActivity.class);
        startActivityForResult(intent,REQUEST_COUNTRY);
    }



    */

/**
 * Method for logOut
 *//*
    private void logOut() {
        DialogHelper.logOutDialog(getApplicationContext(), this);
    }



    //callbacks
    */
/**
 * General clicker
 *//*
    private final View.OnClickListener clicker = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.exit_layout: logOut(); break;
                case R.id.display_layout: clickDisplayCard(); break;
                case R.id.current_location_layout: openCountryLocation(); break;

            }
        }
    };*/