package com.script972.clutchclient.ui.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
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
import com.script972.clutchclient.ui.activities.card.ActivityListCompany;
import com.script972.clutchclient.ui.adapters.TabsPagerFragmentAdapter;

public class MainActivity extends BaseActivity {


    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ViewPager viewPager;
    private SearchView searchView;

    private int [] tabTitle = new int[]{
            R.string.my_cards,
            R.string.OnlineCards
    };
    private int[] unreadCount={0,5,0};

    private  TabLayout tabLayout;



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
        toolbar=(Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_gear: startActivity(new Intent(MainActivity.this, SettingActivity.class)); break;
                }
                return false;
            }
        });
        toolbar.inflateMenu(R.menu.main_toolbar_menu);
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



    private void initNavigationView() {
        drawerLayout= (DrawerLayout) findViewById(R.id.drawerLayout);
        NavigationView navigationView= (NavigationView) findViewById(R.id.navigation);
        View headerView = navigationView.inflateHeaderView(R.layout.navigation_header);
        ImageView imgView = (ImageView)headerView.findViewById(R.id.close_drawable_menu);
        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawers();
            }
        });
        //burger button
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            toggle.getDrawerArrowDrawable().setColor(getColor(R.color.colorWhite));
        } else {
            toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.colorWhite));
        }

        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawerLayout.closeDrawers();
                switch (item.getItemId()){
                    case R.id.menuMyCards:
                        showMyCardTab();
                        break;

                    case R.id.menuOnlineCards:
                        showOnlineCardTab();
                        break;
                    case R.id.menuAddCard:
                        showAddCard();
                        break;
                    case R.id.menuDiscountPoint:
                        showMapsDiscount();
                        break;
                    case R.id.setting_menu:
                        showMenu();
                        break;
                }
                return true;
            }
        });

    }

    private void logOut() {
        DialogHelper.logOutDialog(this, MainActivity.this);
    }

    private void showMenu() {
        Intent intent=new Intent(this, SettingActivity.class);
        startActivity(intent);
    }

    private void showMapsDiscount() {
        Intent intent=new Intent(this, DiscountMapsActivity.class);
        startActivity(intent);

    }

    private void showAddCard() {
        Intent intent=new Intent(this,ActivityListCompany.class);
        startActivity(intent);
    }


    private void initTab() {
        viewPager= (ViewPager) findViewById(R.id.viewPager);
        setupViewPager(viewPager);
        setupTabIcons();
    }


    private void showMyCardTab(){
        viewPager.setCurrentItem(0);
    }

    private void showOnlineCardTab() {
        viewPager.setCurrentItem(1);
    }

    private void setupViewPager(ViewPager viewPager)
    {
        TabsPagerFragmentAdapter adapter = new TabsPagerFragmentAdapter(getSupportFragmentManager(), getApplicationContext());
        viewPager.setAdapter(adapter);

        tabLayout= (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(adapter);
    }

    private View prepareTabView(int pos) {
        View view = getLayoutInflater().inflate(R.layout.custom_tab,null);
        TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
        TextView tvCount = (TextView) view.findViewById(R.id.tv_count);
        tvTitle.setText(tabTitle[pos]);
        if(unreadCount[pos]>0)
        {
            tvCount.setVisibility(View.VISIBLE);
            tvCount.setText(""+unreadCount[pos]);
        }
        else
            tvCount.setVisibility(View.GONE);


        return view;
    }

    private void setupTabIcons()
    {
        for(int i=0;i<tabTitle.length;i++)
        {
            tabLayout.getTabAt(i).setCustomView(prepareTabView(i));
        }

    }


}
