package com.script972.clutchclient.api.service;

import com.script972.clutchclient.model.api.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserService {

    @GET("/api/user/{userid}")
    Call<User> getUser(@Query("userid") String id);

    @Headers("Content-Type: application/json")
    @POST("/api/user/")
    Call<User> postAthletes(@Body User user);

    @GET("/api/user/{phoneNumber}")
    Call<List<User>> getUsersByPhoneNumber(@Query("phoneNumber") String phoneNumbers);


}