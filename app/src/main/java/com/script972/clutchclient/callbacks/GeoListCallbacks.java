package com.script972.clutchclient.callbacks;

import com.script972.clutchclient.domain.api.model.api.Company;

import java.util.List;

public interface GeoListCallbacks {

    void returnWorkerList(List<Company> companyList);

    /**
     * Method wich call in click on item geo list company
     * @param company
     */
    void chooseItemInList(Company company);
}
