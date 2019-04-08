package com.script972.clutchclient.mappers;

import com.script972.clutchclient.domain.database.entity.CardEntity;
import com.script972.clutchclient.ui.model.CardItem;

public class CardMapper {


    public static CardItem entityToUi(CardEntity cardItem) {
        CardItem item = new CardItem();
        item.setCardNumber(cardItem.getNumber());
        item.setTitle(cardItem.getTitle());
        item.setComment(cardItem.getComment());
        item.setPhotoFront(cardItem.getPhotoFrontLocal());
        item.setPhotoBack(cardItem.getPhotoBackLocal());
        return item;
    }

    public static CardEntity uiToEntity(CardItem uiModel) {
        CardEntity cardEntity = new CardEntity();
        cardEntity.setTitle(uiModel.getTitle());
        cardEntity.setComment(uiModel.getComment());
        cardEntity.setNumber(uiModel.getCardNumber());
        cardEntity.setPhotoFrontLocal(uiModel.getPhotoFront());
        cardEntity.setPhotoBackLocal(uiModel.getPhotoBack());
        return cardEntity;
    }
}
