package com.script972.clutchclient.ui.activities;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import androidx.core.view.MenuItemCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.script972.clutchclient.R;
import com.script972.clutchclient.helpers.DialogHelper;
import com.script972.clutchclient.helpers.IntentHelpers;
import com.script972.clutchclient.ui.adapters.TabsPagerFragmentAdapter;

public class MainActivity extends BaseActivity {


    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ViewPager viewPager;
    private SearchView searchView;

    private int[] tabTitle = new int[]{
            R.string.my_cards,
            R.string.online_cards
    };
    private int[] unreadCount = {0, 5, 0};

    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        super.initCommonView();
        initView();
    }

    private void initView() {
        initToolbar();
        initNavigationView();
        initTab();
    }

    /**
     * Method for init toolbar
     */
    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_toolbar_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_gear) {
            IntentHelpers.pushSettingsActivity(this);
        }
        return true;
    }


    private void initNavigationView() {
        drawerLayout = findViewById(R.id.drawerLayout);
        NavigationView navigationView = findViewById(R.id.navigation);
        View headerView = navigationView.inflateHeaderView(R.layout.navigation_header);
        ImageView imgView = headerView.findViewById(R.id.close_drawable_menu);
        imgView.setOnClickListener(v -> drawerLayout.closeDrawers());
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        toggle.getDrawerArrowDrawable().setColor(getColor(R.color.colorWhite));
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(item -> {
            drawerLayout.closeDrawers();
            switch (item.getItemId()) {
                case R.id.menuMyCards:
                    viewPager.setCurrentItem(0);
                    break;

                case R.id.menuOnlineCards:
                    viewPager.setCurrentItem(1);
                    break;

                case R.id.menuAddCard:
                    IntentHelpers.pushCompanyListActivity(this);
                    break;

                case R.id.menuDiscountPoint:
                    IntentHelpers.pushMapsDiscount(this);
                    break;

                case R.id.setting_menu:
                    IntentHelpers.pushSettingsActivity(this);
                    break;
            }
            return true;
        });

    }

    private void logOut() {
        DialogHelper.logOutDialog(this, MainActivity.this);
    }

    private void initTab() {
        viewPager = findViewById(R.id.viewPager);
        setupViewPager(viewPager);
        setupTabIcons();
    }

    private void setupViewPager(ViewPager viewPager) {
        TabsPagerFragmentAdapter adapter = new TabsPagerFragmentAdapter(getSupportFragmentManager(), getApplicationContext());
        viewPager.setAdapter(adapter);

        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(adapter);
    }

    private View prepareTabView(int pos) {
        View view = getLayoutInflater().inflate(R.layout.custom_tab, null);
        TextView tvTitle = view.findViewById(R.id.tv_title);
        TextView tvCount = view.findViewById(R.id.tv_count);
        tvTitle.setText(tabTitle[pos]);
        if (unreadCount[pos] > 0) {
            tvCount.setVisibility(View.VISIBLE);
            tvCount.setText("" + unreadCount[pos]);
        } else
            tvCount.setVisibility(View.GONE);


        return view;
    }

    private void setupTabIcons() {
        for (int i = 0; i < tabTitle.length; i++) {
            tabLayout.getTabAt(i).setCustomView(prepareTabView(i));
        }
    }


}
