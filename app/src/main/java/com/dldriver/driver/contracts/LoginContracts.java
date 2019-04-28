package com.dldriver.driver.contracts;

public interface LoginContracts {
    interface ILoginView{
        void success(String message);
        void failed(String message);
        void error(String message);
    }
    interface ILoginPresenter{
        void doLogin(String registrationToken, String pin);
    }
    interface ILoginInteracter{
        void doLogin(String pin,String registrationToken);
    }
}
