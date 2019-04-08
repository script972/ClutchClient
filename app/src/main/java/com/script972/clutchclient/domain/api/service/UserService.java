package com.script972.clutchclient.domain.api.service;

import com.script972.clutchclient.domain.api.model.api.LoginRequestBody;
import com.script972.clutchclient.domain.api.model.api.TokenResponce;
import com.script972.clutchclient.domain.api.model.api.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserService {

    @POST("/auth/login")
    Call<TokenResponce> authorization( @Body LoginRequestBody login);

    @GET("/api/user/{userid}")
    Call<User> getUser(@Query("userid") String id);

    @POST("/api/user/existinguser")
    Call<User> isExistingUser(@Body User user);

    @POST("/auth")
    Call<User> postUser(@Body User user);

    @GET("/api/user/{phoneNumber}")
    Call<List<User>> getUsersByPhoneNumber(@Query("phoneNumber") String phoneNumbers);


}
