package com.devas.bear.clutchclient.fragment;

import android.annotation.SuppressLint;
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

import com.devas.bear.clutchclient.addcard.ActivityAddCard;
import com.devas.bear.clutchclient.R;
import com.devas.bear.clutchclient.adapters.CardsAdapter;
import com.devas.bear.clutchclient.model.CardModel;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by script972 on 09.05.2017.
 */

@SuppressLint("ValidFragment")
public class MyCardsFragment extends Fragment {
    private View view;
    private final int LAYOUT=R.layout.my_cards_fragment;
    private RecyclerView rcv;
    private List<CardModel> cardModels;
    private TextView text;

    private FloatingActionButton fab;



    public static MyCardsFragment getInstance(){
        Bundle args = new Bundle();
        MyCardsFragment fragment = new MyCardsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(LAYOUT, container, false);
        initView();
        return view;

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
                Intent intent=new Intent(getContext(), ActivityAddCard.class);
                startActivity(intent);
            }
        });
    }


    private void initCards() {

        mocke();
        rcv= (RecyclerView) view.findViewById(R.id.recycler_view_my_cards);
        CardsAdapter cardsAdapter=new CardsAdapter(this.getContext(), cardModels);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this.getContext(), 2);
        rcv.setLayoutManager(mLayoutManager);
        rcv.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        rcv.setItemAnimator(new DefaultItemAnimator());
        rcv.setAdapter(cardsAdapter);
        cardsAdapter.notifyDataSetChanged();
        Log.i("checkcard","set adapter ok");

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

    private void mocke() {
        cardModels=new ArrayList<>();
        cardModels.add(new CardModel("Addidas", 12, "img1"));
        cardModels.add(new CardModel("Puma", 2, "img2"));
        cardModels.add(new CardModel("Mack", 33, "img3"));
        cardModels.add(new CardModel("Card1", 30, "img3"));
        cardModels.add(new CardModel("Card2", 37, "img3"));
        cardModels.add(new CardModel("Card3", 31, "img3"));
        cardModels.add(new CardModel("Card4", 32, "img3"));
        cardModels.add(new CardModel("Card5", 34, "img3"));
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
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
