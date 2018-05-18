package com.script972.clutchclient.mvp.impl;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.script972.clutchclient.api.helpers.ApiItemCardHelper;
import com.script972.clutchclient.api.service.CardItemService;
import com.script972.clutchclient.helpers.ContactHelper;
import com.script972.clutchclient.model.ContactModel;
import com.script972.clutchclient.model.api.CardItem;
import com.script972.clutchclient.mvp.contracts.ContactPhoneContract;
import com.script972.clutchclient.ui.activitys.ContactPhoneListActivity;
import com.script972.clutchclient.ui.adapters.ContactModelAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactPhonePresenterImpl implements ContactPhoneContract.Presenter {

    /**
     * View activity
     */
    private ContactPhoneContract.View view;


    //constructors
    /**
     * @param contactPhoneListActivity
     */
    public ContactPhonePresenterImpl(ContactPhoneListActivity contactPhoneListActivity) {
        this.view=contactPhoneListActivity;
    }

    /**
     * Method wich is item of licycle activity
     */
    @Override
    public void onStart(Long itemCardID) {
        //TODO server
        /*ApiItemCardHelper.getCardItem().getItemCard(itemCardID).enqueue(new Callback<List<CardItem>>() {
            @Override
            public void onResponse(Call<List<CardItem>> call, Response<List<CardItem>> response) {
                contactBookList(response.body());
            }

            @Override
            public void onFailure(Call<List<CardItem>> call, Throwable t) {

            }
        });*/


    }

    private void contactBookList(List<CardItem> cardItems) {
        //TODO  to server сравнить значение телефонов и поставить галочку клиент клатча или нет
        ContactHelper.getContacts((Context) view, new ContactHelper.OnContactCallback() {
            @Override
            public void onStart() {

            }

            @Override
            public void onResult(boolean isSuccess, @Nullable Exception exception, @NonNull List<ContactHelper.ContactContainer> contacts) {
                checkIsUserClient(contacts);
            }
        });
    }


    /**
     * Method wich ask server if client is user by phone number
     *
     * @param contacts - list of contacts
     */
    private void checkIsUserClient(List<ContactHelper.ContactContainer> contacts) {
        /*
        //TODO to server
        ApiUserHelper.getClientByPhoneNumbers().getUsersByPhoneNumber("+380936629627").enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {

            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });*/
        List<ContactModel> resultList=new ArrayList<>();
        for (int i = 0; i < contacts.size(); i++) {
            ContactModel contactModel=new ContactModel();
            contactModel.setName(contacts.get(i).name);
            contactModel.setPhone(contacts.get(i).phone);
            contactModel.setPhoto(contacts.get(i).photo);
            contactModel.setUser(true);
            resultList.add(contactModel);
        }

        ContactModelAdapter contactAdapter=new ContactModelAdapter(resultList);
        contactAdapter.notifyDataSetChanged();
        view.fillRecyclerView(contactAdapter, resultList);


    }
}
