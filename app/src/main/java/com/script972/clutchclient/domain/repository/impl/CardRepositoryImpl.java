package com.script972.clutchclient.domain.repository.impl;

import android.os.AsyncTask;
import android.util.Pair;

import com.script972.clutchclient.domain.callback.OnResult;
import com.script972.clutchclient.domain.database.dao.CardDao;
import com.script972.clutchclient.domain.database.entity.CardEntity;
import com.script972.clutchclient.domain.repository.CardRepository;
import com.script972.clutchclient.manages.DatabaseManager;
import com.script972.clutchclient.mappers.CardMapper;
import com.script972.clutchclient.ui.model.CardItem;
import com.script972.clutchclient.ui.model.InformationCodes;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

public class CardRepositoryImpl implements CardRepository {

    private CardDao cardDao;

    public CardRepositoryImpl() {
        this.cardDao = DatabaseManager.getInstance().getCardDao();
    }

    @Override
    public LiveData<Pair<InformationCodes, CardItem>> addNewCard(CardEntity cardItem) {
        MutableLiveData<Pair<InformationCodes, CardItem>> liveData = new MutableLiveData<>();
        new CardAddDb(cardItem, cardDao, (result, entity) -> {
            Pair<InformationCodes, CardItem> out = new Pair<>(result,
                    CardMapper.entityToUi(entity));
            liveData.setValue(out);
        }).execute();
        return liveData;

    }

    @Override
    public MutableLiveData<Pair<InformationCodes, CardItem>> updateCardEntity(CardEntity cardItem) {
        MutableLiveData<Pair<InformationCodes, CardItem>> liveData = new MutableLiveData<>();
        new CardUpdateDb(cardItem, cardDao, (result, cardEntity) -> {
            Pair out = new Pair(result, CardMapper.entityToUi(cardEntity));
            liveData.setValue(out);
        }).execute();
        return liveData;
    }

    @Override
    public LiveData<List<CardItem>> getAllCard() {
        return Transformations.map(cardDao.getAllList(), input -> {
            List<CardItem> outData = new ArrayList<>();
            for (int i = 0; i < input.size(); i++) {
                outData.add(CardMapper.entityToUi(input.get(i)));
            }
            return outData;
        });
    }

    class CardUpdateDb extends AsyncTask<Void, Void, InformationCodes> {

        private InformationCodes result;
        private CardEntity item;

        private CardDao database;
        private OnResult callback;

        CardUpdateDb(CardEntity cardItem, CardDao cardDao, OnResult onResult) {
            this.item = cardItem;
            this.database = cardDao;
            this.callback = onResult;
        }

        @Override
        protected InformationCodes doInBackground(Void... voids) {
            CardEntity dbEntity = database.getCardByNumber(item.getNumber());
            if (dbEntity != null) {
                item.setId(dbEntity.getId());
                database.update(item);
                result = InformationCodes.CARD_UPDATED_SUCCESS;
            } else {
                database.insert(item);
                result = InformationCodes.CARD_ADDED_SUCCESS;
            }
            return null;
        }

        @Override
        protected void onPostExecute(InformationCodes informationCodes) {
            super.onPostExecute(informationCodes);
            callback.dataResult(result, item);
        }
    }

    class CardAddDb extends AsyncTask<Void, Void, InformationCodes> {
        private InformationCodes result;
        private CardEntity item;

        private CardDao database;
        private OnResult callback;

        CardAddDb(CardEntity cardItem, CardDao database, OnResult onResult) {
            this.item = cardItem;
            this.database = database;
            this.callback = onResult;
        }


        @Override
        protected InformationCodes doInBackground(Void... voids) {
            CardEntity dbEntity = database.getCardByNumber(item.getNumber());
            if (dbEntity == null) {
                database.insert(item);
                result = InformationCodes.CARD_ADDED_SUCCESS;
            } else {
                result = InformationCodes.CARD_ALREADY_ADDED;
                item = dbEntity;
            }
            return null;
        }


        @Override
        protected void onPostExecute(InformationCodes aVoid) {
            super.onPostExecute(aVoid);
            callback.dataResult(result, item);

        }
    }

}


//send to server
       /* Context context = ClutchApplication.getApplication().getApplicationContext();
        File file = new File(cardItem.getPhotoFrontLocal());

        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), reqFile);
        CardItemService cardItemService = RetrofitManager.getInstance().apiRetrofit.create(CardItemService.class);
        cardItemService.postImage(body).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String url = response.body().string();
                    cardItem.setFacePhoto(url);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String jsonCredentials = new Gson().toJson(cardItem);
                cardItemService.postCardItem(cardItem).enqueue(new Callback<CardItem>() {
                    @Override
                    public void onResponse(Call<CardItem> call, Response<CardItem> response) {
                    }

                    @Override
                    public void onFailure(Call<CardItem> call, Throwable t) {
                    }
                });

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });*/