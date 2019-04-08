package com.script972.clutchclient.domain.repository;

import android.util.Pair;

import com.script972.clutchclient.domain.database.entity.CardEntity;
import com.script972.clutchclient.ui.model.CardItem;
import com.script972.clutchclient.ui.model.InformationCodes;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public interface CardRepository {

    LiveData<Pair<InformationCodes, CardItem>> addNewCard(CardEntity cardItem);

    MutableLiveData<Pair<InformationCodes, CardItem>> updateCardEntity(CardEntity cardItem);

    LiveData<List<CardItem>> getAllCard();

}
