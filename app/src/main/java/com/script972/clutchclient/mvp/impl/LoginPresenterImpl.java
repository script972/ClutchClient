package com.script972.clutchclient.mvp.impl;

import android.content.Context;
import android.util.Log;

import com.script972.clutchclient.R;
import com.script972.clutchclient.api.helpers.ApiCompanyHelper;
import com.script972.clutchclient.api.helpers.ApiUserHelper;
import com.script972.clutchclient.model.api.Company;
import com.script972.clutchclient.model.api.LoginRequestBody;
import com.script972.clutchclient.model.api.TokenResponce;
import com.script972.clutchclient.model.api.User;
import com.script972.clutchclient.mvp.contracts.LoginContract;
import com.script972.clutchclient.mvp.contracts.RegistrationContract;
import com.script972.clutchclient.ui.activitys.authorization.LoginActivity;
import com.script972.clutchclient.ui.activitys.authorization.RegistrationStep1Activity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresenterImpl implements LoginContract.Presenter {

    private final LoginContract.View view;
    private final Context context;

    public LoginPresenterImpl(LoginActivity loginActivity) {
        this.view=loginActivity;
        this.context= (Context) view;
    }


    /**
     * Method wich controll login process of User
     *
     * @param login
     * @param password
     */
    @Override
    public void login(String login, String password) {
        //TODO to server
        final LoginRequestBody body=new LoginRequestBody(login, password);
        ApiUserHelper.authorization().authorization(body).enqueue(new Callback<TokenResponce>() {
            @Override
            public void onResponse(Call<TokenResponce> call, Response<TokenResponce> response) {
                if(response.body()==null){
                    view.loginFail(context.getResources().getString(R.string.e_invalid_login));
                }else{
                    view.loginDone(response.body());
                }
            }

            @Override
            public void onFailure(Call<TokenResponce> call, Throwable t) {
                view.loginFail(context.getResources().getString(R.string.e_check_internet));
            }
        });

    }
}
