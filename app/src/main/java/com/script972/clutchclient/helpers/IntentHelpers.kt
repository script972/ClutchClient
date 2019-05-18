package com.script972.clutchclient.helpers

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.MenuItem

import com.script972.clutchclient.ui.activities.DiscountMapsActivity
import com.script972.clutchclient.ui.activities.MainActivity
import com.script972.clutchclient.ui.activities.SettingActivity
import com.script972.clutchclient.ui.activities.SplashScreenActivity
import com.script972.clutchclient.ui.activities.authorization.LoginActivity
import com.script972.clutchclient.ui.activities.card.ActivityAddCard
import com.script972.clutchclient.ui.activities.card.ActivityItemCard
import com.script972.clutchclient.ui.activities.card.ActivityListCompany
import com.script972.clutchclient.ui.activities.card.BarcodeLandscapeActivity
import com.script972.clutchclient.ui.model.CardItem

object IntentHelpers {

    val CARD_ITEM = "cardItem"
    val OPEN_SCAN = "openScan"
    val CARD_NUMBER = "cardNumber"

    fun pushMainActivity(context: Context) {
        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        context.startActivity(intent)
    }

    fun pushAddCardActivity(context: Context) {
        val intent = Intent(context, ActivityAddCard::class.java)
        context.startActivity(intent)
    }

    fun pushAddCardActivity(context: Context, item: CardItem) {
        val intent = Intent(context, ActivityAddCard::class.java)
        intent.putExtra(CARD_ITEM, DataTransferHelper.convertToJson(item))
        intent.putExtra(OPEN_SCAN, false)
        context.startActivity(intent)
    }

    fun pushDetailsCard(context: Context, id: Long) {
        val intent = Intent(context, ActivityItemCard::class.java)
        intent.putExtra(CARD_ITEM, id)
        context.startActivity(intent)
    }

    fun pushBarcodeLandScape(context: Context, cardNumber: String) {
        val intent = Intent(context, BarcodeLandscapeActivity::class.java)
        intent.putExtra(CARD_NUMBER, cardNumber)
        context.startActivity(intent)
    }

    fun pushCompanyListActivity(context: Context) {
        val intent = Intent(context, ActivityListCompany::class.java)
        context.startActivity(intent)
    }

    fun pushSettingsActivity(context: Context) {
        val intent = Intent(context, SettingActivity::class.java)
        context.startActivity(intent)
    }

    fun pushMapsDiscount(context: Context) {
        val intent = Intent(context, DiscountMapsActivity::class.java)
        context.startActivity(intent)
    }

    fun pushLoginActivity(context: Context) {
        val intent = Intent(context, LoginActivity::class.java);
        context.startActivity(intent);
    }


    /**
     * Method for choosing image
     *
     * @param codeRequest
     */
    fun pushPhotoFromGallary(activity: Activity, codeRequest: Int) {
        val galleryIntent = Intent(Intent.ACTION_GET_CONTENT,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        activity.startActivityForResult(galleryIntent, codeRequest)
    }


}
