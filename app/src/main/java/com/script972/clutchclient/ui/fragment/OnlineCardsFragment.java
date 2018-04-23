package com.script972.clutchclient.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.script972.clutchclient.R;

/**
 * Created by script972 on 28.05.2017.
 */

@SuppressLint("ValidFragment")
public class OnlineCardsFragment extends Fragment {
    private View view;
    private final int LAYOUT= R.layout.online_cards_fragment;

    public static OnlineCardsFragment getInstance(){
        Bundle args = new Bundle();
        OnlineCardsFragment fragment = new OnlineCardsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(LAYOUT, container, false);
        return view;
    }
}