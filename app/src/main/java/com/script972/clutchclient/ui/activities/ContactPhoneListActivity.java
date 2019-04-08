package com.script972.clutchclient.ui.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;

import com.script972.clutchclient.R;
import com.script972.clutchclient.helpers.DialogHelper;
import com.script972.clutchclient.domain.api.model.ContactModel;
import com.script972.clutchclient.mvp.contracts.ContactPhoneContract;
import com.script972.clutchclient.mvp.impl.ContactPhonePresenterImpl;
import com.script972.clutchclient.ui.adapters.ContactModelAdapter;

import java.util.List;


public class ContactPhoneListActivity extends AppCompatActivity implements ContactPhoneContract.View {

    Toolbar tollbar;

    RecyclerView recyclerView;

    private ContactPhoneContract.Presenter presenter=new ContactPhonePresenterImpl(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_phone_list);
        tollbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.contact_phone);
        tollbar.setNavigationOnClickListener(tollbarClicker);
        initView();
    }


    @Override
    protected void onStart() {
        super.onStart();
        this.presenter.onStart(getItemCard());

    }

    /**
     * Method for initial view
     */
    private void initView() {
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    /**
     * Method wich call in view when presenter initial all data
     *
     * @param contactAdapter
     * @param contactModels
     */
    @Override
    public void fillRecyclerView(ContactModelAdapter contactAdapter, List<ContactModel> contactModels) {
        DialogHelper.getProgressDialog(this).dismiss();
        this.recyclerView.setAdapter(contactAdapter);
    }


    //callbacks
    View.OnClickListener tollbarClicker = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };

    /**
     * Method wich get id item card from intent
     * @return
     */
    public Long getItemCard() {
        return getIntent().getExtras().getLong("itemCard");
    }
}
