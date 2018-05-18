package com.script972.clutchclient.mvp.contracts;

import com.script972.clutchclient.model.api.CardItem;

import java.util.List;

public interface CardContract {

    interface Presenter{
        /**
         * Method wich is item of licycle activity
         */
        void onStart();

    }

    interface View{

        /**
         * Method wich call in view when presenter initial all data and fill this data
         *
         * @param cardList
         */
        void fillCards( List<CardItem> cardList);


    }

}
