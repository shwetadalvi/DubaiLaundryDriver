package com.dldriver.driver.presenter;

import com.dldriver.driver.NetworkResponses;
import com.dldriver.driver.contracts.OrderContracts;
import com.dldriver.driver.interactors.OrderInteracterImpl;
import com.dldriver.driver.models.Address;
import com.dldriver.driver.models.BaseError;
import com.dldriver.driver.models.Order;
import com.dldriver.driver.models.Response;
import com.dldriver.driver.room.AppDatabase;

import java.util.concurrent.atomic.AtomicReference;

public class OrderPesenterImpl implements OrderContracts.IOrderPresenter, NetworkResponses<Order> {

    private final OrderInteracterImpl mOrderInteracter;
    private OrderContracts.IOrderView mOrderView;
    private AppDatabase mAppDatabase;
    private int mOrderId;

    public OrderPesenterImpl(OrderContracts.IOrderView orderView, int orderId, AppDatabase appDatabase) {
        mOrderView = orderView;
        mOrderId = orderId;
        mAppDatabase = appDatabase;
        mOrderInteracter = new OrderInteracterImpl(this, this);
    }

    @Override
    public void getORderDetail() {
        mOrderInteracter.getOrderDetail(mOrderId);
    }

    @Override
    public void passFetchedAddress(AtomicReference<Address> address) {
        mOrderView.setUpFirstColumn(address.get());
    }

    @Override
    public void getAddress(int orderId) {
        mOrderInteracter.getAddressDetail(mAppDatabase, mOrderId);
    }

    @Override
    public void updateTrackingStatus(int status) {
        mOrderInteracter.updateTrakingStatus(mOrderId, status, new NetworkResponses<Response>() {
            @Override
            public void success(Response response) {
                mOrderInteracter.updateDeliveryStatusToDb(mOrderId, status);
                mOrderView.showMessage("Status updated");
            }

            @Override
            public void failed(Response failedResonse) {
                mOrderView.showMessage(((BaseError) failedResonse).getThrowable().getMessage());
            }

            @Override
            public void error(BaseError errorMessage) {
                mOrderView.showMessage(((BaseError) errorMessage).getThrowable().getMessage());
            }
        });
    }

    @Override
    public void rejectOrder(int status, int driverId, int orderId) {
        mOrderInteracter.rejectOrder(status, driverId, orderId, new NetworkResponses<Response>() {
            @Override
            public void success(Response response) {
                mOrderInteracter.updateDeliveryStatusToDb(mOrderId, status);
              //  mOrderInteracter.deleteOrderFromDb(mOrderId);
                mOrderView.showMessage("Status updated");
            }

            @Override
            public void failed(Response failedResonse) {
                mOrderView.showMessage(((BaseError) failedResonse).getThrowable().getMessage());
            }

            @Override
            public void error(BaseError errorMessage) {
                mOrderView.showMessage(((BaseError) errorMessage).getThrowable().getMessage());
            }
        });
    }




    @Override public void success(Order response) {
        mOrderView.success("dsasdasd");
        mOrderView.parseOrderDetails(response);
    }

    @Override public void failed(Order failedResonse) {
        mOrderView.failed("gjkljkhjghgfda");
    }

    @Override public void error(BaseError errorMessage) {
        mOrderView.error("khjhgfd");
    }
}