package com.script972.clutchclient.mvp.impl;

import androidx.annotation.NonNull;

import com.script972.clutchclient.manages.RetrofitManager;
import com.script972.clutchclient.domain.api.service.CompanyService;
import com.script972.clutchclient.domain.api.model.api.Company;
import com.script972.clutchclient.mvp.contracts.CompanyListContract;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompanyListPresenterImpl implements CompanyListContract.Presenter {

    /**
     * View activity
     */
    private CompanyListContract.View view;

    public CompanyListPresenterImpl(CompanyListContract.View view) {
        this.view = view;
    }

    @Override
    public void refreshCompanyList() {
        initCardCompanyListFromServer();
    }

    /**
     * Method for getting list company from server
     */
    private void initCardCompanyListFromServer() {
        CompanyService companyService= RetrofitManager.getInstance().apiRetrofit.create(CompanyService.class);
        companyService.getCompanyList()
                .enqueue(new Callback<List<Company>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<Company>> call, @NonNull Response<List<Company>> response) {
                        List<Company> responceList=response.body();
                        if(responceList==null)
                            return;
                        Collections.sort(responceList);
                        view.refreshDataCompany(responceList);
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<Company>> call, @NonNull Throwable t) {
                        view.onFailureGetData(t);
                    }
                });
    }
}
