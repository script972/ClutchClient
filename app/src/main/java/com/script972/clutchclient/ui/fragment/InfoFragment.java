package com.script972.clutchclient.ui.fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.script972.clutchclient.R;
import com.script972.clutchclient.helpers.DataTransferHelper;
import com.script972.clutchclient.ui.model.CardItem;
import com.squareup.picasso.Picasso;

import java.net.URI;

public class InfoFragment extends Fragment {

    private static InfoFragment fragment;


    //outlets
    private View view;
    private ImageView front;
    private ImageView back;

    public static InfoFragment getInstance() {
        if (fragment == null) {
            fragment = new InfoFragment();
        }
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.info_card_fragment, container, false);
        front = view.findViewById(R.id.photo_front);
        back = view.findViewById(R.id.photo_back);
        //  initView();
        return view;
    }

    /**
     * Called when the Fragment is visible to the user.  This is generally
     * tied to {@link Activity#onStart() Activity.refreshCompanyList} of the containing
     * Activity's lifecycle.
     */
    @Override
    public void onStart() {
        super.onStart();
        fillData();
    }

    private void fillData() {
        Bundle bundle = this.getArguments();
        if(bundle==null)
            return;
        if (bundle.containsKey("cardItem")) {
            CardItem cardItem = (CardItem) DataTransferHelper.convertFromJson(CardItem.class,
                    bundle.getString("cardItem"));
            setBind(cardItem);
        }


    }

    private void setBind(CardItem cardItem) {
        String backphoto = cardItem.getPhotoBack();
        String facephoto = cardItem.getPhotoFront();

        if (backphoto != null && back!=null) {
           /* Picasso.get()
                    .load(backphoto)
                    .placeholder(R.drawable.cardtemplate)
                    .into(back);*/
            back.setImageURI(Uri.parse(backphoto));
        }

        if (facephoto != null && front!=null) {
            /*Picasso.get()
                    .load(Uri.parse(facephoto))
                    .placeholder(R.drawable.cardtemplate)
                    .into(front);*/
            front.setImageURI(Uri.parse(facephoto));
        }
    }
}
