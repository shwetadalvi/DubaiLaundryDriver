package com.dldriver.driver.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Remarks {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("remarks")
    @Expose
    private String remarks;
    @SerializedName("cdate")
    @Expose
    private String cdate;
    @SerializedName("value")
    @Expose
    private Integer value;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getCdate() {
        return cdate;
    }

    public void setCdate(String cdate) {
        this.cdate = cdate;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

}