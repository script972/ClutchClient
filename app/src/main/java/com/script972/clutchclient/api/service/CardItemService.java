package com.script972.clutchclient.api.service;

import android.app.DownloadManager;

import com.script972.clutchclient.model.api.CardItem;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface CardItemService {

    @GET("/api/card/mycard")
    Call<List<CardItem>> getAllItemCard();

    @POST("/api/card")
    Call<CardItem> postCardItem(@Body CardItem cardItem);

    @Multipart
    @POST("/api/card/frontphoto")
    Call<ResponseBody> postImage(@Part MultipartBody.Part file);


}