package com.script972.clutchclient.domain.callback;

import com.script972.clutchclient.domain.database.entity.CardEntity;
import com.script972.clutchclient.ui.model.InformationCodes;

public interface OnResult {

    void dataResult(InformationCodes result, CardEntity cardEntity);

}
