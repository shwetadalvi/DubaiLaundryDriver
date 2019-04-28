package com.dldriver.driver.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class RemarkList {
    @SerializedName("Result")
    @Expose
    private List<Remarks> result = null;

    public List<Remarks> getResult() {
        return result;
    }

    public void setResult(List<Remarks> result) {
        this.result = result;
    }

}