package com.script972.clutchclient.helpers;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;

import com.script972.clutchclient.R;
import com.script972.clutchclient.ui.views.CustomProgressDialog;

public class DialogHelper {

    /**
     * Method wich controll ProgressDialog wich freeze window
     * @param context of activity
     * @return wish Dialog
     */
    public static Dialog getProgressDialog(Context context) {
        CustomProgressDialog dialog = new CustomProgressDialog(context,
                android.R.style.Theme_Translucent_NoTitleBar);
        dialog.setCancelable(false);
        return dialog;
    }

    /**
     * Method wich open Dialog GPS on/off
     * @param context of activity
     * @return wish Dialog
     */
    public static Dialog getGpsDialog(final Context context){
        final AlertDialog.Builder builder =
                new AlertDialog.Builder(context);
        final String action = Settings.ACTION_LOCATION_SOURCE_SETTINGS;
        final String message = context.getResources().getString(R.string.dialog_gps_message);

        builder.setMessage(message)
                .setPositiveButton( context.getResources().getString(R.string.btn_ok),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface d, int id) {
                                context.startActivity(new Intent(action));
                                d.dismiss();
                            }
                        })
                .setNegativeButton(context.getResources().getString(R.string.btn_cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });


        builder.setCancelable(true);
        return builder.create();
    }

    /**
     * Method for safety Cloase dialog
     *
     * @param dialog
     */
    public static void safeClose(Dialog dialog) {
        try {
            if (dialog != null) {
                if (dialog.isShowing()) {
                    dialog.cancel();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void logOutDialog(final Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setMessage(R.string.msg_log_out_agree)
                .setTitle(R.string.title_log_out);
        builder.setCancelable(true);

        builder.setPositiveButton(R.string.btn_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                PrefHelper.clearCredentials(context);
                PrefHelper.setAuthorizedFlag(context, false);
                PrefHelper.setAccessToken(context, null);
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        }).setNegativeButton(R.string.btn_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.create().show();



    }
}
