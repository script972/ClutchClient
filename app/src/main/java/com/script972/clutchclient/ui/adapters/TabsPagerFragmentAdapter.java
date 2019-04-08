package com.script972.clutchclient.ui.adapters;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.script972.clutchclient.R;
import com.script972.clutchclient.ui.fragment.MyCardsFragment;
import com.script972.clutchclient.ui.fragment.OnlineCardsFragment;

/**
 * Created by script972 on 09.05.2017.
 */

public class TabsPagerFragmentAdapter extends FragmentPagerAdapter {
    private int [] tabs;
    private Context context;

    public TabsPagerFragmentAdapter(FragmentManager fm, Context mainActivity) {
        super(fm);
        tabs=new int[]{
                R.string.my_cards,
                R.string.OnlineCards
        };

        context=mainActivity;


    }


    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return MyCardsFragment.getInstance();
            case 1: return OnlineCardsFragment.getInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return tabs.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return context.getResources().getString(tabs[position]) ;
    }






}
