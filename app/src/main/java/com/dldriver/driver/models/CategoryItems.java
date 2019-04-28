
package com.dldriver.driver.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CategoryItems{

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
        private ArrayList<Item> items = null;
        @SerializedName("categoryId")
        @Expose
        private String categoryId;
        @SerializedName("categoryName")
        @Expose
        private String categoryName;
        @SerializedName("CategoryIcons")
        @Expose
        private String categoryIcons;

        @SerializedName("type")
        @Expose
        private String type;

        public List<Item> getItems() {
            return items;
        }

        public void setItems(List<Item> items) {
            this.items = (ArrayList<Item>) items;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public String getCategoryIcons() {
            return categoryIcons;
        }

        public void setCategoryIcons(String categoryIcons) {
            this.categoryIcons = categoryIcons;
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
            private String itemImage;
            @SerializedName("categoryId")
            @Expose
            private Integer categoryId;
            @SerializedName("itemId")
            @Expose
            private Integer itemId;
            @SerializedName("categoryName")
            @Expose
            private String categoryName;
            @SerializedName("CategoryIcons")
            @Expose
            private String categoryIcons;
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

            public String getItemImage() {
                return itemImage;
            }

            public void setItemImage(String itemImage) {
                this.itemImage = itemImage;
            }

            public Integer getCategoryId() {
                return categoryId;
            }

            public void setCategoryId(Integer categoryId) {
                this.categoryId = categoryId;
            }

            public Integer getItemId() {
                return itemId;
            }

            public void setItemId(Integer itemId) {
                this.itemId = itemId;
            }

            public String getCategoryName() {
                return categoryName;
            }

            public void setCategoryName(String categoryName) {
                this.categoryName = categoryName;
            }

            public String getCategoryIcons() {
                return categoryIcons;
            }

            public void setCategoryIcons(String categoryIcons) {
                this.categoryIcons = categoryIcons;
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
