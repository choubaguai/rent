package com.xinlong.rent.daomain;


import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 账单实体类
 *
 * @author fukai
 */
public class Bill implements Serializable {

    private static final long serialVersionUID = 1L;

    private String bill_id;//账单ID，主键
    private String tenement_id;//房屋Id，外键，对应房屋主键
    private String tenant_id;//租户ID，外键，对应租户主键
    private String bill_name;//账单名称
    private Date generated_date;//生成时间
    private String month_dt;//生成年月
    private Integer is_or_not_pay;//是否交租，1：是，2：否，默认1
    private BigDecimal rent;//租金
    private String remark;//备注
    private Integer is_delete;
    private Integer bill_type;//账单类型0捆绑1房租2抄表
    private BigDecimal cash_pledge;
    private BigDecimal bill_rent;//租客租金

    public BigDecimal getBill_rent() {
        return bill_rent;
    }

    public void setBill_rent(BigDecimal bill_rent) {
        this.bill_rent = bill_rent;
    }

    public BigDecimal getCash_pledge() {
        return cash_pledge;
    }

    public void setCash_pledge(BigDecimal cash_pledge) {
        this.cash_pledge = cash_pledge;
    }

    public String getMonth_dt() {
        return month_dt;
    }

    public void setMonth_dt(String month_dt) {
        this.month_dt = month_dt;
    }

    public String getBill_id() {
        return bill_id;
    }

    public void setBill_id(String bill_id) {
        this.bill_id = bill_id;
    }

    public String getTenement_id() {
        return tenement_id;
    }

    public void setTenement_id(String tenement_id) {
        this.tenement_id = tenement_id;
    }

    public String getTenant_id() {
        return tenant_id;
    }

    public void setTenant_id(String tenant_id) {
        this.tenant_id = tenant_id;
    }

    public String getBill_name() {
        return bill_name;
    }

    public void setBill_name(String bill_name) {
        this.bill_name = bill_name;
    }
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getGenerated_date() {
        return generated_date;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public void setGenerated_date(Date generated_date) {
        this.generated_date = generated_date;
    }

    public Integer getIs_or_not_pay() {
        return is_or_not_pay;
    }

    public void setIs_or_not_pay(Integer is_or_not_pay) {
        this.is_or_not_pay = is_or_not_pay;
    }

    public BigDecimal getRent() {
        return rent;
    }

    public void setRent(BigDecimal rent) {
        this.rent = rent;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getIs_delete() {
        return is_delete;
    }

    public void setIs_delete(Integer is_delete) {
        this.is_delete = is_delete;
    }

    public Integer getBill_type() {
        return bill_type;
    }

    public void setBill_type(Integer bill_type) {
        this.bill_type = bill_type;
    }
}
