package com.devas.bear.clutchclient.adapters;

import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.devas.bear.clutchclient.R;
import com.devas.bear.clutchclient.fragment.MyCardsFragment;

/**
 * Created by script972 on 09.05.2017.
 */

public class TabsPagerFragmentAdapter extends FragmentPagerAdapter {
    private int [] tabs;

    public TabsPagerFragmentAdapter(FragmentManager fm) {
        super(fm);
        tabs=new int[]{
                R.string.MyCards,
                R.string.OnlineCards
        };

    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return MyCardsFragment.getInstance();
            case 1: return MyCardsFragment.getInstance();
            case 2: return MyCardsFragment.getInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return tabs.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return "ds";
    }
}
