package com.dldriver.driver.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class data1 {

    @SerializedName("Detail")
    @Expose
    private Detail detail ;



    public class Detail {

        @SerializedName("items")
        @Expose
        private ArrayList<Item> items;
        @SerializedName("type")
        @Expose
        private String type;

        public ArrayList<Item> getItems() {
            return items;
        }

        public void setItems(ArrayList<Item> items) {
            this.items = items;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public class Item {

            @SerializedName("item_name")
            @Expose
            private String itemName;
            @SerializedName("item_image")
            @Expose
            private String iteImage;
            @SerializedName("itemId")
            @Expose
            private Integer itemId;
            @SerializedName("itemcount")
            @Expose
            private Integer itemcount;
            @SerializedName("amount")
            @Expose
            private String amount;

            @SerializedName("total")
            @Expose
            private String total;

            public String getItemName() {
                return itemName;
            }

            public void setItemName(String itemName) {
                this.itemName = itemName;
            }

            public String getIteImage() {
                return iteImage;
            }

            public void setIteImage(String iteImage) {
                this.iteImage = iteImage;
            }

            public Integer getItemId() {
                return itemId;
            }

            public void setItemId(Integer itemId) {
                this.itemId = itemId;
            }

            public Integer getItemcount() {
                return itemcount;
            }

            public void setItemcount(Integer itemcount) {
                this.itemcount = itemcount;
            }

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getTotal() {
                return total;
            }

            public void setTotal(String total) {
                this.total = total;
            }
        }

    }
}
