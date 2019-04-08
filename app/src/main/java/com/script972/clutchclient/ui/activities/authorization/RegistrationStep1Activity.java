package com.script972.clutchclient.ui.activities.authorization;

import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.AccountPicker;
import com.script972.clutchclient.R;
import com.script972.clutchclient.domain.api.model.api.User;
import com.script972.clutchclient.mvp.contracts.RegistrationContract;
import com.script972.clutchclient.helpers.ValidatorHelper;
import com.script972.clutchclient.mvp.impl.RegistrationPresentersImpl;
import com.script972.clutchclient.ui.activities.BaseActivity;
import com.script972.clutchclient.ui.model.StatusCode;

import java.io.IOException;


public class RegistrationStep1Activity extends BaseActivity implements RegistrationContract.View {

    //outlets
    private Toolbar toolbar;
    private Button btnRegistrationOne;
    private EditText edtEmail;
    private EditText edtPassword;
    private EditText edtRepPassword;
    private LinearLayout signInViaGoogle;
    private LinearLayout signInViaFacebook;


    private final static String G_PLUS_SCOPE =
            "oauth2:https://www.googleapis.com/auth/plus.me";
    private final static String USERINFO_SCOPE =
            "https://www.googleapis.com/auth/userinfo.profile";
    private final static String EMAIL_SCOPE =
            "https://www.googleapis.com/auth/userinfo.email";
    private final static String SCOPES = G_PLUS_SCOPE + " " + USERINFO_SCOPE + " " + EMAIL_SCOPE;

    //presenters
    private final RegistrationContract.Presenter presenter = new RegistrationPresentersImpl(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_step1);
        initView();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 123 && resultCode == RESULT_OK) {
            final String accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
            @SuppressLint("StaticFieldLeak")
            AsyncTask<Void, Void, String> getToken = new AsyncTask<Void, Void, String>() {
                @Override
                protected String doInBackground(Void... params) {
                    try {
                        String token = GoogleAuthUtil.getToken(RegistrationStep1Activity.this, accountName,
                                SCOPES);
                        return token;

                    } catch (UserRecoverableAuthException userAuthEx) {
                        startActivityForResult(userAuthEx.getIntent(), 123);
                    }  catch (IOException ioEx) {
                        Log.d("logindoit", "IOException");
                    }  catch (GoogleAuthException fatalAuthEx)  {
                        Log.d("logindoit", "Fatal Authorization Exception" + fatalAuthEx.getLocalizedMessage());
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(String token) {
                }

            };
            getToken.execute(null, null, null);
        }
    }

    /**
     * Method wich init views
     */
    private void initView() {
        initToolbar();
        btnRegistrationOne = findViewById(R.id.btn_registration_one);

        edtEmail = findViewById(R.id.edt_email);
        edtPassword = findViewById(R.id.edt_password);
        edtRepPassword = findViewById(R.id.edt_password_rep);
        signInViaFacebook = findViewById(R.id.signing_via_facebook);
        signInViaGoogle = findViewById(R.id.signing_via_googleplus);

        btnRegistrationOne.setOnClickListener(clicker);
        signInViaFacebook.setOnClickListener(clicker);
        signInViaGoogle.setOnClickListener(clicker);


    }

    /**
     * Method for init toolbar
     */
    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setTitle(R.string.toolbar_registration);
    }

    /**
     * Method which controll registration click
     */
    private void btnRegistrationClick() {
        if(validateEmail(edtEmail) & validatePassword(edtPassword) & validatePassword(edtRepPassword)){
            if(!edtPassword.getText().toString().equals(edtRepPassword.getText().toString())){
                edtRepPassword.setError(getResources().getString(R.string.e_password_not_same));
                return;
            }
            super.showProgressDialog();
            presenter.checkSameUserName(edtEmail.getText().toString());

            //presenter.sendNewUser(edtEmail.getText().toString(), edtPassword.getText().toString());
        }
    }

    /**
     * Method which controll registration via Google Plus click
     */
    private void btnRegistrationGooglePlusClick() {
        Log.d("logindoit", "clicker");
        Intent intent = AccountPicker.newChooseAccountIntent(null, null, new String[]{"com.google"},
                false, null, null, null, null);
        startActivityForResult(intent, 123);

    }

    /**
     * Method which controll registration via Facebook click
     */
    private void btnRegistrationFacebookClick() {

    }

    /**
     * Method wich controll validation email
     * @param edtEmail
     * @return
     */
    private boolean validateEmail(EditText edtEmail) {
        if(!ValidatorHelper.validateEmail(edtEmail.getText().toString())){
            edtEmail.setError(getResources().getString(R.string.e_invalid_email));
            return false;
        }else{
            return true;
        }
    }

    /**
     * Method wich controll validation password
     * @param edtPassword
     * @return
     */
    private boolean validatePassword(EditText edtPassword) {
        if(edtPassword.getText().length() < 6){
            edtPassword.setError(getResources().getString(R.string.e_short_password));
            return false;
        }
        return true;
    }

    /**
     * Method wich call if registation pass success
     * @param user
     */
    @Override
    public void registrationSuccess(User user) {
        hideProgressDialog();
        Intent intent = new Intent(this, PersonalInfoActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    /**
     * Method wich call if registation pass fail
     *
     * @param errorMessage
     */
    @Override
    public void registrationFail(String errorMessage) {
        super.showStatusPanel(errorMessage, StatusCode.ERROR);
    }

    /**
     * Method wich display that the same user already existing
     */
    @Override
    public void sameUserExisting(boolean existing) {
        if(existing) {
            edtEmail.setError(getResources().getString(R.string.e_user_already_existing));
        }else{
            presenter.sendNewUser(edtEmail.getText().toString(), edtPassword.getText().toString());
        }
    }

    /**
     * Listener click
     */
    private final View.OnClickListener clicker=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_registration_one: btnRegistrationClick(); break;
                case R.id.signing_via_googleplus: btnRegistrationGooglePlusClick(); break;
                case R.id.signing_via_facebook: btnRegistrationFacebookClick(); break;
            }
        }
    };


}
