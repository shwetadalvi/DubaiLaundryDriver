
package com.dldriver.driver.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Order_ {

    @SerializedName("orderNo")
    @Expose
    private String orderNo;
    @SerializedName("price")
    @Expose
    private Double price;
    @SerializedName("orderType")
    @Expose
    private Integer orderType;
    @SerializedName("remarks")
    @Expose
    private String remarks;
    private Double vat;
    private Double exlusivePrice;
    @SerializedName("deliveryType")
    @Expose
    private Integer deliveryType;
    @SerializedName("service")
    @Expose
    private List<Service> service = null;

    @SerializedName("pickupDriver")
    @Expose
    private String pickupDriver;
    @SerializedName("deliveryDriver")
    @Expose
    private String deliveryDriver;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(Integer deliveryType) {
        this.deliveryType = deliveryType;
    }

    public List<Service> getService() {
        return service;
    }

    public void setService(List<Service> service) {
        this.service = service;
    }

    public Double getVat() {
        return vat;
    }

    public void setVat(Double vat) {
        this.vat = vat;
    }

    public Double getExlusivePrice() {
        return exlusivePrice;
    }

    public void setExlusivePrice(Double exlusivePrice) {
        this.exlusivePrice = exlusivePrice;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getPickupDriver() {
        return pickupDriver;
    }

    public void setPickupDriver(String pickupDriver) {
        this.pickupDriver = pickupDriver;
    }

    public String getDeliveryDriver() {
        return deliveryDriver;
    }

    public void setDeliveryDriver(String deliveryDriver) {
        this.deliveryDriver = deliveryDriver;
    }
}
