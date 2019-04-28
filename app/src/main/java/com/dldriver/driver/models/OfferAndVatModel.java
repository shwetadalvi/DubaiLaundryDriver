package com.dldriver.driver.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class OfferAndVatModel {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("result")
    @Expose
    private ArrayList<Result> result;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Result> getResult() {
        return result;
    }

    public void setResult(ArrayList<Result> result) {
        this.result = result;
    }

    public class Result {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("VAT_Per")
        @Expose
        private Integer vATPer;
        @SerializedName("VAT_Status")
        @Expose
        private Integer vATStatus;
        @SerializedName("NASAB_DISC_PER")
        @Expose
        private Integer nASABDISCPER;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getVATPer() {
            return vATPer;
        }

        public void setVATPer(Integer vATPer) {
            this.vATPer = vATPer;
        }

        public Integer getVATStatus() {
            return vATStatus;
        }

        public void setVATStatus(Integer vATStatus) {
            this.vATStatus = vATStatus;
        }

        public Integer getNASABDISCPER() {
            return nASABDISCPER;
        }

        public void setNASABDISCPER(Integer nASABDISCPER) {
            this.nASABDISCPER = nASABDISCPER;
        }

    }
}
