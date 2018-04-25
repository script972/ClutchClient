package com.script972.clutchclient.mvp.contracts;

import com.script972.clutchclient.model.api.User;

public class LoginContract {

    public interface Presenter{


        /**
         * Method wich controll login process of User
         * @param login
         * @param password
         */
        void login(String login, String password);
    }

    public interface View{
        /**
         * Withod wich show UI user login success
         */
        void loginDone(User user);

        /**
         * Method wich show UI user login fail
         * @param errorMessage describe errror
         */
        void loginFail(String errorMessage);

    }

}
