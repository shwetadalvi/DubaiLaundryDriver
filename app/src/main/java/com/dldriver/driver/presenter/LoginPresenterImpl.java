package com.dldriver.driver.presenter;

import com.dldriver.driver.NetworkResponses;
import com.dldriver.driver.contracts.LoginContracts;
import com.dldriver.driver.interactors.LoginInteracterImpl;
import com.dldriver.driver.models.BaseError;
import com.dldriver.driver.models.Response;

public class LoginPresenterImpl implements LoginContracts.ILoginPresenter, NetworkResponses<Response> {

    private LoginContracts.ILoginView mLoginView;
    private final LoginInteracterImpl mLoginInteracter;

    public LoginPresenterImpl(LoginContracts.ILoginView loginView) {
        mLoginView = loginView;
        mLoginInteracter = new LoginInteracterImpl(this);
    }

    @Override public void success(Response response) {
        mLoginView.success(response.getMessage());

    }

    @Override public void failed(Response failedResonse) {
        mLoginView.failed(((BaseError)failedResonse).getThrowable().getMessage());
    }

    @Override public void error(BaseError errorMessage) {
        mLoginView.error(((BaseError)errorMessage).getThrowable().getMessage());
    }

    @Override public void doLogin(String registrationToken, String pin) {
        mLoginInteracter.doLogin(pin,registrationToken);
    }
}