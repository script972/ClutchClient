package com.script972.clutchclient.domain.api.service;

import com.script972.clutchclient.domain.api.model.api.Company;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CompanyService {

    @GET("/api/company")
    Call<List<Company>> getCompanyList();

}
