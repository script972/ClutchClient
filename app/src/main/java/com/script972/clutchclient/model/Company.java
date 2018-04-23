package com.script972.clutchclient.model;

import android.support.annotation.NonNull;

/**
 * Created by script972 on 27.08.2017.
 */

public class Company implements Comparable {

    private long id;

    private String title;

    private String icon;

    public Company(long id, String title, String icon) {
        this.id = id;
        this.title = title;
        this.icon = icon;
    }

    public Company() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }



    @Override
    public int compareTo(@NonNull Object o) {
        String compareQuantity = ((Company) o).getTitle();



        //ascending order
        return this.title.charAt(0) - compareQuantity.charAt(0);
    }
}
