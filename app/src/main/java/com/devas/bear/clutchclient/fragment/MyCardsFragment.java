package com.devas.bear.clutchclient.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.devas.bear.clutchclient.R;


/**
 * Created by script972 on 09.05.2017.
 */

@SuppressLint("ValidFragment")
public class MyCardsFragment extends Fragment {
    private View view;

    public static MyCardsFragment getInstance(){
        Bundle args = new Bundle();
        MyCardsFragment fragment = new MyCardsFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.my_cards_fragment, container, false);
        return view;
    }
}
