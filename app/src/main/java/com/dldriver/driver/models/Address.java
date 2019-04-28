package com.dldriver.driver.models;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Address {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String email;
    private int addressId;
    private String orderId;
    private String orderNo;
    private String Additional;
    private String Address_Name;
    private String Apartment;
    private String Area;
    private String Block;
    private String Floor;
    private String Full_Name;
    private String House;
    private String Mobile;
    private String Street;
    private String delType;
    private String timeSlot;
    private boolean isOpened;
    private String OrderDate;
    private String OrderTime;
    private String DelTime;
    private String DelDate;
    private double latitude;
    private double longitude;
    private int deliveryStatus;
    private String category;
    @Ignore
    private String fullAddress;
    private int paymeentStatus;

    public Address() {
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    @Ignore
    public Address(int addressId,String email,String orderId, String orderNo,
                   String additional, String address_Name,
                   String apartment, String area,
                   String block, String floor, String full_Name,
                   String house, String mobile, String street,
                   String delType, String timeSlot, String orderDate,
                   String orderTime, String delTime, String delDate,
                   double lat, double lng, boolean isOpened,
                   String category, int deliveryStatus,int paymeentStatus) {
        this.addressId = addressId;
        this.orderId = orderId;
        this.orderNo = orderNo;
        Additional = additional;
        Address_Name = address_Name;
        Apartment = apartment;
        Area = area;
        Block = block;
        Floor = floor;
        Full_Name = full_Name;
        House = house;
        Mobile = mobile;
        Street = street;
        this.delType = delType;
        this.timeSlot = timeSlot;
        this.isOpened = isOpened;
        OrderDate = orderDate;
        OrderTime = orderTime;
        DelTime = delTime;
        DelDate = delDate;
        latitude = lat;
        longitude = lng;
        this.category = category;
        this.deliveryStatus = deliveryStatus;
        this.paymeentStatus=paymeentStatus;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getAdditional() {
        return Additional;
    }

    public void setAdditional(String additional) {
        Additional = additional;
    }

    public String getAddress_Name() {
        return Address_Name;
    }

    public void setAddress_Name(String address_Name) {
        Address_Name = address_Name;
    }

    public String getApartment() {
        return Apartment;
    }

    public void setApartment(String apartment) {
        Apartment = apartment;
    }

    public String getArea() {
        return Area;
    }

    public void setArea(String area) {
        Area = area;
    }

    public String getBlock() {
        return Block;
    }

    public void setBlock(String block) {
        Block = block;
    }

    public String getFloor() {
        return Floor;
    }

    public void setFloor(String floor) {
        Floor = floor;
    }

    public String getFull_Name() {
        return Full_Name;
    }

    public void setFull_Name(String full_Name) {
        Full_Name = full_Name;
    }

    public String getHouse() {
        return House;
    }

    public void setHouse(String house) {
        House = house;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getStreet() {
        return Street;
    }

    public void setStreet(String street) {
        Street = street;
    }

    public boolean isOpened() {
        return isOpened;
    }

    public void setOpened(boolean opened) {
        isOpened = opened;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getDelType() {
        return delType;
    }

    public void setDelType(String delType) {
        this.delType = delType;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public String getFullAddress() {
        String address = House + ", " +
                Apartment + ", " +
                Floor + ", " +
                Block + ", " +
                Area + ", " +
                Street + ", " +
                Additional + ", ";
        return address;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public String getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(String orderDate) {
        OrderDate = orderDate;
    }

    public String getOrderTime() {
        return OrderTime;
    }

    public void setOrderTime(String orderTime) {
        OrderTime = orderTime;
    }

    public String getDelTime() {
        return DelTime;
    }

    public void setDelTime(String delTime) {
        DelTime = delTime;
    }

    public String getDelDate() {
        return DelDate;
    }

    public void setDelDate(String delDate) {
        DelDate = delDate;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(int deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getPaymeentStatus() {
        return paymeentStatus;
    }

    public void setPaymeentStatus(int paymeentStatus) {
        this.paymeentStatus = paymeentStatus;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
