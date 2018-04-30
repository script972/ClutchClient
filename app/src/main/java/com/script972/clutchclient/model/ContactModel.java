package com.script972.clutchclient.model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

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
     * Contact already use the isModo
     */
    private boolean isUser;

    /**
     * Mark as selected
     */
    private boolean isSelected=false;

    // Constructors


    public ContactModel() {
    }

    /**
     * Constructor which provide the create the {@link ContactModel}
     * with parameters
     *
     * @param name   {@link String} value of the name
     * @param phone  {@link String} value of the phone number
     * @param photo  {@link String} value of the photo
     * @param isUser
     */
    public ContactModel(@Nullable String name,
                        @Nullable String phone,
                        @Nullable String photo,
                        boolean isUser) {
        this.name=name;
        this.phone=phone;
        this.photo=photo;
        this.isUser=isUser;
    }


    /**
     * Constructor which provide the create {@link ContactModel}
     * from {@link ContactHelper.ContactContainer}
     *
     * @param container instance of the {@link ContactHelper.ContactContainer}
     * @param isUser    instance of the {@link Object}
     */
    public ContactModel(@NonNull ContactHelper.ContactContainer container, boolean isUser) {
        this(container.name, container.phone, container.photo, isUser);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public boolean isUser() {
        return isUser;
    }

    public void setUser(boolean user) {
        isUser = user;
    }
}
