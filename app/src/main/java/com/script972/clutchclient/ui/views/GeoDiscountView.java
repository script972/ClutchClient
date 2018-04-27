package com.script972.clutchclient.ui.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.artlite.bslibrary.annotations.FindViewBy;
import com.artlite.bslibrary.ui.view.BSView;
import com.script972.clutchclient.R;
import com.script972.clutchclient.callbacks.GeoListCallbacks;
import com.script972.clutchclient.model.api.Company;
import com.script972.clutchclient.mvp.contracts.GeoListContract;
import com.script972.clutchclient.mvp.impl.GeoDiscountPresenterImp;
import com.script972.clutchclient.ui.adapters.GeoListAdapter;
import com.script972.clutchclient.ui.adapters.callbacks.RecyclerTouchListener;

import java.util.List;


public class GeoDiscountView extends BSView implements GeoListContract.View {

    //outlets
    @FindViewBy(id = R.id.geo_link)
    private RecyclerView rv;

    //presenters
    private GeoListContract.Presenter presenter=new GeoDiscountPresenterImp(this);
    private Context context;
    private GeoListCallbacks callbacks;

    //vars
    @Nullable
    private List<Company> companiesList;


    /**
     * Constructor which provide the create {@link BSView} from
     *
     * @param context instance of {@link Context}
     * @param attrs   instance of {@link AttributeSet}
     */
    public GeoDiscountView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
    }

    /**
     * Constructor which provide the create {@link BSView} from
     *
     * @param context instance of {@link Context}
     */
    public GeoDiscountView(Context context) {
        super(context);
    }

    /**
     * Method which provide the getting of the layout ID
     *
     * @return layout ID
     */
    @Override
    protected int getLayoutId() {
        return R.layout.discount_geo_list_view;
    }

    /**
     * Method which provide interface linking
     */
    @Override
    protected void onLinkInterface() {

    }

    /**
     * Method which provide the creating of the {@link View}
     */
    @Override
    protected void onCreateView() {
        LinearLayoutManager llm = new LinearLayoutManager(context);
        rv.setLayoutManager(llm);
        rv.setItemAnimator(new DefaultItemAnimator());

    }

    /**
     * Method for setting callback of view
     *
     * @param callbacks
     */
    public void setCallbacks(GeoListCallbacks callbacks) {
        this.callbacks = callbacks;
    }

    @Override
    public void onMapReady() {
        presenter.getListGeoCompany();
    }


    /**
     * Method wich filling company list
     *
     * @param geoListAdapter
     * @param lists
     */
    @Override
    public void fillListGeoCompany(GeoListAdapter geoListAdapter, final List<Company> lists) {
        rv.setAdapter(geoListAdapter);
        geoListAdapter.notifyDataSetChanged();
        callbacks.returnWorkerList(lists);
        this.companiesList=lists;
        //click listeners of item recycler view
        rv.addOnItemTouchListener(recyclerTouchListener);

    }


    /**
     * Method wich focus on item company
     *
     * @param marker for focusing
     */
    @Override
    public void companyFocus(Company marker) {
        if(companiesList==null || companiesList.size()==0)
            return;

        for (int i = 0; i < companiesList.size(); i++) {
            if(companiesList.get(i).equals(marker)){
              //  rv.scrollToPosition(i);
                rv.smoothScrollToPosition(i);
                break;
            }
        }
    }


    // Listeners

    /**
     * Click listeners of item recycler view
     */
    RecyclerTouchListener recyclerTouchListener = new RecyclerTouchListener(context, rv, new RecyclerTouchListener.ClickListener() {
        @Override
        public void onClick(View view, int position) {
            callbacks.chooseItemInList(companiesList.get(position));
        }

        @Override
        public void onLongClick(View view, int position) {

        }
    });


}
