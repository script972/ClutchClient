package com.script972.clutchclient.ui.activitys.authorization;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.script972.clutchclient.R;
import com.script972.clutchclient.helpers.KeyboardHelper;
import com.script972.clutchclient.mvp.contracts.LoginContract;
import com.script972.clutchclient.mvp.impl.LoginPresenterImpl;
import com.script972.clutchclient.ui.activitys.BaseActivity;
import com.script972.clutchclient.ui.activitys.MainActivity;

public class LoginActivity extends BaseActivity implements LoginContract.View{

    private EditText edtEmail;

    private EditText edtPassword;

    private Button btnLogin;

    private LinearLayout linkRegistration;


    private final LoginContract.Presenter presenter = new LoginPresenterImpl(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        super.initCommonView();
        initView();

    }

    private void initView() {
        this.btnLogin = findViewById(R.id.btn_login);
        this.edtEmail = findViewById(R.id.edt_email);
        this.edtPassword = findViewById(R.id.edt_password);
        this.linkRegistration = findViewById(R.id.link_registration);
        btnLogin.setOnClickListener(clicker);
        linkRegistration.setOnClickListener(clicker);
    }

    /**
     * Method which controll login click
     */
    private void btnLoginClick() {
        /*if(!ValidatorHelper.validateEmail(edtEmail.getText().toString()) & edtEmail.getText().toString().length()<6){
            edtEmail.setError(getResources().getString(R.string.e_invalid_email));
            edtPassword.setError(getResources().getString(R.string.e_short_password));
            return;
        }else{

            this.presenter.login(edtEmail.getText().toString(), edtPassword.getText().toString());
        }*/

        KeyboardHelper.hideSoftKeyboard(this);
        super.showProgressDialog();
        this.presenter.login(edtEmail.getText().toString(), edtPassword.getText().toString());

    }

    /**
     * Method wich help move to registration activity
     */
    private void btnRegistration() {
        Intent intent = new Intent(this, RegistrationStep1Activity.class);
        startActivity(intent);
    }


    /**
     * Withod wich show UI user login success
     *
     */
    @Override
    public void loginDone() {
        super.hideProgressDialog();
        Intent intent=new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


    /**
     * Method wich show UI user login fail
     *
     * @param errorMessage describe errror
     */
    @Override
    public void loginFail(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        super.hideProgressDialog();
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
