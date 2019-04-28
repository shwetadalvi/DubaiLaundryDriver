package com.dldriver.driver.contracts;

import com.dldriver.driver.NetworkResponses;
import com.dldriver.driver.models.Address;
import com.dldriver.driver.models.Order;
import com.dldriver.driver.room.AppDatabase;

import java.util.concurrent.atomic.AtomicReference;

public interface OrderContracts {
    interface IOrderView {
        void success(String message);

        void failed(String message);

        void error(String message);

        void parseOrderDetails(Order order);

        void setUpFirstColumn(Address address);

        void trackingUpdated(String message);

        void showMessage(String message);
    }

    interface IOrderPresenter {
        void getORderDetail();

        void passFetchedAddress(AtomicReference<Address> address);

        void getAddress(int orderId);

        void updateTrackingStatus(int status);

        void rejectOrder(int status,int driverId,int orderId);
    }

    interface IOrderInteracter {
        void getOrderDetail(int orderId);

        void getAddressDetail(AppDatabase appDatabase, int orderId);

        void updateTrakingStatus(int orderId, int status,NetworkResponses mNetworkResponses);
        void updateDeliveryStatusToDb(int orderId,int statusCode);
        void deleteOrderFromDb(int orderId);
    }
}
