package com.script972.clutchclient.mvp.impl;

import androidx.annotation.NonNull;

import com.script972.clutchclient.manages.RetrofitManager;
import com.script972.clutchclient.domain.api.service.CardItemService;
import com.script972.clutchclient.domain.api.model.api.CardItem;
import com.script972.clutchclient.mvp.contracts.CardContract;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
     * Method witch help to initial CardItem list
     */
    private void initialCardList() {
        /*CardItemService cardItemService= RetrofitManager.getInstance().apiRetrofit.create(CardItemService.class);
        cardItemService.getAllItemCard().enqueue(new Callback<List<CardItem>>() {
            @Override
            public void onResponse(@NonNull Call<List<CardItem>> call, @NonNull Response<List<CardItem>> response) {
                view.fillCards(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<List<CardItem>> call, @NonNull Throwable t) {

            }
        });*/
    }
}
