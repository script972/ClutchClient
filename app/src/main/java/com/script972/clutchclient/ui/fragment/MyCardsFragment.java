package com.script972.clutchclient.ui.fragment;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.script972.clutchclient.R;
import com.script972.clutchclient.core.ClutchApplication;
import com.script972.clutchclient.databinding.MyCardsFragmentBinding;
import com.script972.clutchclient.helpers.DeviceHelper;
import com.script972.clutchclient.helpers.IntentHelpers;
import com.script972.clutchclient.helpers.PrefHelper;
import com.script972.clutchclient.mvp.contracts.CardContract;
import com.script972.clutchclient.mvp.impl.CardPresenterImpl;
import com.script972.clutchclient.ui.adapters.CardsAdapter;
import com.script972.clutchclient.ui.model.CardItem;
import com.script972.clutchclient.viewmodels.ListCardViewModel;

import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;


/**
 * Created by script972 on 09.05.2017.
 */

public class MyCardsFragment extends Fragment implements CardContract.View {
    private List<CardItem> data;
    private CardsAdapter cardsAdapter;
    private RecyclerView rcv;
    private GifImageView noData;
    private MyCardsFragmentBinding binding;

    private FloatingActionButton fab;

    // vars for help cheking if change span in settings
    private int saveOldSpan = 0;

    private ViewModel viewModel;


    //presenter
    private final CardContract.Presenter presenter = new CardPresenterImpl(this);


    public static MyCardsFragment getInstance() {
        Bundle args = new Bundle();
        MyCardsFragment fragment = new MyCardsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.binding = DataBindingUtil.inflate(
                inflater, R.layout.my_cards_fragment, container, false);
        //this.viewModel = ViewModelProviders.of(this).get(AddCardViewModel.class);
        initView();
        return binding.getRoot();
        /*
        * this.binding = DataBindingUtil.setContentView(this, R.layout.activity_add_card);
        this.viewModel = ViewModelProviders.of(this).get(AddCardViewModel.class);
        *
        * */
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(ListCardViewModel.class);
        ((ListCardViewModel) viewModel).observeLiveData().observe(this, this::handleModification);
    }

    private void handleModification(List<CardItem> cardItems) {
        this.data.clear();
        this.data.addAll(cardItems);
        this.cardsAdapter.notifyDataSetChanged();
    }

    /**
     * Called when the Fragment is visible to the user.  This is generally
     * tied to {@link Activity#onStart() Activity.refreshCompanyList} of the containing
     * Activity's lifecycle.
     */
    @Override
    public void onStart() {
        super.onStart();
        presenter.onStart();
        if (saveOldSpan != 0) {
            int value = PrefHelper.getDisplayCardView(ClutchApplication.getApplication().getApplicationContext());
            if (value != saveOldSpan) {
                initCards();
            }
        }

    }

    private void initView() {
        data = new ArrayList<>();//del
        noData = binding.noData;
        initCards();
        binding.fab.setOnClickListener(view -> IntentHelpers.openAddCardActivity(getContext()));


    }


    private void initCards() {
        rcv = binding.rvViewMyCards;
        cardsAdapter = new CardsAdapter(data);
        saveOldSpan = PrefHelper.getDisplayCardView(ClutchApplication.getApplication().getApplicationContext());
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this.getContext(), saveOldSpan);
        rcv.setLayoutManager(mLayoutManager);
        rcv.addItemDecoration(new GridSpacingItemDecoration(saveOldSpan, DeviceHelper.dpToPx(0), true));
        rcv.setItemAnimator(new DefaultItemAnimator());
        rcv.setAdapter(cardsAdapter);
        cardsAdapter.notifyDataSetChanged();
        rcv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 || dy < 0 && fab.isShown()) {
                    // fab.hide();
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_INDICATOR_TOP) {/*SCROLL_INDICATOR_BOTTOM*/
//                    fab.show();
                }
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }


    /**
     * Method wich call in view when presenter initial all data and fill this data
     *
     * @param cardList
     */
    @Override
    public void fillCards(List<CardItem> cardList) {
        if (cardList == null || cardList.size() == 0) {
            return;
        }
       /* if (!cardList.get(0).getCodeError().equals(0)) {
            rcv.setVisibility(View.GONE);
            noData.setVisibility(View.VISIBLE);
            return;
        }*/

        data.clear();
        data.addAll(cardList);
        cardsAdapter.notifyDataSetChanged();
    }


    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }
}
