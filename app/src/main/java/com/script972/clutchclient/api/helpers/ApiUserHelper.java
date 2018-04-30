package com.script972.clutchclient.api.helpers;

import com.script972.clutchclient.api.service.UserService;
import com.script972.clutchclient.api.utils.RetrofitClient;

public final class ApiUserHelper {

    public static final String BASE_URL = "http://staging.pmax.co";

    public static UserService getUsers() {
        return RetrofitClient.getClient(BASE_URL).create(UserService.class);
    }

    public static UserService getVideo(){
        return RetrofitClient.getClient(BASE_URL).create(UserService.class);
    }

    public static UserService getClientByPhoneNumbers(){
        return RetrofitClient.getClient(BASE_URL).create(UserService.class);
    }

}
