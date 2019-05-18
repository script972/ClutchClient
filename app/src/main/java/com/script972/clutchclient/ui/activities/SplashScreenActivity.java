package com.script972.clutchclient.ui.activities;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;

import androidx.annotation.NonNull;

import android.os.Bundle;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.script972.clutchclient.R;
import com.script972.clutchclient.helpers.IntentHelpers;
import com.script972.clutchclient.manages.AuthManager;
import com.script972.clutchclient.ui.activities.authorization.LoginActivity;



public class SplashScreenActivity extends BaseActivity {

    TextView versionView;

    /**
     * Manager of authorize
     */
    private AuthManager authManager = AuthManager.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        fillVersion();
        waiter();
    }

    /**
     * Method wich fill version of this app
     */
    private void fillVersion() {
        versionView = findViewById(R.id.version_show);
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
                openNextActivity();
            }
        }, delay);

    }

    /**
     * Method wich decided what activity is next
     */
    private void openNextActivity() {
        IntentHelpers.INSTANCE.pushMainActivity(this);

        /*AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
        if (isLoggedIn) {
                    IntentHelpers.pushMainActivity(this);
        } else {
            openLoginActivity();
        }*/

    }


    /**
     * Method wich provide checking validation token
     *
     * @param valueToken
     * @return is validation token
     */
    @NonNull
    private boolean validateToken(String valueToken) {
        if (valueToken.equalsIgnoreCase("null")) {
            return false;
        }

        //TODO send request to server for check token
        return false;
    }

    /**
     * Method wich provide open login screen
     */
    private void openLoginActivity() {
        Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

}
