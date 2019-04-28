package com.dldriver.driver.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PriceDetails {

    @SerializedName("Detail")
    @Expose
    private List<Detail> detail = null;

    public List<Detail> getDetail() {
        return detail;
    }

    public void setDetail(List<Detail> detail) {
        this.detail = detail;
    }

    public class Detail {

        @SerializedName("items")
        @Expose
        private List<Item> items = null;
        @SerializedName("ServiceId")
        @Expose
        private Integer serviceId;
        @SerializedName("ServiceName")
        @Expose
        private String serviceName;

        public List<Item> getItems() {
            return items;
        }

        public void setItems(List<Item> items) {
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
            private Float price;
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

            public Float getPrice() {
                return price;
            }

            public void setPrice(Float price) {
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