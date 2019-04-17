package com.script972.clutchclient.ui.fragment;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
import com.script972.clutchclient.ui.model.CardItem;


public class BarcodeFragment extends Fragment {


    private View view;
    private static BarcodeFragment fragment;


    public static BarcodeFragment getInstance() {
        if (fragment == null) {
            fragment = new BarcodeFragment();
        }
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.barcode_fragment, container, false);
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
        Bundle bundle = this.getArguments();
        if (bundle != null && bundle.containsKey("number")) {
            setBind(bundle.getString("number"));
        }
    }


    public void setBind(String number) {
        if (number == null || view == null)
            return;
        ((TextView) view.findViewById(R.id.barcodestr)).setText(String.valueOf(number != null ? number : ""));
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(number, BarcodeFormat.CODE_128, 1200, 600);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            ((ImageView) view.findViewById(R.id.barcode)).setImageBitmap(bitmap);

        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
}
