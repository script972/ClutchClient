package com.script972.clutchclient.ui.activities.card;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.script972.clutchclient.R;
import com.script972.clutchclient.domain.api.model.api.Company;
import com.script972.clutchclient.helpers.IntentHelpers;
import com.script972.clutchclient.mvp.contracts.CompanyListContract;
import com.script972.clutchclient.mvp.impl.CompanyListPresenterImpl;
import com.script972.clutchclient.ui.activities.BaseActivity;
import com.script972.clutchclient.ui.adapters.CompanyListAdapter;

import java.util.ArrayList;
import java.util.List;


public class ActivityListCompany extends BaseActivity implements CompanyListContract.View {

    //outlets
    private Toolbar toolbar;
    private SearchView searchView;
    private Button btnAddCard;

    private RecyclerView rvCompanyList;


    private CompanyListAdapter adapter;
    //get info from server and not change
    private ArrayList<Company> companiesListFromServer;

    private ArrayList<Company> companies;

    private CompanyListPresenterImpl presenter = new CompanyListPresenterImpl(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_card);
        super.initCommonView();
        initView();
        presenter.refreshCompanyList();
        super.showProgressDialog();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    /**
     * Method for init Outlets
     */
    private void initView() {
        initToolBar();
        initRecycler();
        btnAddCard = findViewById(R.id.btn_add_company);
        btnAddCard.setOnClickListener(clicker);
    }

    /**
     * Method for initRecyclerView for show list of company
     */
    private void initRecycler() {
        this.rvCompanyList = (RecyclerView) findViewById(R.id.rv_company_list);

        LinearLayoutManager llm = new LinearLayoutManager(this.getApplicationContext());
        llm.setOrientation(RecyclerView.VERTICAL);
        rvCompanyList.setLayoutManager(llm);
        this.companies = new ArrayList<>();
        this.companiesListFromServer = new ArrayList<>();
        this.adapter = new CompanyListAdapter(this.getApplicationContext(), this.companies);
        rvCompanyList.setAdapter(adapter);
        rvCompanyList.setClickable(true);
        rvCompanyList.addOnItemTouchListener(new RecyclerItemListener(getApplicationContext(), rvCompanyList,
                new RecyclerItemListener.RecyclerTouchListener() {
                    public void onClickItem(View v, int position) {
                        IntentHelpers.INSTANCE.pushAddCardActivity(ActivityListCompany.this);
                    }

                    public void onLongClickItem(View v, int position) {

                    }
                }));
    }

    /**
     * Method for init Toolbar
     */
    private void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //color of button back
        toolbar.getNavigationIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_card_list_toolbar, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<Company> companiestemp = new ArrayList<Company>();
                companiestemp.addAll(companies);
                companies.clear();

                if (newText.equals("")) {
                    companies.addAll(companiesListFromServer);
                    adapter.notifyDataSetChanged();
                    return false;
                }


                for (int i = 0; i < companiestemp.size(); i++) {
                    if (companiestemp.get(i).getTitle().toUpperCase().contains(newText.toUpperCase()))
                        companies.add(companiestemp.get(i));
                }
                adapter.notifyDataSetChanged();

                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_favorite:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void refreshDataCompany(List<Company> companyList) {
        this.companiesListFromServer.clear();
        this.companiesListFromServer.addAll(companyList);
        this.companies.clear();
        this.companies.addAll(companyList);
        this.adapter.notifyDataSetChanged();
        super.hideProgressDialog();

    }

    @Override
    public void onFailureGetData(Throwable t) {
        // this.rvCompanyList.setVisibility(View.GONE);
        super.hideProgressDialog();
    }


    /**
     * Method for added for Company
     */
    private void addNewCompany() {

    }


    //callbacks
    final View.OnClickListener clicker = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_add_company:
                    addNewCompany();
                    break;
            }

        }
    };


}
