package com.script972.clutchclient.api.helpers;

import com.script972.clutchclient.api.service.CardItemService;
import com.script972.clutchclient.api.service.CompanyService;
import com.script972.clutchclient.api.utils.RetrofitClient;

public final class ApiCompanyHelper {

    //public static final String BASE_URL = "http://94.179.117.8:97";
    public static final String BASE_URL = "https://clutchserver.herokuapp.com/";

    public static CompanyService getComapyList(){
        return RetrofitClient.getClient(BASE_URL).create(CompanyService.class);
    }
}
