package com.script972.clutchclient.ui.activitys.authorization;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.artlite.bslibrary.annotations.FindViewBy;
import com.script972.clutchclient.R;
import com.script972.clutchclient.helpers.DialogHelper;
import com.script972.clutchclient.helpers.ValidatorHelper;
import com.script972.clutchclient.model.api.User;
import com.script972.clutchclient.mvp.contracts.LoginContract;
import com.script972.clutchclient.mvp.contracts.RegistrationContract;
import com.script972.clutchclient.mvp.impl.LoginPresenterImpl;
import com.script972.clutchclient.mvp.impl.RegistrationPresentersImpl;
import com.script972.clutchclient.ui.activitys.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements LoginContract.View{

    @BindView(R.id.edt_email)
    EditText edtEmail;

    @BindView(R.id.edt_password)
    EditText edtPassword;

    @BindView(R.id.btn_login)
    Button button;

    @BindView(R.id.pb_freeze)
    ProgressBar pbFreeze;

    @BindView(R.id.link_registration)
    TextView linkRegistration;

    private Dialog progressDialog = null;


    private final LoginContract.Presenter presenter = new LoginPresenterImpl(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        ButterKnife.bind(this);
        button.setOnClickListener(clicker);
        linkRegistration.setOnClickListener(clicker);
    }

    /**
     * Method which controll login click
     */
    private void btnLoginClick() {
        if(!ValidatorHelper.validateEmail(edtEmail.getText().toString()) & edtEmail.getText().toString().length()<6){
            edtEmail.setError(getResources().getString(R.string.e_invalid_email));
            edtPassword.setError(getResources().getString(R.string.e_short_password));
            return;
        }else{
            showProgressDialog();
            this.presenter.login(edtEmail.getText().toString(), edtPassword.getText().toString());
        }
    }

    /**
     * Method wich help move to registration activity
     */
    private void btnRegistration() {
        Intent intent = new Intent(this, RegistrationStep1Activity.class);
        startActivity(intent);
    }

    /**
     * Method wich show progress dialog and freeze window
     */
    private void showProgressDialog() {
        hideProgressDialog();
        progressDialog = DialogHelper.getProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    /**
     * Method wich hide progress dialog and unfreeze window
     */
    protected void hideProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    /**
     * Withod wich show UI user login success
     *
     * @param user
     */
    @Override
    public void loginDone(User user) {
        Intent intent=new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /**
     * Method wich show UI user login fail
     *
     * @param errorMessage describe errror
     */
    @Override
    public void loginFail(String errorMessage) {
        hideProgressDialog();
    }

    /**
     * Listener click
     */
    private final View.OnClickListener clicker=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_login: btnLoginClick(); break;
                case R.id.link_registration: btnRegistration(); break;
            }
        }
    };



}
