package com.script972.clutchclient.ui.activities.card;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.script972.clutchclient.R;
import com.script972.clutchclient.helpers.DeviceHelper;
import com.script972.clutchclient.helpers.IntentHelpers;

public class BarcodeLandscapeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_landscape);
        initView();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void initView() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            finish();
            return;
        }

        String number = getIntent().getStringExtra(IntentHelpers.INSTANCE.getCARD_NUMBER());
        try {
            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            BitMatrix bitMatrix = multiFormatWriter.encode(number, BarcodeFormat.CODE_128, 1200, 600);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            ((ImageView) findViewById(R.id.barcode_land)).setImageBitmap(bitmap);
            ((TextView) findViewById(R.id.barcode_namber_land)).setText(number);

        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
}
