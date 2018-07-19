package com.script972.clutchclient.mvp.impl;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.artlite.bslibrary.helpers.preference.BSSharedPreferenceHelper;
import com.artlite.bslibrary.managers.BSContextManager;
import com.script972.clutchclient.R;
import com.script972.clutchclient.api.helpers.ApiClient;
import com.script972.clutchclient.api.helpers.AuthManager;
import com.script972.clutchclient.api.service.UserService;
import com.script972.clutchclient.model.Credentials;
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
        final Credentials credentials=new Credentials(login, password);
        AuthManager.getInstance().loginWithCredentials(context, credentials, new AuthManager.AuthCallback() {
            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess() {
                view.loginDone();
            }

            @Override
            public void onError(Throwable e) {
                view.loginFail(context.getResources().getString(R.string.e_invalid_login));

                //startSplashActivity();
//                Dialogs.showSingleButtonDialog(SplashActivity.this, e.getMessage());
            }
        });

    }



    /**
     * Callbacks
     */

    AuthManager.AuthCallback callbackManager = new AuthManager.AuthCallback() {
        @Override
        public void onStart() {

        }

        @Override
        public void onSuccess() {

        }

        @Override
        public void onError(Throwable e) {

        }
    };
}
