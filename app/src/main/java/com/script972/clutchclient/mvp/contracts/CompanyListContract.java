package com.script972.clutchclient.mvp.contracts;

import com.script972.clutchclient.domain.api.model.api.Company;

import java.util.List;

public interface CompanyListContract {

    interface Presenter{
        /**
         * Method wich call refreshCompanyList
         */
        void refreshCompanyList();


    }

    interface View{
        /**
         * Method wich show UI user login fail
         */
        void refreshDataCompany(List<Company> companyList);


        void onFailureGetData(Throwable t);

    }

}
