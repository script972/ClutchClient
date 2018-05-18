package com.script972.clutchclient.api.service;

import com.script972.clutchclient.model.api.CardItem;
import com.script972.clutchclient.model.api.Company;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface CompanyService {

    @GET("/api/company")
    Call<List<Company>> getCompanyList(@Header("Authorization") String headerToken);

}
