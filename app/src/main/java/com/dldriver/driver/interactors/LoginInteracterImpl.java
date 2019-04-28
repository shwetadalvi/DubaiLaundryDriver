package com.dldriver.driver.interactors;

import com.dldriver.driver.NetworkResponses;
import com.dldriver.driver.contracts.LoginContracts;
import com.dldriver.driver.models.BaseError;
import com.dldriver.driver.models.Response;
import com.dldriver.driver.network.API;
import com.dldriver.driver.network.RetrofitClient;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class LoginInteracterImpl implements LoginContracts.ILoginInteracter {
    private NetworkResponses<Response> mNetworkResponses;

    public LoginInteracterImpl(NetworkResponses<Response> networkResponses) {
        mNetworkResponses = networkResponses;
    }

    @Override public void doLogin(String token,String pin) {
        RequestBody RequestBodyPin = RequestBody.create(MediaType.parse("multipart/form-data"), pin);
        RequestBody RequestBodyToken = RequestBody.create(MediaType.parse("multipart/form-data"), token);
        API apiList = RetrofitClient.getClient ().create (API.class);
        Call<Response> call = apiList.driverLogin(RequestBodyPin,RequestBodyToken);
        call.enqueue (new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if (response.code()==200) {
                  Response result = response.body();
                  mNetworkResponses.success(result);
                }else {
                    Throwable throwable = new Throwable("Login Failed");
                    BaseError baseError = new BaseError(throwable);
                    mNetworkResponses.failed(baseError);
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                BaseError baseError = new BaseError(t);
                mNetworkResponses.error(baseError);
            }
        });
    }


}