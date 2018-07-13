package com.script972.clutchclient.ui.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.script972.clutchclient.R;
import com.script972.clutchclient.model.api.CardItem;
import com.script972.clutchclient.mvp.contracts.CardContract;
import com.script972.clutchclient.mvp.impl.CardPresenterImpl;
import com.script972.clutchclient.ui.activitys.card.ActivityListCompany;
import com.script972.clutchclient.ui.adapters.CardsAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by script972 on 09.05.2017.
 */

@SuppressLint("ValidFragment")
public class MyCardsFragment extends Fragment implements CardContract.View{
    private View view;
    private  List<CardItem> cardModels;
    private CardsAdapter cardsAdapter;

    private FloatingActionButton fab;
    private final CardContract.Presenter presenter = new CardPresenterImpl(this);



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
        initView();
        return view;
    }

    /**
     * Called when the Fragment is visible to the user.  This is generally
     * tied to {@link Activity#onStart() Activity.onStart} of the containing
     * Activity's lifecycle.
     */
    @Override
    public void onStart() {
        super.onStart();
        presenter.onStart();

    }

    private void initView() {
        cardModels=new ArrayList<>();//del
        initCards();
        initFloating();

    }

    private void initFloating() {
        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                Intent intent=new Intent(getContext(), ActivityListCompany.class);
                startActivity(intent);
            }
        });
    }


    private void initCards() {
        RecyclerView rcv = (RecyclerView) view.findViewById(R.id.recycler_view_my_cards);
        cardsAdapter=new CardsAdapter(this.getContext(), cardModels);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this.getContext(), 2);
        rcv.setLayoutManager(mLayoutManager);
        rcv.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        rcv.setItemAnimator(new DefaultItemAnimator());
        rcv.setAdapter(cardsAdapter);
        cardsAdapter.notifyDataSetChanged();

        rcv.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy){
                Log.i("checkcard","dx="+dx+" dy="+dy);
                if (dy > 0 ||dy<0 && fab.isShown())
                    fab.hide();
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                Log.i("checkcard","newState="+newState);

                if (newState == RecyclerView.SCROLL_INDICATOR_TOP){/*SCROLL_INDICATOR_BOTTOM*/
                    fab.show();
                }
                super.onScrollStateChanged(recyclerView, newState);
            }
        });


    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    /**
     * Method wich call in view when presenter initial all data and fill this data
     *
     * @param cardList
     */
    @Override
    public void fillCards(List<CardItem> cardList) {
        if(cardList==null || cardList.size()==0){
            return;
        }
        cardModels.clear();
        cardModels.addAll(cardList);
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
