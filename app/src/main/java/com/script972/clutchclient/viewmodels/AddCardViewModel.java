package com.script972.clutchclient.viewmodels;

import android.util.Pair;

import com.script972.clutchclient.domain.database.entity.CardEntity;
import com.script972.clutchclient.domain.repository.CardRepository;
import com.script972.clutchclient.domain.repository.impl.CardRepositoryImpl;
import com.script972.clutchclient.ui.model.CardItem;
import com.script972.clutchclient.ui.model.InformationCodes;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

public class AddCardViewModel extends ViewModel {

    private CardRepository cardRepository;
    private MediatorLiveData<Pair<InformationCodes, CardItem>> liveData;

    public AddCardViewModel() {
        this.cardRepository = new CardRepositoryImpl();
        this.liveData = new MediatorLiveData<>();
    }

    public LiveData<Pair<InformationCodes, CardItem>> observeLiveData() {
        return liveData;
    }

    /**
     * Add new card to database
     *
     * @param cardItem
     * @return
     */
    public void addNewCard(CardEntity cardItem) {
        liveData.addSource(cardRepository.addNewCard(cardItem), liveData::setValue);
    }

    public void updateCard(CardEntity cardItem) {
        liveData.addSource(cardRepository.updateCardEntity(cardItem), liveData::setValue);
    }

    public void findOneCardById(long cardId){
        liveData.addSource(cardRepository.findCardById(cardId), liveData::setValue);
    }
}
