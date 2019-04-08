package com.script972.clutchclient.ui.activities;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import android.util.Log;

import com.script972.clutchclient.R;
import com.script972.clutchclient.domain.api.model.LocationPosition;
import com.script972.clutchclient.ui.adapters.SettingCountryAdapter;

import java.util.ArrayList;
import java.util.List;

public class SettingCountryActivity extends BaseActivity {

    private RecyclerView rvCountry;
    private SearchView searcher;

    private SettingCountryAdapter countryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_country);

        initView();
    }

    private void initView() {
        initRvCountry();
        initSearcher();
    }


    private void initSearcher() {
        this.searcher = findViewById(R.id.searcher);

        this.searcher.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    /**
     * Method wich init country lists
     */
    private void initRvCountry() {
        this.rvCountry = (RecyclerView) findViewById(R.id.country_list);
        List<LocationPosition> positions = countryMock();

        countryAdapter = new SettingCountryAdapter(positions);
        this.rvCountry.setAdapter(countryAdapter);

        LinearLayoutManager llm = new LinearLayoutManager(this.getApplicationContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rvCountry.setLayoutManager(llm);

        countryAdapter.notifyDataSetChanged();
        Log.i("recyclerproblem", "setAdapter");


    }

    private List<LocationPosition> countryMock() {
        List<LocationPosition> country = new ArrayList<>();
        LocationPosition loc1=new LocationPosition();
        loc1.setCountry("new LocationPosition()");
        LocationPosition loc2=new LocationPosition();
        loc2.setCountry("new LocationPosition()2");
        country.add(loc1);
        country.add(loc2);
        return country;
    }
}
