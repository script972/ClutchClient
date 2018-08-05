package com.script972.clutchclient.ui.fragment;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.script972.clutchclient.R;
import com.squareup.picasso.Picasso;

public class InfoFragment extends Fragment {

    //outlets
    private View view;
    private ImageView front;
    private ImageView back;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.info_card_fragment, container, false);
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
        if(bundle==null){
            return;
        }

        String backphoto = bundle.getString("backphoto", null);
        String facephoto = bundle.getString("facephoto", null);

        if(backphoto!=null) {
            Picasso.get()
                    .load(backphoto)
                    .placeholder(R.drawable.cardtemplate)
                    .error(R.drawable.ic_arrow_back)
                    .into(back);
        }
        if(facephoto!=null){
            Picasso.get()
                    .load(facephoto)
                    .placeholder(R.drawable.cardtemplate)
                    .error(R.drawable.ic_earth)
                    .into(front);
        }
        //((TextView)view.findViewById(R.id.barcodestr)).setText(number);

    }
}
