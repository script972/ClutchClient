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
import android.util.Log;
import android.widget.TextView;

import com.artlite.bslibrary.helpers.preference.BSSharedPreferenceHelper;
import com.script972.clutchclient.Constants;
import com.script972.clutchclient.R;
import com.script972.clutchclient.api.helpers.ApiUserHelper;
import com.script972.clutchclient.model.api.LoginRequestBody;
import com.script972.clutchclient.model.api.TokenResponce;
import com.script972.clutchclient.ui.activitys.authorization.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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
        String valueToken=BSSharedPreferenceHelper.getString(getApplicationContext(), "token");
        if (validateToken(valueToken)) {
            openMainActivity();
        } else {
            getNewToken();
        }

    }

    /**
     * Method wich help to get new values temporary token
     */
    @NonNull
    private void getNewToken() {
        String login = BSSharedPreferenceHelper.getString(getApplicationContext(), "valueLogin");
        String password = BSSharedPreferenceHelper.getString(getApplicationContext(), "valuePassword");
        if(login.equalsIgnoreCase("null") || password.equalsIgnoreCase("null")){
            openLoginActivity();
            return;
        }

        final LoginRequestBody body=new LoginRequestBody(login, password);
        ApiUserHelper.authorization().authorization(body).enqueue(new Callback<TokenResponce>() {
            @Override
            public void onResponse(Call<TokenResponce> call, Response<TokenResponce> response) {
                if(response.body()==null){
                    openLoginActivity();
                }else{
                    BSSharedPreferenceHelper.save(getApplicationContext(), "Bearer "+response.body().getAccess_token(), "token");
                    openMainActivity();
                }
            }

            @Override
            public void onFailure(Call<TokenResponce> call, Throwable t) {
                //openLoginActivity();
            }
        });
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
