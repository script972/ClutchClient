package com.script972.clutchclient.ui.activitys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.script972.clutchclient.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SplashScreenActivity extends AppCompatActivity {


    @BindView(R.id.version_show)
    TextView versionView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ButterKnife.bind(this);
        fillVersion();
        waiter();
    }

    /**
     * Method wich fill version of this app
     */
    private void fillVersion() {
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            versionView.setText(getResources().getString(R.string.txt_version_bottom) + " " + pInfo.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method wich provide freeze window on fix time
     */
    private void waiter() {
        long delay = 3000;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                openNextActivty();
            }
        }, delay);

    }

    /**
     * Method wich decided what activity is next
     */
    private void openNextActivty() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String valueLogin = sharedPref.getString("valueLogin", null);
        String valuePassword = sharedPref.getString("valuePassword", null);
        String valueToken = sharedPref.getString("valueToken", null);
        if (validateToken()) {
            openMainActivity();
        } else {
            if (valueLogin != null && valuePassword != null && getNewToken(valueLogin, valuePassword)) {
                openMainActivity();
            } else {
                Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        }

    }

    /**
     * Method wich help to get new values temporary token
     *
     * @param valueLogin
     * @param valuePassword
     * @return is new token recive
     */
    @NonNull
    private boolean getNewToken(String valueLogin, String valuePassword) {
        //TODO get new token from server
        return false;
    }

    /**
     * Method wich provide checking validation token
     *
     * @return is validation token
     */
    @NonNull
    private boolean validateToken() {
        //TODO send request to server for check token
        return false;
    }

    /**
     * Method wich provide open main screen
     */
    private void openMainActivity() {
        Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

}
