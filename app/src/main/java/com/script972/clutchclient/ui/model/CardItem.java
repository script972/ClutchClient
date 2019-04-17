package com.script972.clutchclient.ui.model;

import lombok.Data;

@Data
public class CardItem {

    private long id;

    private String title;

    private String cardNumber;

    private String comment;

    private String photoFront;

    private String photoBack;

    private boolean updateMode;
}
