package com.script972.clutchclient.callbacks;

import com.script972.clutchclient.model.api.Company;

import java.util.List;

public interface GeoListCallbacks {

    void returnWorkerList(List<Company> companyList);

}
