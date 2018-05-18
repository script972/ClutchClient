package com.script972.clutchclient.api.helpers;

import com.script972.clutchclient.api.service.UserService;
import com.script972.clutchclient.api.utils.RetrofitClient;

import retrofit2.http.Header;

public final class ApiUserHelper {


    //public static final String BASE_URL = "http://94.179.117.8:97";
    public static final String BASE_URL = "https://clutchserver.herokuapp.com/";

    public static UserService authorization(){
        return RetrofitClient.getClient(BASE_URL).create(UserService.class);
    }


}
