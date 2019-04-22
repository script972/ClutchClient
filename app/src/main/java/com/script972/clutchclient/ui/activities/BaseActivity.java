package com.script972.clutchclient.ui.activities;

import android.app.Dialog;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.TextView;

import com.script972.clutchclient.R;
import com.script972.clutchclient.databinding.ActivityAddCardBinding;
import com.script972.clutchclient.manages.AuthManager;
import com.script972.clutchclient.helpers.DialogHelper;
import com.script972.clutchclient.ui.model.InformationCodes;
import com.script972.clutchclient.ui.model.StatusCode;

import java.util.Timer;
import java.util.TimerTask;

public class BaseActivity extends AppCompatActivity {

    //outlets
    private View statusPanel;
    private TextView txtStatus;

    private AuthManager authManager = AuthManager.getInstance();
    private Dialog mProgressDialog;

    //public enum TypeStatus {INFORM, WARNING, ERROR}


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProgressDialog = DialogHelper.getProgressDialog(this);

        if (isAuthShouldBeChecked() && !authManager.isUserAuthorized(getApplicationContext())) {
            tryToAuthorizeInBackground();
        }
    }

    /**
     * Method wich init common view, such us inform panel
     */
    public void initCommonView() {
        initInformPanel();
    }

    /**
     * Method wich init InformPanel
     */
    private void initInformPanel() {
        statusPanel = findViewById(R.id.status_panel);
        txtStatus = findViewById(R.id.txt_status);

    }

    /**
     * Method for show status panel on time
     *
     * @param value
     * @param typeStatus
     * @param timer
     */
    public void showStatusPanel(String value, StatusCode typeStatus, boolean timer) {
        if (statusPanel == null)
            return;
        showStatusPanel(value, typeStatus);
        Timer t = new Timer(false);
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> statusPanel.setVisibility(View.GONE));
            }
        }, 5000);
        //statusPanel.setVisibility(View.GONE);

    }


    /**
     * Method wich show status panel
     *
     * @param value      of inform data (text)
     * @param typeStatus
     */
    public void showStatusPanel(String value, StatusCode typeStatus) {
        int colorStatus = getResources().getColor(R.color.color_inform_panel);
        switch (typeStatus) {
            case ERROR:
                colorStatus = getResources().getColor(R.color.color_error_panel);
                break;
            case INFORMATION:
                colorStatus = getResources().getColor(R.color.color_inform_panel);
                break;
            case WARNING:
                colorStatus = getResources().getColor(R.color.color_warning_panel);
                break;
        }

        if (statusPanel == null || txtStatus == null) {
            if (statusPanel != null)
                statusPanel.setVisibility(View.GONE);
            return;
        }
        statusPanel.setBackgroundColor(colorStatus);
        statusPanel.setVisibility(View.VISIBLE);
        txtStatus.setText(value);
    }

    public void showStatusPanel(InformationCodes message) {
        showStatusPanel(message.getTextInformation(), message.getStatusCode(), true);
    }


    /**
     * Method wich closing status panel
     */
    public void closeStatusPanel() {
        if (statusPanel != null) {
            statusPanel.setVisibility(View.GONE);
        }
    }


    /**
     * Method wich show progress dialog
     */
    public void showProgressDialog() {
        if (!mProgressDialog.isShowing())
            mProgressDialog.show();
    }

    /**
     * Method wich close Dialog view
     */
    protected void hideProgressDialog() {
        if(mProgressDialog.isShowing())
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
