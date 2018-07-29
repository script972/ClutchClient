package com.script972.clutchclient.ui.activitys.authorization;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.script972.clutchclient.R;
import com.script972.clutchclient.model.api.User;
import com.script972.clutchclient.mvp.contracts.RegistrationContract;
import com.script972.clutchclient.helpers.ValidatorHelper;
import com.script972.clutchclient.mvp.impl.RegistrationPresentersImpl;
import com.script972.clutchclient.ui.activitys.BaseActivity;


public class RegistrationStep1Activity extends BaseActivity implements RegistrationContract.View {

    //outlets
    private Toolbar toolbar;

    private Button btnRegistrationOne;

    private EditText edtEmail;

    private EditText edtPassword;

    private EditText edtRepPassword;

    //presenters
    private final RegistrationContract.Presenter presenter = new RegistrationPresentersImpl(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_step1);
        initView();

    }

    /**
     * Method wich init views
     */
    private void initView() {
        initToolbar();
        btnRegistrationOne = findViewById(R.id.btn_registration_one);
        btnRegistrationOne.setOnClickListener(clicker);

        edtEmail = findViewById(R.id.edt_email);
        edtPassword = findViewById(R.id.edt_password);
        edtRepPassword = findViewById(R.id.edt_password_rep);
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
            }else{
                super.showProgressDialog();
                presenter.checkSameUserName(edtEmail.toString());
            }
        }
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
            }
        }
    };
}
