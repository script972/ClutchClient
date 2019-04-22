package com.script972.clutchclient.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

import com.script972.clutchclient.ui.activities.DiscountMapsActivity;
import com.script972.clutchclient.ui.activities.MainActivity;
import com.script972.clutchclient.ui.activities.SettingActivity;
import com.script972.clutchclient.ui.activities.card.ActivityAddCard;
import com.script972.clutchclient.ui.activities.card.ActivityItemCard;
import com.script972.clutchclient.ui.activities.card.ActivityListCompany;
import com.script972.clutchclient.ui.activities.card.BarcodeLandscapeActivity;
import com.script972.clutchclient.ui.model.CardItem;

public class IntentHelpers {

    public static final String CARD_ITEM = "cardItem";
    public static final String OPEN_SCAN = "openScan";
    public static final String CARD_NUMBER = "cardNumber";

    public static void pushAddCardActivity(Context context) {
        Intent intent = new Intent(context, ActivityAddCard.class);
        context.startActivity(intent);
    }

    public static void pushAddCardActivity(Context context, CardItem item) {
        Intent intent = new Intent(context, ActivityAddCard.class);
        intent.putExtra(CARD_ITEM, DataTransferHelper.convertToJson(item));
        intent.putExtra(OPEN_SCAN, false);
        context.startActivity(intent);
    }

    public static void pushDetailsCard(Context context, long id) {
        Intent intent = new Intent(context, ActivityItemCard.class);
        intent.putExtra(CARD_ITEM, id);
        context.startActivity(intent);
    }

    public static void pushBarcodeLandScape(Context context, String cardNumber) {
        Intent intent = new Intent(context, BarcodeLandscapeActivity.class);
        intent.putExtra(CARD_NUMBER, cardNumber);
        context.startActivity(intent);
    }

    public static void pushCompanyListActivity(Context context) {
        Intent intent = new Intent(context, ActivityListCompany.class);
        context.startActivity(intent);
    }

    public static void pushSettingsActivity(Context context) {
        Intent intent = new Intent(context, SettingActivity.class);
        context.startActivity(intent);
    }

    public static void pushMapsDiscount(Context context) {
        Intent intent = new Intent(context, DiscountMapsActivity.class);
        context.startActivity(intent);
    }

    /**
     * Method for choosing image
     *
     * @param codeRequest
     */
    public static void pushPhotoFromGallary(Activity activity, int codeRequest) {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(galleryIntent, codeRequest);
    }



}
