package com.script972.clutchclient.mvp.impl;


import com.script972.clutchclient.model.api.User;
import com.script972.clutchclient.mvp.contracts.RegistrationContract;
import com.script972.clutchclient.ui.activitys.authorization.RegistrationStep1Activity;

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
        //TODO to server
        this.view.sameUserExisting(false);

    }

    @Override
    public void sendNewUser(String email, String password) {
        //TODO to server
        User user=new User();
        user.setEmail(email);
        user.setPassword(password);
        this.view.registrationSuccess(user);

    }
}
