package com.xinlong.rent.daomain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class Customer {

    private String customer_id;
    private String customer_name ;
    private String  customer_tel;
    private String customer_passwrod;
    private String  customer_number;
    private Integer  is_cancellation;
    private Date customer_dt;
    private String remarks;

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getCustomer_tel() {
        return customer_tel;
    }

    public void setCustomer_tel(String customer_tel) {
        this.customer_tel = customer_tel;
    }

    public String getCustomer_passwrod() {
        return customer_passwrod;
    }

    public void setCustomer_passwrod(String customer_passwrod) {
        this.customer_passwrod = customer_passwrod;
    }

    public String getCustomer_number() {
        return customer_number;
    }

    public void setCustomer_number(String customer_number) {
        this.customer_number = customer_number;
    }

    public Integer getIs_cancellation() {
        return is_cancellation;
    }

    public void setIs_cancellation(Integer is_cancellation) {
        this.is_cancellation = is_cancellation;
    }
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getCustomer_dt() {
        return customer_dt;
    }
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public void setCustomer_dt(Date customer_dt) {
        this.customer_dt = customer_dt;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
