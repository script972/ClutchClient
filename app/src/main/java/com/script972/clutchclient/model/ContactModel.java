package com.script972.clutchclient.model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.artlite.adapteredrecyclerview.models.ARCell;
import com.artlite.adapteredrecyclerview.models.ARObject;
import com.mio.modo.helpers.ContactHelper;
import com.mio.modo.ui.recycle.ContactRecycleView;
import com.script972.clutchclient.helpers.ContactHelper;

/**
 * Class which provide the contact model
 * Created by script972 on 2/27/2018.
 */

public class ContactModel {

    /**
     * Contact name
     */
    private String name;

    /**
     * Contact phone number
     */
    private String phone;

    /**
     * Contact photo
     */
    private String photo;

    /**
     * Instance of the {@link Uri}
     */
    private Uri thumbnail = null;

    /**
     * Contact already use the isModo
     */
    private boolean isModo;

    // Constructors

    /**
     * Constructor which provide the create the {@link ContactModel}
     * with parameters
     *
     * @param name   {@link String} value of the name
     * @param phone  {@link String} value of the phone number
     * @param photo  {@link String} value of the photo
     * @param isModo
     */
    public ContactModel(@Nullable String name,
                        @Nullable String phone,
                        @Nullable String photo,
                        boolean isModo) {
        this(name, phone, photo, null, isModo);
    }

    /**
     * Constructor which provide the create the {@link ContactModel}
     * with parameters
     *
     * @param name      {@link String} value of the name
     * @param phone     {@link String} value of the phone number
     * @param photo     {@link String} value of the photo
     * @param thumbnail instance of the {@link Uri}
     * @param isModo
     */
    public ContactModel(@Nullable String name,
                        @Nullable String phone,
                        @Nullable String photo,
                        @Nullable Uri thumbnail,
                        boolean isModo) {
        this.name = name;
        this.phone = phone;
        this.photo = photo;
      //  this.setIsModo(isModo);
        this.thumbnail = thumbnail;
    }

    /**
     * Constructor which provide the create {@link ContactModel}
     * from {@link ContactHelper.ContactContainer}
     *
     * @param container instance of the {@link ContactHelper.ContactContainer}
     * @param isModo    instance of the {@link Object}
     */
    public ContactModel(@NonNull ContactHelper.ContactContainer container, boolean isModo) {
        this(container.name, container.phone, container.photo, container.thumbnail, isModo);
    }



    /**
     * Method which provide the {@link Object}
     *
     * @return instance of the {@link Object}
     */
    public Uri getThumbnail() {
        return thumbnail;
    }

    /**
     * Method which provide the setting of the {@link Object}
     *
     * @param thumbnail instance of the {@link Object}
     */
    public void setThumbnail(Uri thumbnail) {
        this.thumbnail = thumbnail;
    }
}
