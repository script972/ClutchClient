package com.script972.clutchclient.helpers;

import android.content.Context;
import android.content.Intent;

import com.script972.clutchclient.ui.activities.card.ActivityAddCard;
import com.script972.clutchclient.ui.activities.card.ActivityListCompany;

public class IntentHelpers {

    public static void openAddCardActivity(Context context) {
        Intent intent = new Intent(context, ActivityAddCard.class);
        context.startActivity(intent);
    }

}
