package com.script972.clutchclient.mvp.impl;

import com.script972.clutchclient.model.api.User;
import com.script972.clutchclient.mvp.contracts.LoginContract;
import com.script972.clutchclient.mvp.contracts.RegistrationContract;
import com.script972.clutchclient.ui.activitys.authorization.LoginActivity;
import com.script972.clutchclient.ui.activitys.authorization.RegistrationStep1Activity;

public class LoginPresenterImpl implements LoginContract.Presenter {

    private final LoginContract.View view;

    public LoginPresenterImpl(LoginActivity loginActivity) {
        this.view=loginActivity;
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
        User user=new User();
        user.setEmail(login);
        user.setPassword(password);
        view.loginDone(user);

    }
}
