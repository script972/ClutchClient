package com.script972.clutchclient.mvp.impl;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.script972.clutchclient.Constants;
import com.script972.clutchclient.api.helpers.ApiItemCardHelper;
import com.script972.clutchclient.model.api.CardItem;
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
    private Activity activity;

    public CardPresenterImpl(FragmentActivity activity, CardContract.View view) {
        this.view = view;
        this.activity=activity;
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

        token = Constants.token;
        Log.i("card", token+"<<");

        ApiItemCardHelper.getCardItem().getAllItemCard(token).enqueue(new Callback<List<CardItem>>() {
            @Override
            public void onResponse(Call<List<CardItem>> call, Response<List<CardItem>> response) {
                List<CardItem> list=response.body();
                view.fillCards(list);
                Log.i("card", call.request().url()+"<<");
                Log.i("card", response.body()+"<<");
            }

            @Override
            public void onFailure(Call<List<CardItem>> call, Throwable t) {

            }
        });
    }
}
