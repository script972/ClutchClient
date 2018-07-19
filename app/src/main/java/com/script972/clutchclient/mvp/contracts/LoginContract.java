package com.script972.clutchclient.mvp.contracts;

import com.script972.clutchclient.model.api.TokenResponce;
import com.script972.clutchclient.model.api.User;

public interface LoginContract {

    interface Presenter{
        /**
         * Method wich controll login process of User
         * @param login user's
         * @param password user's
         */
        void login(String login, String password);
    }

    interface View{
        /**
         * Withod wich show UI user login success
         */
        void loginDone();

        /**
         * Method wich show UI user login fail
         * @param errorMessage describe errror
         */
        void loginFail(String errorMessage);

    }

}
