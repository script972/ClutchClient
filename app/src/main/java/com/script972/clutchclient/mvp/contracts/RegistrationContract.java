package com.script972.clutchclient.mvp.contracts;

import com.script972.clutchclient.model.api.User;

public class RegistrationContract {

    public interface Presenter{

        /**
         * Method wich check the existing User on the server
         * @param email user's email
         */
        void checkSameUserName(String email);

        /**
         * Method wich registration new User
         * @param email
         * @param password
         */
        void sendNewUser(String email, String password);

    }

    public interface View{
        /**
         * Method wich call if registation pass success
         * @param user
         */
        void registrationSuccess(User user);

        /**
         * Method wich call if registation pass fail
         */
        void registrationFail(String errorMessage);

        /**
         * Method wich display that the same user already existing
         * @param existing true or false
         */
        void sameUserExisting(boolean existing);



    }

}
