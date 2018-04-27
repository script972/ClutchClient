package com.script972.clutchclient.ui.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.script972.clutchclient.R;

import butterknife.BindView;

public class ContactPhoneListActivity extends AppCompatActivity {

    @BindView( R.id.toolbar)
    Toolbar tollbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_phone_list);
        tollbar.setNavigationOnClickListener(tollbarClicker);

    }

    //callbacks
    View.OnClickListener tollbarClicker = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };
}
