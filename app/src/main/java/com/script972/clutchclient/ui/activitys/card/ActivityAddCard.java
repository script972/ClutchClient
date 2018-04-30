package com.script972.clutchclient.ui.activitys.card;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.script972.clutchclient.R;

import java.io.FileDescriptor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class ActivityAddCard extends AppCompatActivity {


    private Toolbar toolbar;
    private ImageView cardPhotoBarckode;
    private EditText etNumberCard;
    private ImageView cardPhotoFront;
    private ImageView cardPhotoBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);

        initView();

    }

    private void initView() {
        initToolBar();
        cardPhotoBarckode= (ImageView) findViewById(R.id.card_photo_barckode);
        etNumberCard= (EditText) findViewById(R.id.et_number_card);
        cardPhotoFront= (ImageView) findViewById(R.id.card_photo_front);
        cardPhotoBack= (ImageView) findViewById(R.id.card_photo_back);
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

        IntentIntegrator integrator = new IntentIntegrator(this);

        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt(getResources().getString(R.string.scaner_activity_title));
        integrator.setCameraId(0);

        integrator.setBeepEnabled(false);
        integrator.setBarcodeImageEnabled(false);
        integrator.initiateScan();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.i("scan", "request code "+requestCode+" resultCode "+requestCode+" data ");

        //TODO
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Log.i("scan", "Cancelled scan");
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Log.i("scan", "Scanned");
                etNumberCard.setText(result.getContents());
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
            }

        }

        /*if (requestCode == 21) {

            //Get ImageURi and load with help of picasso
            //Uri selectedImageURI = data.getData();
            Log.i("scan", "fronPhoto ready");

            Picasso.with(this).load(data.getData()).noPlaceholder().centerCrop().fit()
                    .into(cardPhotoFront);
        }*/

        if(requestCode == 21 && resultCode == RESULT_OK){
            Log.i("scan", "fronPhoto ready");

            Uri imageUri = data.getData();
            cardPhotoFront.setImageURI(imageUri);

        }
        if (requestCode == 22 && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            cardPhotoBack.setImageURI(selectedImage);

        }

    }

    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }

    public void loadFront(View view) {
        /*Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE );
        startActivityForResult( intent, 22 );*/
        pickImage(21);
    }

    public void loadBack(View view) {
        pickImage(22);

    }


    private void pickImage(int code) {

        Intent i = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(i, code);
    }

}



