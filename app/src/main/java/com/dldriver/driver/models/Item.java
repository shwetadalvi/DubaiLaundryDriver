
package com.dldriver.driver.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Item {

    @SerializedName("serviceName")
    @Expose
    private String serviceName;
    @SerializedName("OrderItems")
    @Expose
    private OrderItems orderItems;

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public OrderItems getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(OrderItems orderItems) {
        this.orderItems = orderItems;
    }

}
