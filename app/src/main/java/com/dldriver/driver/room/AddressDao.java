package com.dldriver.driver.room;

import com.dldriver.driver.models.Address;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface AddressDao {

    @Insert
    void insertNewOrderAddress(Address address);

    @Query("SELECT * FROM Address WHERE isOpened=0")
    LiveData<List<Address>> getNewAddresses();

    @Query("SELECT * FROM Address WHERE id=(SELECT max(id) FROM Address) and isOpened=0")
    LiveData<Address> getLatestAddress();

    @Query("SELECT * FROM Address WHERE orderId=:orderId ")
    LiveData<Address> getAddresses(int orderId);

    @Query("SELECT * FROM Address WHERE orderId=:orderId and deliveryStatus =:deliveryStatus")
    LiveData<Address> getAddresses(int orderId, int deliveryStatus);

    @Query("SELECT * FROM Address WHERE orderId=:orderId")
    Address getAddressByOrder(int orderId);

    @Query("UPDATE Address set isOpened = 1 WHERE orderId=:id")
    void updateReadStatus(int id);

    @Query("UPDATE Address set deliveryStatus = :status WHERE orderId=:id")
    long updateDeliveryStatus(int id, int status);

    @Query("UPDATE Address set paymeentStatus = :status WHERE orderId=:id")
    long updatePaymentStatus(int id, int status);

    @Query("SELECT * FROM Address WHERE paymeentStatus=0")
    LiveData<List<Address>> getAddressesWithoutPayment();

    @Query("SELECT * FROM Address WHERE orderId=:orderId")
    Address getAddressByOrderId(String orderId);
    @Update
    void updateAddress(Address address);
    @Delete()
    void deleteAddress(Address address);

    @Query("DELETE FROM Address WHERE orderId=:orderId")
    void deleteAddressByOrderId(String orderId);
}
