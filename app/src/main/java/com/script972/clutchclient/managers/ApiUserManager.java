package com.script972.clutchclient.managers;

import com.script972.clutchclient.api.UserService;
import com.script972.clutchclient.utils.RetrofitClient;

public class ApiUserManager {

    public static final String BASE_URL = "http://staging.pmax.co";

    public static UserService getUsers() {
        return RetrofitClient.getClient(BASE_URL).create(UserService.class);
    }

    public static UserService getVideo(){
        return RetrofitClient.getClient(BASE_URL).create(UserService.class);
    }

}
