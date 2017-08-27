package com.devas.bear.clutchclient.addcard;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.devas.bear.clutchclient.R;

public class ActivityAddCard extends AppCompatActivity {


    private Toolbar toolbar;
    private ImageView cardPhotoBarckode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);

        initView();

    }

    private void initView() {
        initToolBar();
        cardPhotoBarckode= (ImageView) findViewById(R.id.card_photo_barckode);
    }

    private void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);//цвет стрелки назад
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    public void openScan(View view) {
        Intent intent=new Intent(this, ActivityScaner.class);
        startActivity(intent);
    }
}
