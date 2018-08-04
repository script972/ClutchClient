package com.script972.clutchclient.ui.activitys.card;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.script972.clutchclient.R;
import com.script972.clutchclient.helpers.DataTransferHelper;
import com.script972.clutchclient.model.api.Company;
import com.script972.clutchclient.ui.activitys.BaseActivity;
import com.squareup.picasso.Picasso;

import java.io.FileDescriptor;
import java.io.IOException;


public class ActivityAddCard extends BaseActivity {

    //outlets
    private ImageView imCardPhotoBarckode;
    private EditText etNumberCard;
    private ImageView imCompanyImage;
    private ImageView imCardPhotoFront;
    private ImageView imCardPhotoBack;

    private Company company;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);
        super.initCommonView();
        getFromIntent();

        initView();
        openScan();
    }

    private void initView() {
        initToolbar();
        imCardPhotoBarckode = (ImageView) findViewById(R.id.card_photo_barckode);
        etNumberCard= (EditText) findViewById(R.id.et_number_card);
        imCardPhotoFront = (ImageView) findViewById(R.id.card_photo_front);
        imCardPhotoBack = (ImageView) findViewById(R.id.card_photo_back);
        imCompanyImage = findViewById(R.id.card_top_logo);

        imCardPhotoBarckode.setOnClickListener(clicker);
        Picasso.get()
                .load(company.getLogo())
                .placeholder(R.drawable.cardtemplate)
                .error(R.drawable.ic_arrow_back)
                .into(imCompanyImage);
    }

    /**
     * Method for get information from intent
     */
    private void getFromIntent() {
        this.company = (Company) DataTransferHelper.convertFromJson(Company.class, getIntent().getExtras().getString("company"));
    }

    /**
     * Method for init toolbar
     */
    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back));
        toolbar.setTitle(getResources().getString(R.string.toolbar_addition_card));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    /**
     * Method wich open scanner
     */
    public void openScan() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt(getResources().getString(R.string.scaner_activity_title));
        integrator.setCameraId(0);

        integrator.setBeepEnabled(false);
        integrator.setBarcodeImageEnabled(true);
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
                    .into(imCardPhotoFront);
        }*/

        if(requestCode == 21 && resultCode == RESULT_OK){
            Log.i("scan", "fronPhoto ready");

            Uri imageUri = data.getData();
            imCardPhotoFront.setImageURI(imageUri);

        }
        if (requestCode == 22 && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            imCardPhotoBack.setImageURI(selectedImage);

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

    //callbacks
    private View.OnClickListener clicker=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.card_photo_barckode: openScan(); break;
            }
        }
    };


}



