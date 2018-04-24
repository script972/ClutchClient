package com.script972.clutchclient.api;

import com.script972.clutchclient.model.api.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserService {

    @GET("/user/{userid}")
    Call<User> getUser(@Query("userid") String id);

    @Headers("Content-Type: application/json")
    @POST("/user/")
    Call<User> postAthletes(@Body User user);


}
