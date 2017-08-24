package com.devas.bear.clutchclient;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.devas.bear.clutchclient.adapters.TabsPagerFragmentAdapter;

public class MainActivity extends AppCompatActivity {


    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ViewPager viewPager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppDefault);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar();
        initNavigationView();
        initTab();
       // initFloating();

    }




    private void initToolbar() {
        toolbar=(Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });

        toolbar.inflateMenu(R.menu.menu);
    }

    private void initNavigationView() {
        drawerLayout= (DrawerLayout) findViewById(R.id.drawerLayout);

        //Кнопка без  свайпа
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView= (NavigationView) findViewById(R.id.navigation);
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

                }
                return true;
            }
        });

    }


    private void initTab() {
        viewPager= (ViewPager) findViewById(R.id.viewPager);
        TabsPagerFragmentAdapter adapter=new TabsPagerFragmentAdapter(getSupportFragmentManager(), getApplicationContext());
        viewPager.setAdapter(adapter);


        TabLayout tabLayout= (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        //icon in adapter cant add???
        tabLayout.getTabAt(0).setIcon(R.mipmap.ic_cards_outline_grey600_36dp);
        tabLayout.getTabAt(1).setIcon(R.mipmap.ic_cards_variant_grey600_36dp);


    }


    private void showMyCardTab(){
        viewPager.setCurrentItem(Constants.TAB_ONE);
    }

    private void showOnlineCardTab() {
        viewPager.setCurrentItem(Constants.TAB_TWO);
    }
}
