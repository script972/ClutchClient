package com.script972.manages;

import android.content.Context;
import android.support.annotation.NonNull;

import com.script972.clutchclient.api.ApiClient;
import com.script972.clutchclient.api.service.UserService;
import com.script972.clutchclient.exceptions.EmpyResponceException;
import com.script972.clutchclient.helpers.PrefHelper;
import com.script972.clutchclient.model.Credentials;
import com.script972.clutchclient.model.api.LoginRequestBody;
import com.script972.clutchclient.model.api.TokenResponce;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthManager {

    public interface AuthCallback {
        void onStart();

        void onSuccess();

        void onError(Throwable e);
    }

    private static final class LAZY_HOLDER {
        public static AuthManager INSTANCE = new AuthManager(ApiClient.getClient().create(UserService.class));
    }

    private AuthManager(UserService authService) {
        this.authService = authService;
    }

    public static AuthManager getInstance() {
        return LAZY_HOLDER.INSTANCE;
    }

    private final UserService authService;

    public void authorize(Context context, AuthCallback authCallback) {
        authCallback.onStart();

        Credentials credentials = PrefHelper.getCredentials(context);
        if (credentials == null || credentials.getEmail().isEmpty() || credentials.getPassword().isEmpty()) {
            PrefHelper.clear(context);
            authCallback.onError(new Throwable());
            return;
        }
        loginWithCredentials(context, credentials, authCallback);
    }

    public boolean isUserAuthorized(Context context) {
        return PrefHelper.isAuthorized(context);
    }

    public void loginWithCredentials(final Context context, final Credentials credentials, final AuthCallback authCallback) {
        authCallback.onStart();
        final LoginRequestBody body=new LoginRequestBody(credentials.getEmail(), credentials.getPassword());

        authService.authorization(body).enqueue(new Callback<TokenResponce>() {
            @Override
            public void onResponse(@NonNull Call<TokenResponce> call, @NonNull Response<TokenResponce> response) {
                if(response.body()==null){
                    authCallback.onError(new EmpyResponceException("Empy responce body. Request = "+ call.request().toString()));
                    return;
                }

                //PrefHelper.setUserId(context, Integer.valueOf(value.getUserId()));
                PrefHelper.setAccessToken(context, response.body().getAccess_token());
                PrefHelper.setAuthorizedFlag(context, true);
                if (PrefHelper.isRememberMeOptionEnabled(context)) {
                    PrefHelper.saveCredentials(context, credentials);
                }
                authCallback.onSuccess();

            }

            @Override
            public void onFailure(@NonNull Call<TokenResponce> call, @NonNull Throwable t) {
                authCallback.onError(t);
            }
        });

    }






}
