package com.script972.clutchclient.ui.activitys;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.script972.clutchclient.api.helpers.AuthManager;
import com.script972.clutchclient.helpers.DialogHelper;

public class BaseActivity extends AppCompatActivity {

    private AuthManager authManager = AuthManager.getInstance();
    private Dialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProgressDialog = DialogHelper.getProgressDialog(this);
        
        if (isAuthShouldBeChecked() && !authManager.isUserAuthorized(getApplicationContext())) {
            tryToAuthorizeInBackground();
        }
    }

    /**
     * Method wich show progress dialog
     */
    protected void showProgressDialog(){
        if(!mProgressDialog.isShowing())
            mProgressDialog.show();
    }

    /**
     * Method wich close Dialog view
     *
     */
    protected void hideProgressDialog() {
        DialogHelper.safeClose(mProgressDialog);
    }

    /**
     * @return true if need to reauthorize in case logout
     */
    protected boolean isAuthShouldBeChecked() {
        return true;
    }

    /**
     * Method wich doing authorize
     */
    private void tryToAuthorizeInBackground() {
        authManager.authorize(this, new AuthManager.AuthCallback() {
            @Override
            public void onStart() {
                showProgressDialog();
            }

            @Override
            public void onSuccess() {
                hideProgressDialog();
            }

            @Override
            public void onError(Throwable e) {
                hideProgressDialog();
                //startSplashActivity();
//                Dialogs.showSingleButtonDialog(SplashActivity.this, e.getMessage());
            }
        });
    }


}
