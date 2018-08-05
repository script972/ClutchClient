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


import com.google.android.gms.vision.text.Line;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.script972.clutchclient.R;
import com.script972.clutchclient.api.RetrofitManager;
import com.script972.clutchclient.api.service.CardItemService;
import com.script972.clutchclient.api.service.CompanyService;
import com.script972.clutchclient.helpers.DataTransferHelper;
import com.script972.clutchclient.model.api.CardItem;
import com.script972.clutchclient.model.api.Company;
import com.script972.clutchclient.ui.activitys.BaseActivity;
import com.squareup.picasso.Picasso;

import java.io.FileDescriptor;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ActivityAddCard extends BaseActivity {

    //outlets
    private ImageView imCardPhotoBarckode;
    private EditText etNumberCard;
    private ImageView imCompanyImage;
    private View cardPhotoFront;
    private View cardPhotoBack;
    private ImageView imCardPhotoFront;
    private ImageView imCardPhotoBack;

    private Company company;
    /**
     * Object for server
     */
    private CardItem cardItem=new CardItem();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);
        super.initCommonView();
        getFromIntent();

        initView();
      //  openScan();
    }

    /**
     * Init view
     */
    private void initView() {
        initToolbar();
        imCompanyImage = findViewById(R.id.card_top_logo);
        imCardPhotoFront = findViewById(R.id.card_photo_front_img);
        imCardPhotoBack = findViewById(R.id.card_photo_back_img);
        imCardPhotoBarckode = findViewById(R.id.card_photo_barckode);

        etNumberCard = findViewById(R.id.et_number_card);
        cardPhotoFront = findViewById(R.id.card_photo_front);
        cardPhotoBack = findViewById(R.id.card_photo_back);

        cardPhotoFront.setOnClickListener(clicker);
        cardPhotoBack.setOnClickListener(clicker);
        imCardPhotoFront.setOnClickListener(clicker);
        imCardPhotoBarckode.setOnClickListener(clicker);
        imCardPhotoBack.setOnClickListener(clicker);

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
        this.cardItem.setCompany(company);
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

        if(requestCode == 21 && resultCode == RESULT_OK){
            super.showStatusPanel(getResources().getString(R.string.card_added), TypeStatus.INFORM, true);
            Uri imageUri = data.getData();
            imCardPhotoFront.setImageURI(imageUri);
            return;
        }

        if (requestCode == 22 && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            imCardPhotoBack.setImageURI(selectedImage);
            return;
        }

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                //scannser cancelled
                super.showStatusPanel("Cancelled ", TypeStatus.ERROR, true);
            } else {
                //from scanner ok
                etNumberCard.setText(result.getContents());
                imCardPhotoFront.setImageURI(Uri.parse(result.getBarcodeImagePath()));
                super.showStatusPanel(getResources().getString(R.string.card_added), TypeStatus.INFORM, true);
                cardItem.setNumber(result.getContents());
                //cardItem.setFacePhoto();
                addCardToServer(cardItem);
            }
        }

    }

    /**
     * Add card to server
     */
    private void addCardToServer(CardItem cardItem) {
        super.showProgressDialog();
        CardItemService cardItemService= RetrofitManager.getInstance().apiRetrofit.create(CardItemService.class);
        cardItemService.postCardItem(cardItem).enqueue(new Callback<CardItem>() {
            @Override
            public void onResponse(Call<CardItem> call, Response<CardItem> response) {
                Log.i("responce", response.message());
                ActivityAddCard.super.hideProgressDialog();
            }

            @Override
            public void onFailure(Call<CardItem> call, Throwable t) {
                ActivityAddCard.super.hideProgressDialog();
            }
        });



    }

    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }

    public void loadFront() {
        pickImage(21);
    }

    public void loadBack() {
        pickImage(22);

    }

    /**
     * Method for choosing image
     *
     * @param code
     */
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
                case R.id.card_photo_front_img: loadFront(); break;
                case R.id.card_photo_back_img: loadBack(); break;
            }
        }
    };


}



