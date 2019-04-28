package com.dldriver.driver;

import com.dldriver.driver.models.CategoryItems;
import com.dldriver.driver.models.OrderListdto;
import com.dldriver.driver.models.PriceDetails;
import com.dldriver.driver.models.Service;
import com.dldriver.driver.models.data1;
import com.dldriver.driver.models.priceListdto;

import java.util.ArrayList;
import java.util.List;

public class Almoski {

    public static Almoski inst;

    public String address;
    public String pickupTime;
    public String pickupDate;
    public String delDate;
    public String delTime;
    public boolean NisabClub;
    public String email;
    public int orderId;
    public int addressId;


    public double vatRate,nasabRate;
    public List<CategoryItems.Detail.Item> drycleanList;
    public List<CategoryItems.Detail.Item> ironList;
    public List<CategoryItems.Detail.Item> washList;

    public PriceDetails drycleanpriceList;
    public PriceDetails washironpriceList;
    public PriceDetails ironingpriceList;

    public int selecterOrderId;
    public ArrayList<data1.Detail.Item> drycleanList1;
    public ArrayList<data1.Detail.Item> ironList1;
    public ArrayList<data1.Detail.Item> washList1;

    public ArrayList<data1.Detail.Item> drycleanList1temp;
    public ArrayList<data1.Detail.Item> ironList1temp;
    public ArrayList<data1.Detail.Item> washList1temp;
    public String deliveryType;
    public OrderListdto.Result selectedOrder;

    public ArrayList<priceListdto.Detail> itempriceList;

    public int cartcount;
    public int cartamount;
    public List<Service> serviceList;

    public List<Service> getServiceListt() {
        return serviceList;
    }

    public void setServiceList(List<Service> itempriceList) {
        this.serviceList = serviceList;
    }
    public ArrayList<priceListdto.Detail> getItempriceList() {
        return itempriceList;
    }

    public void setItempriceList(ArrayList<priceListdto.Detail> itempriceList) {
        this.itempriceList = itempriceList;
    }

    public int getCartcount() {
        return cartcount;
    }

    public void setCartcount(int cartcount) {
        this.cartcount = cartcount;
    }

    public int getCartamount() {
        return cartamount;
    }

    public void setCartamount(int cartamount) {
        this.cartamount = cartamount;
    }

    public boolean isOffer() {
        return offer;
    }

    public void setOffer(boolean offer) {
        this.offer = offer;
    }

    public String getCurentOrderId() {
        return curentOrderId;
    }

    public void setCurentOrderId(String curentOrderId) {
        this.curentOrderId = curentOrderId;
    }

    public ArrayList<OrderListdto.Result> getCurrentOrders() {
        return currentOrders;
    }

    public void setCurrentOrders(ArrayList<OrderListdto.Result> currentOrders) {
        this.currentOrders = currentOrders;
    }

    public boolean offer;
    public String curentOrderId;
    public ArrayList<OrderListdto.Result> currentOrders;
    public ArrayList<data1.Detail.Item> getDrycleanList1() {
        return drycleanList1;
    }

    public void setDrycleanList1(ArrayList<data1.Detail.Item> drycleanList1) {
        this.drycleanList1 = drycleanList1;
    }

    public ArrayList<data1.Detail.Item> getIronList1() {
        return ironList1;
    }

    public void setIronList1(ArrayList<data1.Detail.Item> ironList1) {
        this.ironList1 = ironList1;
    }

    public ArrayList<data1.Detail.Item> getWashList1() {
        return washList1;
    }

    public void setWashList1(ArrayList<data1.Detail.Item> washList1) {
        this.washList1 = washList1;
    }

    public ArrayList<data1.Detail.Item> getDrycleanList1temp() {
        return drycleanList1temp;
    }

    public void setDrycleanList1temp(ArrayList<data1.Detail.Item> drycleanList1temp) {
        this.drycleanList1temp = drycleanList1temp;
    }

    public ArrayList<data1.Detail.Item> getIronList1temp() {
        return ironList1temp;
    }

    public void setIronList1temp(ArrayList<data1.Detail.Item> ironList1temp) {
        this.ironList1temp = ironList1temp;
    }

    public ArrayList<data1.Detail.Item> getWashList1temp() {
        return washList1temp;
    }

    public void setWashList1temp(ArrayList<data1.Detail.Item> washList1temp) {
        this.washList1temp = washList1temp;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static void setInst(Almoski inst) {
        Almoski.inst = inst;
    }

    public List<CategoryItems.Detail.Item> getDrycleanList() {
        return drycleanList;
    }

    public void setDrycleanList(List<CategoryItems.Detail.Item> drycleanList) {
        this.drycleanList = drycleanList;
    }

    public List<CategoryItems.Detail.Item> getIronList() {
        return ironList;
    }

    public void setIronList(List<CategoryItems.Detail.Item> ironList) {
        this.ironList = ironList;
    }

    public List<CategoryItems.Detail.Item> getWashList() {
        return washList;
    }

    public void setWashList(List<CategoryItems.Detail.Item> washList) {
        this.washList = washList;
    }

    public PriceDetails getDrycleanpriceList() {
        return drycleanpriceList;
    }

    public void setDrycleanpriceList(PriceDetails drycleanpriceList) {
        this.drycleanpriceList = drycleanpriceList;
    }

    public PriceDetails getWashironpriceList() {
        return washironpriceList;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setWashironpriceList(PriceDetails washironpriceList) {
        this.washironpriceList = washironpriceList;
    }

    public PriceDetails getIroningpriceList() {
        return ironingpriceList;
    }

    public void setIroningpriceList(PriceDetails ironingpriceList) {
        this.ironingpriceList = ironingpriceList;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(String pickupTime) {
        this.pickupTime = pickupTime;
    }

    public String getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(String pickupDate) {
        this.pickupDate = pickupDate;
    }

    public String getDelDate() {
        return delDate;
    }

    public void setDelDate(String delDate) {
        this.delDate = delDate;
    }

    public String getDelTime() {
        return delTime;
    }

    public void setDelTime(String delTime) {
        this.delTime = delTime;
    }
    public boolean isNisabClub() {
        return NisabClub;
    }

    public void setNisabClub(boolean nisabClub) {
        NisabClub = nisabClub;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }
    public double getVatRate() {
        return vatRate;
    }

    public void setVatRate(double vatRate) {
        this.vatRate = vatRate;
    }

    public double getNasabRate() {
        return nasabRate;
    }

    public void setNasabRate(double nasabRate) {
        this.nasabRate = nasabRate;
    }
    public int getSelecterOrderId() {
        return selecterOrderId;
    }

    public void setSelecterOrderId(int selecterOrderId) {
        this.selecterOrderId = selecterOrderId;
    }
    public OrderListdto.Result getSelectedOrder() {
        return selectedOrder;
    }

    public void setSelectedOrder(OrderListdto.Result selectedOrder) {
        this.selectedOrder = selectedOrder;
    }

    public static Almoski getInst() {
        if (inst == null) {
            inst = new Almoski();
        }
        return inst;
    }
}
