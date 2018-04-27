package com.script972.clutchclient.ui.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.script972.clutchclient.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingActivity extends AppCompatActivity {

    @BindView( R.id.toolbar)
    Toolbar tollbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        tollbar.setNavigationOnClickListener(tollbarClicker);
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



}
