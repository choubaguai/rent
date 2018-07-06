package com.xinlong.rent.daomain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

public class Relet implements Serializable {


    private String tenement_id;
    private String house_id;
    private String tenement_code;
    private String tenant_name;
    private String tenant_id;
    private Date end_date;

    public String getTenement_id() {
        return tenement_id;
    }

    public void setTenement_id(String tenement_id) {
        this.tenement_id = tenement_id;
    }

    public String getHouse_id() {
        return house_id;
    }

    public void setHouse_id(String house_id) {
        this.house_id = house_id;
    }

    public String getTenement_code() {
        return tenement_code;
    }

    public void setTenement_code(String tenement_code) {
        this.tenement_code = tenement_code;
    }

    public String getTenant_name() {
        return tenant_name;
    }

    public void setTenant_name(String tenant_name) {
        this.tenant_name = tenant_name;
    }

    public String getTenant_id() {
        return tenant_id;
    }

    public void setTenant_id(String tenant_id) {
        this.tenant_id = tenant_id;
    }
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    public Date getEnd_date() {
        return end_date;
    }
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }
}
