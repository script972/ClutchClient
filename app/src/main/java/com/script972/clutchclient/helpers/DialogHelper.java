package com.script972.clutchclient.helpers;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import androidx.appcompat.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

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
        final AlertDialog.Builder builder = getAlertDialogBuilder(context);
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
     * Method for logout and exit from app
     *
     * @param context
     * @param activity
     */
    public static void logOutDialog(final Context context, Activity activity){
        final AlertDialog.Builder builder = getAlertDialogBuilder(activity);

        builder.setMessage(R.string.msg_log_out_agree)
                .setTitle(R.string.title_log_out);
        builder.setCancelable(true);

        builder.setPositiveButton(R.string.btn_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                PrefHelper.clearCredentials(context);
                PrefHelper.setAuthorizedFlag(context, false);
                PrefHelper.setAccessToken(context, null);
                activity.finish();
                System.exit(0);

            }
        }).setNegativeButton(R.string.btn_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.create().show();
    }

    /**
     * Method for raiting app with with login <4 email and >=4 play market
     *
     * @param context
     */
    public static void openRateDialog(final Context context) {
        final String supportEmail = "script972@gmail.com";

        AlertDialog.Builder builder = getAlertDialogBuilder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_rating, null);
        builder.setView(view);
        builder.create();
        final AlertDialog dialog = builder.show();
        TextView dialogButton = (TextView) view.findViewById(R.id.submit_button);
        RatingBar ratingBar = (RatingBar) view.findViewById(R.id.rating_bar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (ratingBar.getRating() < 4.0) {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("plain/text");
                    intent.putExtra(Intent.EXTRA_EMAIL, new String[]{supportEmail});
                    intent.putExtra(Intent.EXTRA_SUBJECT, context.getResources().getString(R.string.email_rate_us_subject));
                    intent.putExtra(Intent.EXTRA_TEXT, context.getResources().getString(R.string.email_rate_us_text));
                    context.startActivity(Intent.createChooser(intent, context.getResources().getString(R.string.email_chooser_text)));
                } else {
                    Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
                    Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                    try {
                        context.startActivity(goToMarket);
                    } catch (ActivityNotFoundException e) {
                        Toast toast = Toast.makeText(context, "Failed to launch Google Play", Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
                safeClose(dialog);

            }
        });
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                safeClose(dialog);
            }
        });
    }


    /**
     * Constructor dialog
     *
     * @param context
     * @return
     */
    public static AlertDialog.Builder getAlertDialogBuilder(Context context) {
        return new AlertDialog.Builder(context);
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
}
