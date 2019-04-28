package com.dldriver.driver.interactors;

import android.util.Log;

import com.dldriver.driver.NetworkResponses;
import com.dldriver.driver.contracts.OrderContracts;
import com.dldriver.driver.models.Address;
import com.dldriver.driver.models.BaseError;
import com.dldriver.driver.models.CategoryItems;
import com.dldriver.driver.models.NewOrderItem;
import com.dldriver.driver.models.Order;
import com.dldriver.driver.models.PriceDetails;
import com.dldriver.driver.models.Response;
import com.dldriver.driver.network.API;
import com.dldriver.driver.network.RetrofitClient;
import com.dldriver.driver.room.AppDatabase;

import java.util.concurrent.atomic.AtomicReference;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class OrderInteracterImpl implements OrderContracts.IOrderInteracter {
    private NetworkResponses<Response> mNetworkResponses;
    private OrderContracts.IOrderPresenter mOrderPresenter;
    private AppDatabase mAppDatabase;

    public OrderInteracterImpl(NetworkResponses networkResponses, OrderContracts.IOrderPresenter orderPresenter) {
        mNetworkResponses = networkResponses;
        mOrderPresenter = orderPresenter;
    }

    public OrderInteracterImpl() {
    }

    @Override public void getOrderDetail(int orderId) {
      //  orderId = 34;
        RequestBody RequestBodyOrderId = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(orderId));
        API apiList = RetrofitClient.getClient().create(API.class);
        Call<Order> call = apiList.getOrders(RequestBodyOrderId);
        call.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, retrofit2.Response<Order> response) {
                if (response.code() == 200) {
                    Order result = response.body();
                    mNetworkResponses.success(result);
                } else {
                    Throwable throwable = new Throwable("Login Failed");
                    BaseError baseError = new BaseError(throwable);
                    mNetworkResponses.failed(baseError);
                }
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                Order baseError = new Order(t);
                mNetworkResponses.error(baseError);
            }
        });
    }

    @Override public void getAddressDetail(AppDatabase appDatabase, int orderId) {
        mAppDatabase = appDatabase;
        AtomicReference<Address> address = new AtomicReference<>();
        Thread thread = new Thread(() -> address.set(appDatabase.mAddressDao().getAddressByOrder(orderId)));
        thread.start();
        try {
            thread.join();
            if (address != null) {
                mOrderPresenter.passFetchedAddress(address);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override public void updateTrakingStatus(int orderId, int status, NetworkResponses mNetworkResponses) {
        RequestBody RequestBodyOrderId = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(orderId));
        RequestBody RequestBodyStatusCode = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(status));
        API apiList = RetrofitClient.getClient().create(API.class);
        Call<Response> call = apiList.trackingOn(RequestBodyOrderId, RequestBodyStatusCode);
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if (response.code() == 200) {
                    Response result = response.body();
                    mNetworkResponses.success(result);
                } else {
                    Throwable throwable = new Throwable("updation failed");
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
  public void rejectOrder(int status, int driverId, int orderId, NetworkResponses mNetworkResponses) {
      RequestBody RequestBodyDriverId = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(driverId));
        RequestBody RequestBodyOrderId = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(orderId));
        RequestBody RequestBodyStatusCode = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(status));
        API apiList = RetrofitClient.getClient().create(API.class);
        Call<Response> call = apiList.rejectOrder(RequestBodyStatusCode,RequestBodyOrderId);

      Log.e("Reject :","Inside Reject params ord Id"+String.valueOf(orderId)+" "+driverId+" " + status);
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                Log.e("Reject :","Inside Reject response "+response.toString());
                if (response.code() == 200) {
                    Response result = response.body();
                    mNetworkResponses.success(result);
                } else {
                    Throwable throwable = new Throwable("updation failed");
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
    public void updatePaymentStatus(int orderId, int id,int isCashRecieved, NetworkResponses mNetworkResponses) {
        RequestBody isCashRecievedBody = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(isCashRecieved));
      //  RequestBody deliveryNoteBody = RequestBody.create(MediaType.parse("multipart/form-data"), deliveryNote);
        RequestBody orderIdBody = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(orderId));
        RequestBody idBody = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(id));
        API apiList = RetrofitClient.getClient().create(API.class);
       // Call<Response> call = apiList.updatePaymentStatus(isCashRecievedBody, orderIdBody, idBody);
        Call<Response> call = apiList.updatePaymentStatus(isCashRecievedBody, orderIdBody, idBody);
        Log.e("Payment :","Inside updatePaymentStatus params ord Id"+String.valueOf(orderId)+" "+id+" " + isCashRecieved);
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {

                Log.e("Payment :","Inside updatePaymentStatus"+response.toString());
                if (response.code() == 200) {
                    Response result = response.body();
                    mNetworkResponses.success(result);
                } else {
                    Throwable throwable = new Throwable("updation failed");
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


    public void makeNewOrder(NewOrderItem newOrderItem, NetworkResponses mNetworkResponses) {
        API apiList = RetrofitClient.getClient().create(API.class);
        Call<Response> call = apiList.makeNewOrder(newOrderItem);
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if (response.code() == 200) {
                    Response result = response.body();
                    mNetworkResponses.success(result);
                } else {
                    Throwable throwable = new Throwable("placing new order failed");
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

    public void fetchCategories(NetworkResponses mNetworkResponses) {
        API apiList = RetrofitClient.getClient().create(API.class);
        Call<CategoryItems> call = apiList.fetchCategories();
        call.enqueue(new Callback<CategoryItems>() {
            @Override
            public void onResponse(Call<CategoryItems> call, retrofit2.Response<CategoryItems> response) {
                if (response.code() == 200) {
                    CategoryItems result = response.body();
                    mNetworkResponses.success(result);
                } else {
                    Throwable throwable = new Throwable("placing new order failed");
                    BaseError baseError = new BaseError(throwable);
                    mNetworkResponses.failed(baseError);
                }
            }

            @Override
            public void onFailure(Call<CategoryItems> call, Throwable t) {
                BaseError baseError = new BaseError(t);
                mNetworkResponses.error(baseError);
            }
        });
    }

    public void fetchPriceDetails(NetworkResponses mNetworkResponses) {
        API apiList = RetrofitClient.getClient().create(API.class);
        Call<PriceDetails> call = apiList.fetchPriceList();
        call.enqueue(new Callback<PriceDetails>() {
            @Override
            public void onResponse(Call<PriceDetails> call, retrofit2.Response<PriceDetails> response) {
                if (response.code() == 200) {
                    PriceDetails result = response.body();
                    mNetworkResponses.success(result);
                } else {
                    Throwable throwable = new Throwable("placing new order failed");
                    BaseError baseError = new BaseError(throwable);
                    mNetworkResponses.failed(baseError);
                }
            }

            @Override
            public void onFailure(Call<PriceDetails> call, Throwable t) {
                BaseError baseError = new BaseError(t);
                mNetworkResponses.error(baseError);
            }
        });
    }

    @Override public void updateDeliveryStatusToDb(int orderId, int statusCode) {
        Thread thread = new Thread(() -> {
            mAppDatabase.mAddressDao().updateDeliveryStatus(orderId, statusCode);
            mAppDatabase.mAddressDao().updateReadStatus(orderId);
        });
        thread.start();

    }

    @Override
    public void deleteOrderFromDb(int orderId) {
        String id = String.valueOf(orderId);
        Thread thread = new Thread(()->{
            mAppDatabase.mAddressDao().deleteAddressByOrderId(id);
        });
    }

}