package com.script972.clutchclient.helpers;

import android.app.Dialog;
import android.content.Context;

import com.script972.clutchclient.ui.views.CustomProgressDialog;

public class DialogHelper {

    public static Dialog getProgressDialog(Context context) {
        CustomProgressDialog dialog = new CustomProgressDialog(context,
                android.R.style.Theme_Translucent_NoTitleBar);
        dialog.setCancelable(false);
        return dialog;
    }

}
