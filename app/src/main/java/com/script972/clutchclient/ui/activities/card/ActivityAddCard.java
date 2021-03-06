package com.script972.clutchclient.ui.activities.card;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import android.provider.MediaStore;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.script972.clutchclient.R;
import com.script972.clutchclient.databinding.ActivityAddCardBinding;
import com.script972.clutchclient.helpers.DataTransferHelper;
import com.script972.clutchclient.helpers.ImageHelper;
import com.script972.clutchclient.helpers.IntentHelpers;
import com.script972.clutchclient.mappers.CardMapper;
import com.script972.clutchclient.ui.activities.BaseActivity;
import com.script972.clutchclient.ui.model.CardItem;
import com.script972.clutchclient.ui.model.InformationCodes;
import com.script972.clutchclient.ui.model.StatusCode;
import com.script972.clutchclient.viewmodels.AddCardViewModel;

import java.io.IOException;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;


public class ActivityAddCard extends BaseActivity {

    private static final int REQUEST_LOAD_FRONT = 21;
    private static final int REQUEST_LOAD_BACK = 22;

    private AddCardViewModel viewModel;
    private ActivityAddCardBinding binding;
    private LiveData<Pair<InformationCodes, CardItem>> liveData;

    /**
     * Binding object
     */
    private CardItem uiModel = new CardItem();


    private boolean updateMode = false;
    private boolean showMessage = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_add_card);
        this.viewModel = ViewModelProviders.of(this).get(AddCardViewModel.class);

        super.initCommonView();
        initView();
        initLiveData();
        getDataFromIntent();
        openScanIfNeed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item_edit_card_toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_remove: {
                liveData.removeObservers(this);
                viewModel.removeItemCard(uiModel.getId());
                IntentHelpers.INSTANCE.pushMainActivity(this);
                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }


    private void initLiveData() {
        liveData = viewModel.observeLiveData();
        liveData.observe(this, pair -> {
            if (showMessage) {
                ActivityAddCard.super.showStatusPanel(pair.first);
            } else {
                showMessage = true;
            }
            uiModel = pair.second;
            uiModel.setUpdateMode(updateMode);
            this.binding.setItem(uiModel);

            //*----------------------*//*
           /* Uri selectedImage = Uri.parse(pair.second.getPhotoBack());
            binding.imgCardPhotoBack.setImageURI(selectedImage);
            uiModel.setPhotoBack(String.valueOf(selectedImage));*/
            //*----------------------*//*
            Uri selectedImage = Uri.parse(pair.second.getPhotoFront());
            binding.imgCardPhotoFront.setImageURI(selectedImage);
            if (pair.second.getPhotoBack() != null && !pair.second.getPhotoBack().isEmpty()) {
                selectedImage = Uri.parse(pair.second.getPhotoBack());
                this.binding.imgCardPhotoBack.setImageURI(selectedImage);
            }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_LOAD_FRONT && resultCode == RESULT_OK) {
            super.showStatusPanel(getResources().getString(R.string.card_added), StatusCode.INFORMATION, true);
            Uri selectedImage = data.getData();
            binding.imgCardPhotoFront.setImageURI(selectedImage);

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                String filePath = ImageHelper.saveBitmapToInternalStorage(bitmap, selectedImage);
                uiModel.setPhotoFront(filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return;
        }

        if (requestCode == REQUEST_LOAD_BACK && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            binding.imgCardPhotoBack.setImageURI(selectedImage);
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                String filePath = ImageHelper.saveBitmapToInternalStorage(bitmap, selectedImage);
                uiModel.setPhotoBack(filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return;
        }

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                super.showStatusPanel("Cancelled ", StatusCode.INFORMATION, true);
            } else {
                //from scanner ok
                super.showStatusPanel(getResources().getString(R.string.card_added), StatusCode.INFORMATION, true);
                uiModel.setCardNumber(result.getContents());
                uiModel.setPhotoFront(String.valueOf(result.getBarcodeImagePath()));
                viewModel.addNewCard(CardMapper.uiToEntity(uiModel));
            }
        }

    }

    /**
     * Init view
     */
    private void initView() {
        initToolbar();

        binding.cardPhotoFront.setOnClickListener(clicker);
        binding.cardPhotoBack.setOnClickListener(clicker);
        binding.imgCardPhotoFront.setOnClickListener(clicker);
        binding.imgCardPhotoBarckode.setOnClickListener(clicker);
        binding.imgCardPhotoBack.setOnClickListener(clicker);
        binding.btnAddCard.setOnClickListener(clicker);

        /*Picasso.get()
                .load(company.getLogo())
                .placeholder(R.drawable.cardtemplate)
                .error(R.drawable.ic_arrow_back)
                .into(imCompanyImage);*/
    }

    /**
     * Method for get information from intent
     */
    private void getDataFromIntent() {
        if (getIntent().hasExtra(IntentHelpers.INSTANCE.getCARD_ITEM())) {
            this.uiModel = (CardItem) DataTransferHelper.convertFromJson(CardItem.class, getIntent().getExtras()
                    .getString(IntentHelpers.INSTANCE.getCARD_ITEM()));
            if (uiModel != null && uiModel.getId() != 0) {
                updateMode = true;
                showMessage = false;
                viewModel.findOneCardById(uiModel.getId());
            }
        }
    }

    /**
     * Method for init toolbar
     */
    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back));
        toolbar.setTitle(getResources().getString(R.string.toolbar_addition_card));
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> {
            onBackPressed();
        });
    }

    public void openScanIfNeed() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey(IntentHelpers.INSTANCE.getOPEN_SCAN()) &&
                !((boolean) bundle.get(IntentHelpers.INSTANCE.getOPEN_SCAN()))) {
            return;
        }
        openScan();
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


    private void btnAddCard() {
        parseField();
        if (uiModel.getCardNumber().isEmpty()) {
            binding.etNumberCard.setError(getResources().getString(R.string.e_card_number_can_not_be_empy));
        } else {
            viewModel.updateCard(CardMapper.uiToEntity(uiModel));
        }

    }

    private void parseField() {
        uiModel.setComment(binding.etCommentCard.getText().toString());
        uiModel.setCardNumber(binding.etNumberCard.getText().toString());
        uiModel.setTitle(binding.etTitleCard.getText().toString());
    }


    //callbacks
    private View.OnClickListener clicker = v -> {
        switch (v.getId()) {
            case R.id.img_card_photo_barckode:
                openScan();
                break;
            case R.id.img_card_photo_front:
                IntentHelpers.INSTANCE.pushPhotoFromGallary(this, REQUEST_LOAD_FRONT);
                break;
            case R.id.img_card_photo_back:
                IntentHelpers.INSTANCE.pushPhotoFromGallary(this, REQUEST_LOAD_BACK);
                break;
            case R.id.btn_add_card:
                btnAddCard();
                break;

        }
    };


}



