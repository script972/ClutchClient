package com.script972.clutchclient.ui.activitys.card;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.script972.clutchclient.R;
import com.wajahatkarim3.easyflipview.EasyFlipView;



public class ActivityItemCard extends AppCompatActivity {

    private EasyFlipView profileImage;
    private ImageView barcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_card);
        initView();
    }

    private void initView() {
        barcode= (ImageView) findViewById(R.id.barcode);

        initToolbar();
        //TODO mock();


    }

    //TODO
   /* private void mock() {
        String text="11232321"; // Whatever you need to encode in the QR code
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.CODE_128,700,330);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            barcode.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
*/
    private void initToolbar() {

    }

}
