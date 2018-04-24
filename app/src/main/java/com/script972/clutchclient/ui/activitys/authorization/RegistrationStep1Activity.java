package com.script972.clutchclient.ui.activitys.authorization;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.script972.clutchclient.R;
import com.script972.clutchclient.helpers.DialogHelper;
import com.script972.clutchclient.model.api.User;
import com.script972.clutchclient.mvp.contracts.RegistrationContract;
import com.script972.clutchclient.helpers.ValidatorHelper;
import com.script972.clutchclient.mvp.impl.RegistrationPresentersImpl;

import butterknife.BindView;

public class RegistrationStep1Activity extends AppCompatActivity implements RegistrationContract.View {

    @BindView(R.id.btn_registration)
    Button btnRegistration;

    @BindView(R.id.edt_email)
    EditText edtEmail;

    @BindView(R.id.edt_password)
    EditText edtPassword;

    @BindView(R.id.edt_password_rep)
    EditText edtRepPassword;

    private Dialog progressDialog = null;


    private final RegistrationContract.Presenter presenter = new RegistrationPresentersImpl(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_step1);

        btnRegistration.setOnClickListener(clicker);

    }

    /**
     * Method which controll registration click
     */
    private void btnRegistrationClick() {
        if(validateEmail(edtEmail) & validatePassword(edtPassword) & validatePassword(edtRepPassword)){

            if(!edtPassword.equals(edtRepPassword)){
                edtRepPassword.setError(getResources().getString(R.string.e_password_not_same));
                return;
            }else{
                this.showProgressDialog();
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
        if(ValidatorHelper.validateEmail(edtEmail.getText().toString())){
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




    private void showProgressDialog() {
        hideProgressDialog();

        progressDialog = DialogHelper.getProgressDialog(this);
        progressDialog.show();
    }

    protected void hideProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }


    /**
     * Method wich call if registation pass success
     * @param user
     */
    @Override
    public void registrationSuccess(User user) {
        Intent intent = new Intent(this, RegistrationStep2Activity.class);
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
                case R.id.btn_login: btnRegistrationClick(); break;
            }
        }
    };
}
