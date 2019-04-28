package com.dldriver.driver.models;

public class BaseError extends Response{

    private Throwable mThrowable;

    public Throwable getThrowable() {
        return mThrowable;
    }

    public BaseError(Throwable throwable) {
        mThrowable = throwable;
    }
}
