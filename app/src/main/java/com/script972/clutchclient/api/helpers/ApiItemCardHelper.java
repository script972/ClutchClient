package com.script972.clutchclient.api.helpers;

import com.script972.clutchclient.api.service.CardItemService;
import com.script972.clutchclient.api.service.UserService;
import com.script972.clutchclient.api.utils.RetrofitClient;

public final class ApiItemCardHelper {

    public static final String BASE_URL = "http://staging.pmax.co";

    public static CardItemService getCardItem(){
        return RetrofitClient.getClient(BASE_URL).create(CardItemService.class);
    }
}
