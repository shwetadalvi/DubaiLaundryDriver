package com.dldriver.driver.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class priceListdto {
    @SerializedName("Detail")
    @Expose
    private ArrayList<Detail> detail = null;

    public ArrayList<Detail> getDetail() {
        return detail;
    }

    public void setDetail(ArrayList<Detail> detail) {
        this.detail = detail;
    }

    public class Detail {

        @SerializedName("items")
        @Expose
        private ArrayList<Item> items = null;
        @SerializedName("ServiceId")
        @Expose
        private Integer serviceId;
        @SerializedName("ServiceName")
        @Expose
        private String serviceName;

        public ArrayList<Item> getItems() {
            return items;
        }

        public void setItems(ArrayList<Item> items) {
            this.items = items;
        }

        public Integer getServiceId() {
            return serviceId;
        }

        public void setServiceId(Integer serviceId) {
            this.serviceId = serviceId;
        }

        public String getServiceName() {
            return serviceName;
        }

        public void setServiceName(String serviceName) {
            this.serviceName = serviceName;
        }



        public class Item {

            @SerializedName("ItemId")
            @Expose
            private Integer itemId;
            @SerializedName("PriceName")
            @Expose
            private String priceName;
            @SerializedName("categoryId")
            @Expose
            private Integer categoryId;
            @SerializedName("ItemName")
            @Expose
            private String itemName;
            @SerializedName("Price")
            @Expose
            private String price;
            @SerializedName("DeliveryType")
            @Expose
            private String deliveryType;
            @SerializedName("CategoryName")
            @Expose
            private String categoryName;
            @SerializedName("ServiceName")
            @Expose
            private String serviceName;
            @SerializedName("ServiceId")
            @Expose
            private Integer serviceId;

            public Integer getItemId() {
                return itemId;
            }

            public void setItemId(Integer itemId) {
                this.itemId = itemId;
            }

            public String getPriceName() {
                return priceName;
            }

            public void setPriceName(String priceName) {
                this.priceName = priceName;
            }

            public Integer getCategoryId() {
                return categoryId;
            }

            public void setCategoryId(Integer categoryId) {
                this.categoryId = categoryId;
            }

            public String getItemName() {
                return itemName;
            }

            public void setItemName(String itemName) {
                this.itemName = itemName;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getDeliveryType() {
                return deliveryType;
            }

            public void setDeliveryType(String deliveryType) {
                this.deliveryType = deliveryType;
            }

            public String getCategoryName() {
                return categoryName;
            }

            public void setCategoryName(String categoryName) {
                this.categoryName = categoryName;
            }

            public String getServiceName() {
                return serviceName;
            }

            public void setServiceName(String serviceName) {
                this.serviceName = serviceName;
            }

            public Integer getServiceId() {
                return serviceId;
            }

            public void setServiceId(Integer serviceId) {
                this.serviceId = serviceId;
            }

        }
    }

}
