package com.script972.clutchclient.ui.model;

import com.script972.clutchclient.R;
import com.script972.clutchclient.core.ClutchApplication;

public enum InformationCodes {
    CARD_ALREADY_ADDED(StatusCode.ERROR, ClutchApplication.getApplication().getResources().getString(R.string.info_code_card_already_added)),
    CARD_UPDATED_SUCCESS(StatusCode.INFORMATION, ClutchApplication.getApplication().getResources().getString(R.string.info_card_updated_success)),
    CARD_NOT_ADDED(StatusCode.ERROR, ClutchApplication.getApplication().getResources().getString(R.string.info_code_card_already_added)),
    CARD_ADDED_SUCCESS(StatusCode.INFORMATION, ClutchApplication.getApplication().getResources().getString(R.string.info_card_added_success)),
    GET_SUCCESS(StatusCode.INFORMATION,ClutchApplication.getApplication().getResources().getString(R.string.info_get_success));

    private StatusCode statusCode;
    private String textInformation;

    InformationCodes(StatusCode statusCode, String textInformation) {
        this.statusCode = statusCode;
        this.textInformation = textInformation;
    }

    public StatusCode getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(StatusCode statusCode) {
        this.statusCode = statusCode;
    }

    public String getTextInformation() {
        return textInformation;
    }

    public void setTextInformation(String textInformation) {
        this.textInformation = textInformation;
    }
}
