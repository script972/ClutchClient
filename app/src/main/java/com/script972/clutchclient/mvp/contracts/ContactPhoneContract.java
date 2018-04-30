package com.script972.clutchclient.mvp.contracts;

import com.script972.clutchclient.model.ContactModel;
import com.script972.clutchclient.ui.adapters.ContactModelAdapter;

import java.util.List;

public interface ContactPhoneContract {

    interface Presenter{

        /**
         * Method wich is item of licycle activity
         */
        void onStart(Long itemCardId);

    }

    interface View{

        /**
         * Method wich call in view when presenter initial all data
         *
         * @param contactAdapter
         * @param contactModels
         */
        void fillRecyclerView(ContactModelAdapter contactAdapter, List<ContactModel> contactModels);


    }

}
