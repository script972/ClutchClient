package com.script972.clutchclient.mvp.impl;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.artlite.bslibrary.helpers.preference.BSSharedPreferenceHelper;
import com.artlite.bslibrary.managers.BSContextManager;
import com.script972.clutchclient.R;
import com.script972.clutchclient.api.helpers.ApiClient;
import com.script972.clutchclient.api.service.UserService;
import com.script972.clutchclient.model.api.LoginRequestBody;
import com.script972.clutchclient.model.api.TokenResponce;
import com.script972.clutchclient.mvp.contracts.LoginContract;
import com.script972.clutchclient.ui.activitys.authorization.LoginActivity;


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
    public void login(final String login, final String password) {
        final LoginRequestBody body=new LoginRequestBody(login, password);

        UserService userServiceAPI = ApiClient.getClient().create(UserService.class);
        userServiceAPI.authorization(body).enqueue(new Callback<TokenResponce>() {
            @Override
            public void onResponse(@NonNull Call<TokenResponce> call, @NonNull Response<TokenResponce> response) {
                if(response.body()==null){
                    view.loginFail(context.getResources().getString(R.string.e_invalid_login));
                }else{
                    saveSuccessCredentials(login, password);
                    view.loginDone(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<TokenResponce> call, @NonNull Throwable t) {
                view.loginFail(context.getResources().getString(R.string.e_check_internet));
            }
        });

    }

    /**
     * Method witch provide saving success credentials to SharedPreferences
     * @param login
     * @param password
     */
    private void saveSuccessCredentials(String login, String password) {
        BSSharedPreferenceHelper.save(BSContextManager.getApplicationContext(), login, "valueLogin");
        BSSharedPreferenceHelper.save(BSContextManager.getApplicationContext(), password, "valuePassword");
    }
}
