package com.dldriver.driver.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class OrderListdto {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Result")
    @Expose
    private ArrayList<Result> result = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Result> getResult() {
        return result;
    }

    public void setResult(ArrayList<Result> result) {
        this.result = result;
    }

    public class Result {

        @SerializedName("Order_ID")
        @Expose
        private Integer orderID;
        @SerializedName("Order_No")
        @Expose
        private String orderNo;
        @SerializedName("pickup_time")
        @Expose
        private String pickupTime;
        @SerializedName("delivery_time")
        @Expose
        private String deliveryTime;
        @SerializedName("Order_Status")
        @Expose
        private Integer orderStatus;
        @SerializedName("phone_number")
        @Expose
        private String phoneNumber;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("Last_Name")
        @Expose
        private String lastName;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("Address_Name")
        @Expose
        private String addressName;
        @SerializedName("Area")
        @Expose
        private String area;
        @SerializedName("Street")
        @Expose
        private String street;
        @SerializedName("Block")
        @Expose
        private String block;
        @SerializedName("House")
        @Expose
        private String house;
        @SerializedName("Apartment")
        @Expose
        private String apartment;
        @SerializedName("Floor")
        @Expose
        private String floor;
        @SerializedName("CMobile")
        @Expose
        private String cMobile;
        @SerializedName("Additional")
        @Expose
        private String additional;
        @SerializedName("longitude")
        @Expose
        private String longitude;
        @SerializedName("latitude")
        @Expose
        private String latitude;
        @SerializedName("delivery_type")
        @Expose
        private String delivery_type;

        @SerializedName("remarks")
        @Expose
        private String remarks;

        @SerializedName("payment_status")
        @Expose
        private int payment_status;

        @SerializedName("payment_date")
        @Expose
        private String payment_date;

        public String getDelivery_type() {
            return delivery_type;
        }

        public void setDelivery_type(String delivery_type) {
            this.delivery_type = delivery_type;
        }

        public Integer getOrderID() {
            return orderID;
        }

        public void setOrderID(Integer orderID) {
            this.orderID = orderID;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public String getPickupTime() {
            return pickupTime;
        }

        public void setPickupTime(String pickupTime) {
            this.pickupTime = pickupTime;
        }

        public String getDeliveryTime() {
            return deliveryTime;
        }

        public void setDeliveryTime(String deliveryTime) {
            this.deliveryTime = deliveryTime;
        }

        public Integer getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(Integer orderStatus) {
            this.orderStatus = orderStatus;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getAddressName() {
            return addressName;
        }

        public void setAddressName(String addressName) {
            this.addressName = addressName;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getBlock() {
            return block;
        }

        public void setBlock(String block) {
            this.block = block;
        }

        public String getHouse() {
            return house;
        }

        public void setHouse(String house) {
            this.house = house;
        }

        public String getApartment() {
            return apartment;
        }

        public void setApartment(String apartment) {
            this.apartment = apartment;
        }

        public String getFloor() {
            return floor;
        }

        public void setFloor(String floor) {
            this.floor = floor;
        }

        public String getcMobile() {
            return cMobile;
        }

        public void setcMobile(String cMobile) {
            this.cMobile = cMobile;
        }

        public String getAdditional() {
            return additional;
        }


        public void setAdditional(String additional) {
            this.additional = additional;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public int getPayment_status() {
            return payment_status;
        }

        public void setPayment_status(int payment_status) {
            this.payment_status = payment_status;
        }

        public String getPayment_date() {
            return payment_date;
        }

        public void setPayment_date(String payment_date) {
            this.payment_date = payment_date;
        }
    }

}