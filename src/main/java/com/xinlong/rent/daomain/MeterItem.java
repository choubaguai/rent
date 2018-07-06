package com.xinlong.rent.daomain;

import com.fasterxml.jackson.annotation.JsonFormat;
import oracle.sql.DATE;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 抄表项
 *
 * @author NAN
 */
public class MeterItem implements Serializable {


    private String meter_item_id;   //抄表项ID
    private String tenement_id;    //房屋ID
    private String meter_item_name; //抄表项名称
    private Integer start_code;   //起码
    private Integer end_code;       //止码
    private Integer consumption;     //用量
    private BigDecimal unit_price;     //单价
    private BigDecimal amount;           //金额
    private Date meter_item_dt;   //生成时间
    private String  meter_item_month;//抄表年月
    private String  remarks;         //备注
    private String tenant_id;//租户ID，外键，对应租户主键
    private Integer is_bundle;       //是否捆绑0捆绑1未捆绑
    private Integer is_pay;       //是否交付0未交付1已交付
    private String bill_id;

    public String getBill_id() {
        return bill_id;
    }

    public void setBill_id(String bill_id) {
        this.bill_id = bill_id;
    }

    public String getMeter_item_id() {
        return meter_item_id;
    }

    public void setMeter_item_id(String meter_item_id) {
        this.meter_item_id = meter_item_id;
    }

    public String getTenement_id() {
        return tenement_id;
    }

    public void setTenement_id(String tenement_id) {
        this.tenement_id = tenement_id;
    }

    public String getMeter_item_name() {
        return meter_item_name;
    }

    public void setMeter_item_name(String meter_item_name) {
        this.meter_item_name = meter_item_name;
    }

    public Integer getStart_code() {
        return start_code;
    }

    public void setStart_code(Integer start_code) {
        this.start_code = start_code;
    }

    public Integer getEnd_code() {
        return end_code;
    }

    public void setEnd_code(Integer end_code) {
        this.end_code = end_code;
    }

    public Integer getConsumption() {
        return consumption;
    }

    public void setConsumption(Integer consumption) {
        this.consumption = consumption;
    }

    public BigDecimal getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(BigDecimal unit_price) {
        this.unit_price = unit_price;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getMeter_item_dt() {
        return meter_item_dt;
    }
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public void setMeter_item_dt(Date meter_item_dt) {
        this.meter_item_dt = meter_item_dt;
    }

    public String getMeter_item_month() {
        return meter_item_month;
    }

    public void setMeter_item_month(String meter_item_month) {
        this.meter_item_month = meter_item_month;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getTenant_id() {
        return tenant_id;
    }

    public void setTenant_id(String tenant_id) {
        this.tenant_id = tenant_id;
    }

    public Integer getIs_bundle() {
        return is_bundle;
    }

    public void setIs_bundle(Integer is_bundle) {
        this.is_bundle = is_bundle;
    }

    public Integer getIs_pay() {
        return is_pay;
    }

    public void setIs_pay(Integer is_pay) {
        this.is_pay = is_pay;
    }
}
