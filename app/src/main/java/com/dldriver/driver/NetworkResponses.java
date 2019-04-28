package com.dldriver.driver;

import com.dldriver.driver.models.BaseError;

public interface NetworkResponses<T> {
    void success(T response);
    void failed(T failedResonse);
    void error(BaseError baseError);
}
