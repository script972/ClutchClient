package com.script972.clutchclient.mvp.impl;

import android.support.annotation.NonNull;

import com.script972.clutchclient.api.ApiClient;
import com.script972.clutchclient.api.service.CardItemService;
import com.script972.clutchclient.helpers.PrefHelper;
import com.script972.clutchclient.model.api.CardItem;
import com.script972.clutchclient.mvp.contracts.CardContract;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.artlite.bslibrary.managers.BSContextManager.getApplicationContext;

public class CardPresenterImpl implements CardContract.Presenter {

    /**
     * View activity
     */
    private CardContract.View view;

    public CardPresenterImpl(CardContract.View view) {
        this.view = view;
    }

    /**
     * Method wich is item of licycle activity
     */
    @Override
    public void onStart() {
        initialCardList();
    }

    /**
     * Method witch help to initial Card list
     */
    private void initialCardList() {
        String token;
        token= PrefHelper.getAccessToken(getApplicationContext());
        CardItemService cardItemService= ApiClient.getClient().create(CardItemService.class);
        cardItemService.getAllItemCard(token).enqueue(new Callback<List<CardItem>>() {
            @Override
            public void onResponse(@NonNull Call<List<CardItem>> call, @NonNull Response<List<CardItem>> response) {
                view.fillCards(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<List<CardItem>> call, @NonNull Throwable t) {

            }
        });
    }
}
