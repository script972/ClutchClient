package com.script972.clutchclient.helpers;

import android.util.Base64;

import com.google.gson.Gson;
import com.script972.clutchclient.model.LocationPosition;
import com.script972.clutchclient.model.api.CardItem;

public class DataTransferHelper {


    /**
     * Method for convert object to JSON
     *
     * @param object
     * @return
     */
    public static String convertToJson(Object object) {
        String jsonCredentials = new Gson().toJson(object);
        return Base64.encodeToString(jsonCredentials.getBytes(), Base64.DEFAULT);
    }


    /**
     * Method witch convert from json to object
     *
     * @param inputJson
     * @param classInConvert
     * @return
     */

    public static Object convertFromJson(Class classInConvert, String inputJson) {
        String json = new String(Base64.decode(inputJson, Base64.DEFAULT));
        return new Gson().fromJson(json, classInConvert);
    }
}
