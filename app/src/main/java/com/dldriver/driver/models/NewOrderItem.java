
package com.dldriver.driver.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewOrderItem {

    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("pickdate")
    @Expose
    private String pickdate;
    @SerializedName("picktime")
    @Expose
    private String picktime;
    @SerializedName("deldate")
    @Expose
    private String deldate;
    @SerializedName("deltime")
    @Expose
    private String deltime;
    @SerializedName("itemAmount")
    @Expose
    private Float itemAmount;
    @SerializedName("remarks")
    @Expose
    private String remarks;
    @SerializedName("addressId")
    @Expose
    private Integer addressId;
    @SerializedName("orderId")
    @Expose
    private Integer orderId;
    @SerializedName("totalamount")
    @Expose
    private Float totalamount;
    @SerializedName("nasabDiscountAmount")
    @Expose
    private Float nasabDiscountAmount;
    @SerializedName("vatAmount")
    @Expose
    private Float vatAmount;
    @SerializedName("orders")
    @Expose
    private List<Order> orders = null;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPickdate() {
        return pickdate;
    }

    public void setPickdate(String pickdate) {
        this.pickdate = pickdate;
    }

    public String getPicktime() {
        return picktime;
    }

    public void setPicktime(String picktime) {
        this.picktime = picktime;
    }

    public String getDeldate() {
        return deldate;
    }

    public void setDeldate(String deldate) {
        this.deldate = deldate;
    }

    public String getDeltime() {
        return deltime;
    }

    public void setDeltime(String deltime) {
        this.deltime = deltime;
    }

    public Float getItemAmount() {
        return itemAmount;
    }

    public void setItemAmount(Float itemAmount) {
        this.itemAmount = itemAmount;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Float getTotalamount() {
        return totalamount;
    }

    public void setTotalamount(Float totalamount) {
        this.totalamount = totalamount;
    }

    public Float getNasabDiscountAmount() {
        return nasabDiscountAmount;
    }

    public void setNasabDiscountAmount(Float nasabDiscountAmount) {
        this.nasabDiscountAmount = nasabDiscountAmount;
    }

    public Float getVatAmount() {
        return vatAmount;
    }

    public void setVatAmount(Float vatAmount) {
        this.vatAmount = vatAmount;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public class Order {

        @SerializedName("ItemId")
        @Expose
        private Integer itemId;
        @SerializedName("ServiceId")
        @Expose
        private Integer serviceId;
        @SerializedName("Qty")
        @Expose
        private Integer qty;
        @SerializedName("Price")
        @Expose
        private Float price;
        @SerializedName("Item_Name")
        @Expose
        private String itemName;
        @SerializedName("deliveryType")
        @Expose
        private Integer deliveryType;

        public Integer getItemId() {
            return itemId;
        }

        public void setItemId(Integer itemId) {
            this.itemId = itemId;
        }

        public Integer getServiceId() {
            return serviceId;
        }

        public void setServiceId(Integer serviceId) {
            this.serviceId = serviceId;
        }

        public Integer getQty() {
            return qty;
        }

        public void setQty(Integer qty) {
            this.qty = qty;
        }

        public Float getPrice() {
            return price;
        }

        public void setPrice(Float price) {
            this.price = price;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public Integer getDeliveryType() {
            return deliveryType;
        }

        public void setDeliveryType(Integer deliveryType) {
            this.deliveryType = deliveryType;
        }

    }
}
