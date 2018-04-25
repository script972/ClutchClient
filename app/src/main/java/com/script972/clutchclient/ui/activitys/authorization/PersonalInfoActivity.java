package com.script972.clutchclient.ui.activitys.authorization;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.script972.clutchclient.R;
import com.script972.clutchclient.ui.activitys.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PersonalInfoActivity extends AppCompatActivity {

    //outlets
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_step2);
        ButterKnife.bind(this);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity();
            }
        });
    }

    /**
     * Method wich open new Activity
     */
    private void mainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


    /**
     * Take care of popping the fragment back stack or finishing the activity
     * as appropriate.
     */
    @Override
    public void onBackPressed() {
        this.mainActivity();
    }

    /**
     * Listener click
     */
    private final View.OnClickListener clicker=new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

}
