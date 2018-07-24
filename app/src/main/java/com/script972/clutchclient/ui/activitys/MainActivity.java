package com.script972.clutchclient.ui.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.script972.clutchclient.Constants;
import com.script972.clutchclient.R;
import com.script972.clutchclient.helpers.DialogHelper;
import com.script972.clutchclient.helpers.PrefHelper;
import com.script972.clutchclient.ui.activitys.card.ActivityListCompany;
import com.script972.clutchclient.ui.adapters.TabsPagerFragmentAdapter;

public class MainActivity extends BaseActivity {


    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ViewPager viewPager;
    private SearchView searchView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
       // initFloating();

    }

    private void initView() {
        initToolbar();
        initNavigationView();
        initTab();
    }

    private void initToolbar() {
        toolbar=(Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.gear:startActivity(new Intent(MainActivity.this, SettingActivity.class)); break;
                }
                return false;
            }
        });

        toolbar.inflateMenu(R.menu.menu);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);


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
                    case R.id.menuAddCard:
                        showAddCard();
                        break;
                    case R.id.menuDiscountPoint:
                        showMapsDiscount();
                        break;
                    case R.id.setting_menu:
                        showMenu();
                        break;
                    case R.id.menu_log_out:
                        logOut();
                }
                return true;
            }
        });

    }

    private void logOut() {
        DialogHelper.logOutDialog(this);

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
        TabsPagerFragmentAdapter adapter=new TabsPagerFragmentAdapter(getSupportFragmentManager(), getApplicationContext());
        viewPager.setAdapter(adapter);


        TabLayout tabLayout= (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        //icon in adapter cant add???
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_cards_outline_grey600_36dp);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_cards_variant_grey600_36dp);


    }


    private void showMyCardTab(){
        viewPager.setCurrentItem(Constants.TAB_ONE);
    }

    private void showOnlineCardTab() {
        viewPager.setCurrentItem(Constants.TAB_TWO);
    }




}
