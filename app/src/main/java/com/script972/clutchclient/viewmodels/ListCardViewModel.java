package com.script972.clutchclient.viewmodels;

import android.util.Pair;

import com.script972.clutchclient.domain.repository.CardRepository;
import com.script972.clutchclient.domain.repository.impl.CardRepositoryImpl;
import com.script972.clutchclient.ui.model.CardItem;
import com.script972.clutchclient.ui.model.InformationCodes;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

public class ListCardViewModel extends ViewModel {

    private CardRepository cardRepository;
    private LiveData<List<CardItem>> liveData;


    public ListCardViewModel() {
        this.cardRepository = new CardRepositoryImpl();
        this.liveData = cardRepository.getAllCard();
    }

    public LiveData<List<CardItem>> observeLiveData() {
        return liveData;
    }



}
