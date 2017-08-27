package com.devas.bear.clutchclient.addcard;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.devas.bear.clutchclient.R;


public class ActivityScaner extends AppCompatActivity BarcodeReader.BarcodeReaderListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scaner);
    }
}
