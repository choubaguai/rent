package com.xinlong.rent.daomain;


import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 *  租客（用户）实体类
 */
public class Tenant implements Serializable {

    private String tenant_id;//租客ID，主键
    private String tenement_id;//房屋ID，外键，对应房屋主键
    private String tenant_name;//租客名称
    private String tenant_code;//租客编号
    private String tenant_password;//租客密码
    private String id_card;//租客身份证号
    private Date start_date;//起租日期
    private Date end_date;//到期日期
    private String collect_date;//收租日期
    private String produce_cycle;//收租周期
    private BigDecimal rent;//交租金额
    private BigDecimal cash_pledge;//押金
    private Integer rental_num;//租房人数
    private String remark;//备注
    private Integer is_delete;

    public String getTenant_id() {
        return tenant_id;
    }

    public void setTenant_id(String tenant_id) {
        this.tenant_id = tenant_id;
    }

    public String getTenement_id() {
        return tenement_id;
    }

    public void setTenement_id(String tenement_id) {
        this.tenement_id = tenement_id;
    }

    public String getTenant_name() {
        return tenant_name;
    }

    public void setTenant_name(String tenant_name) {
        this.tenant_name = tenant_name;
    }

    public String getTenant_code() {
        return tenant_code;
    }

    public void setTenant_code(String tenant_code) {
        this.tenant_code = tenant_code;
    }

    public String getTenant_password() {
        return tenant_password;
    }

    public void setTenant_password(String tenant_password) {
        this.tenant_password = tenant_password;
    }

    public String getId_card() {
        return id_card;
    }

    public void setId_card(String id_card) {
        this.id_card = id_card;
    }
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    public Date getStart_date() {
        return start_date;
    }
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    public Date getEnd_date() {
        return end_date;
    }
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public String getCollect_date() { return collect_date;}

    public void setCollect_date(String collect_date) {  this.collect_date = collect_date; }

    public String getProduce_cycle() { return produce_cycle; }

    public void setProduce_cycle(String produce_cycle) { this.produce_cycle = produce_cycle; }

    public BigDecimal getRent() {
        return rent;
    }

    public void setRent(BigDecimal rent) {
        this.rent = rent;
    }

    public BigDecimal getCash_pledge() {
        return cash_pledge;
    }

    public void setCash_pledge(BigDecimal cash_pledge) {
        this.cash_pledge = cash_pledge;
    }

    public Integer getRental_num() {
        return rental_num;
    }

    public void setRental_num(Integer rental_num) {
        this.rental_num = rental_num;
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
}
