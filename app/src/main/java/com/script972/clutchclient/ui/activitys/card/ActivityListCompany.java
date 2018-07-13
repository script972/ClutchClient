package com.script972.clutchclient.ui.activitys.card;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import com.artlite.bslibrary.helpers.preference.BSSharedPreferenceHelper;
import com.script972.clutchclient.R;
import com.script972.clutchclient.api.helpers.ApiClient;
import com.script972.clutchclient.api.service.CompanyService;
import com.script972.clutchclient.model.api.Company;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ActivityListCompany extends AppCompatActivity {

    private Toolbar toolbar;
    private  SearchView searchView;
    private RecyclerView companyList;
    private CompanyListAdapter adapter;
    private ArrayList<Company> companiesListFromServer;

    private ArrayList<Company> companies;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_card);
        initView();
    }

    private void initView() {
        initToolBar();
        initRecycler();


    }


    private void initRecycler() {
        companyList= (RecyclerView) findViewById(R.id.company_list);

        companyList.setClickable(true);
        companyList.addOnItemTouchListener(new RecyclerItemListener(getApplicationContext(), companyList,
                new RecyclerItemListener.RecyclerTouchListener() {
                    public void onClickItem(View v, int position) {
                        Intent intent=new Intent(ActivityListCompany.this, ActivityAddCard.class);
                        startActivity(intent);


                    }

                    public void onLongClickItem(View v, int position) {

                    }
                }));

        mockeLoadData();

    }

    private void mockeLoadData() {
        CompanyService companyService= ApiClient.getClient().create(CompanyService.class);
        companyService.getCompanyList(BSSharedPreferenceHelper.getString(getApplicationContext(), "token"))
                .enqueue(new Callback<List<Company>>() {
            @Override
            public void onResponse(@NonNull Call<List<Company>> call, @NonNull Response<List<Company>> response) {

            }

            @Override
            public void onFailure(@NonNull Call<List<Company>> call, @NonNull Throwable t) {

            }
        });


        companies=new ArrayList<>();
        companies.add(new Company( "Addidas", "icon1"));
        companies.add(new Company("Puma", "icon2"));
        companies.add(new Company( "Breshka", "icon3"));
        companies.add(new Company( "BreshkaRR", "icon3RR"));
        companies.add(new Company( "Pulombir", "icon4"));
        companies.add(new Company( "Lacost", "icon5"));
        Collections.sort(companies);
        companiesListFromServer=new ArrayList<>();
        companiesListFromServer.addAll(companies);

        adapter=new CompanyListAdapter(this.getApplicationContext(), companies);
        LinearLayoutManager llm = new LinearLayoutManager(this.getApplicationContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        companyList.setLayoutManager(llm);
        companyList.setAdapter(adapter);
    }

    private void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);//цвет стрелки назад
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
        searchView =
                (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.i("search", "onQueryTextSubmit "+query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.i("search", "onQueryTextChange "+newText);
                List<Company> companiestemp=new ArrayList<Company>();
                companiestemp.addAll(companies);
                companies.clear();

                if(newText.equals("")){
                    companies.addAll(companiesListFromServer);
                    adapter.notifyDataSetChanged();
                    return false;
                }


                for (int i = 0; i < companiestemp.size(); i++) {
                    if(companiestemp.get(i).getTitle().toUpperCase().contains(newText.toUpperCase()))
                        companies.add(companiestemp.get(i));
                }
                adapter.notifyDataSetChanged();
                Log.i("search", "adapterNofityData");

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

    public void addNewCompany(View view) {

    }
}
