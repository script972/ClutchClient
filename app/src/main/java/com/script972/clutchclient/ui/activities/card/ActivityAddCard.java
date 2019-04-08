package com.script972.clutchclient.ui.activities.card;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.util.Pair;
import android.view.View;


import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.script972.clutchclient.R;
import com.script972.clutchclient.databinding.ActivityAddCardBinding;
import com.script972.clutchclient.domain.api.model.api.Company;
import com.script972.clutchclient.domain.database.entity.CardEntity;
import com.script972.clutchclient.mappers.CardMapper;
import com.script972.clutchclient.ui.activities.BaseActivity;
import com.script972.clutchclient.ui.activities.MainActivity;
import com.script972.clutchclient.ui.model.CardItem;
import com.script972.clutchclient.ui.model.InformationCodes;
import com.script972.clutchclient.ui.model.StatusCode;
import com.script972.clutchclient.viewmodels.AddCardViewModel;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


public class ActivityAddCard extends BaseActivity {

    private boolean cardAddedSuccess = false;

    private AddCardViewModel viewModel;
    private ActivityAddCardBinding binding;


    /**
     * Object for server
     */
    // private CardEntity cardItem = new CardEntity();

    private CardItem uiModel = new CardItem();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDataFromIntent();
        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_add_card);
        this.viewModel = ViewModelProviders.of(this).get(AddCardViewModel.class);
        super.initCommonView();
        initView();
        initLiveData();
        openScan();
    }

    private void initLiveData() {
        LiveData<Pair<InformationCodes, CardItem>> liveData = viewModel.observeLiveData();
        liveData.observe(this, pair -> {
            ActivityAddCard.super.showStatusPanel(pair.first);
            uiModel = pair.second;
            this.binding.setItem(uiModel);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 21 && resultCode == RESULT_OK) {
            super.showStatusPanel(getResources().getString(R.string.card_added), StatusCode.INFORMATION, true);
            Uri imageUri = data.getData();
            binding.imgCardPhotoFront.setImageURI(imageUri);
            uiModel.setPhotoFront(String.valueOf(imageUri));
            return;
        }

        if (requestCode == 22 && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            binding.imgCardPhotoBack.setImageURI(selectedImage);
            uiModel.setPhotoBack(String.valueOf(selectedImage));
            return;
        }

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                //scannser cancelled
                super.showStatusPanel("Cancelled ", StatusCode.INFORMATION, true);
            } else {
                //from scanner ok
                super.showStatusPanel(getResources().getString(R.string.card_added), StatusCode.INFORMATION, true);
                uiModel.setCardNumber(result.getContents());
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
      /*  this.company = (Company) DataTransferHelper.convertFromJson(Company.class, getIntent().getExtras().getString("company"));
        this.cardItem.setCompany(company);*/
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
                if (cardAddedSuccess) {
                    Intent intent = new Intent(ActivityAddCard.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else {
                    onBackPressed();
                }
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


    public void loadFront() {
        pickImage(21);
    }

    public void loadBack() {
        pickImage(22);

    }

    private void btnAddCard() {
        parseField();
        viewModel.updateCard(CardMapper.uiToEntity(uiModel));
    }

    private void parseField() {
        uiModel.setComment(binding.etCommentCard.getText().toString());
        uiModel.setCardNumber(binding.etNumberCard.getText().toString());
        uiModel.setTitle(binding.etTitleCard.getText().toString());
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
    private View.OnClickListener clicker = v -> {
        switch (v.getId()) {
            case R.id.img_card_photo_barckode:
                openScan();
                break;
            case R.id.img_card_photo_front:
                loadFront();
                break;
            case R.id.img_card_photo_back:
                loadBack();
                break;
            case R.id.btn_add_card:
                btnAddCard();
                break;

        }
    };


}



