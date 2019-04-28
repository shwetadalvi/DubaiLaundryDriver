
package com.dldriver.driver.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Order extends BaseError{

    @SerializedName("Order")
    @Expose
    private Order_ order;

    public Order(Throwable throwable) {
        super(throwable);
    }

    public Order_ getOrder() {
        return order;
    }

    public void setOrder(Order_ order) {
        this.order = order;
    }

}
