package com.script972.clutchclient.helpers;

import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;

public class KeyboardHelper {

    public static void hideSoftKeyboard(AppCompatActivity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

}
