package com.script972.clutchclient.mvp.impl;


import com.script972.clutchclient.api.RetrofitManager;
import com.script972.clutchclient.api.service.CardItemService;
import com.script972.clutchclient.api.service.UserService;
import com.script972.clutchclient.model.api.LoginRequestBody;
import com.script972.clutchclient.model.api.TokenResponce;
import com.script972.clutchclient.model.api.User;
import com.script972.clutchclient.mvp.contracts.RegistrationContract;
import com.script972.clutchclient.ui.activitys.authorization.RegistrationStep1Activity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationPresentersImpl implements RegistrationContract.Presenter{

    private RegistrationContract.View view;

    public RegistrationPresentersImpl(RegistrationStep1Activity registrationStep1Activity) {
        this.view=registrationStep1Activity;
    }

    /**
     * Method wich check the existing User on the server
     *
     * @param email user's email
     */
    @Override
    public void checkSameUserName(String email) {
        UserService userService= RetrofitManager.getInstance().apiRetrofit.create(UserService.class);
        User user = new User();
        user.setUsername(email);
        userService.isExistingUser(user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (Boolean.parseBoolean(response.message())) {
                    view.sameUserExisting(true);
                }else{
                    view.sameUserExisting(false);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                view.registrationFail(t.getMessage());
            }
        });

    }

    @Override
    public void sendNewUser(String email, String password) {
        User user=new User();
        user.setUsername(email);
        user.setPassword(password);
        user.setEnabled(true);

        UserService userService= RetrofitManager.getInstance().apiRetrofit.create(UserService.class);
        userService.postUser(user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                view.registrationSuccess(user);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                view.registrationFail(t.getMessage());
            }
        });

    }


}
