package com.script972.clutchclient.ui.activitys;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.widget.TextView;

import com.script972.clutchclient.R;
import com.script972.clutchclient.manages.AuthManager;
import com.script972.clutchclient.helpers.PrefHelper;
import com.script972.clutchclient.ui.activitys.authorization.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SplashScreenActivity extends BaseActivity {


    @BindView(R.id.version_show)
    TextView versionView;

    /**
     * Manager of authorize
     */
    private AuthManager authManager = AuthManager.getInstance();


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
        if (PrefHelper.isAuthorized(getApplicationContext())) {
            openMainActivity();
        } else{
            openLoginActivity();
        }

    }


    /**
     * Method wich provide checking validation token
     *
     * @return is validation token
     * @param valueToken
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
     * Method wich provide open main screen
     */
    private void openMainActivity() {
        Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
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
