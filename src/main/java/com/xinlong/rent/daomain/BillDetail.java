package com.xinlong.rent.daomain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


public class BillDetail {

    private String bill_name;//账单名称
    private Date generated_date;//生成时间
    private String tenant_name;//租客名称
    private String tenant_code;//租客编号
    private Integer is_or_not_pay;//是否交租，1：是，2：否，默认1
    private BigDecimal  fixed_water; //固定水费
    private BigDecimal  network_fee;  //网费
    private BigDecimal  sanitation_fee ; //卫生费
    private BigDecimal else_fee ;  //其他费用
    private String  cost_item_month; //月份
    private Integer  bill_type; //0租金+抄表项+基本费用1房屋租金+房屋基本费2房屋电费账单
    private BigDecimal rent;//总金额
    private BigDecimal billRent;//租客租金
    private List<MeterItem> metslist;
    private BigDecimal cash_pledge;//押金
    private String produce_cycle;//收租周期


    public String getProduce_cycle() {
        return produce_cycle;
    }

    public void setProduce_cycle(String produce_cycle) {
        this.produce_cycle = produce_cycle;
    }
    public BigDecimal getCash_pledge() {
        return cash_pledge;
    }

    public void setCash_pledge(BigDecimal cash_pledge) {
        this.cash_pledge = cash_pledge;
    }

    public BigDecimal getBillRent() {
        return billRent;
    }

    public void setBillRent(BigDecimal billRent) {
        this.billRent = billRent;
    }

    public Integer getBill_type() {
        return bill_type;
    }

    public void setBill_type(Integer bill_type) {
        this.bill_type = bill_type;
    }

    public String getBill_name() {
        return bill_name;
    }

    public void setBill_name(String bill_name) {
        this.bill_name = bill_name;
    }
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    public Date getGenerated_date() {
        return generated_date;
    }
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public void setGenerated_date(Date generated_date) {
        this.generated_date = generated_date;
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

    public Integer getIs_or_not_pay() {
        return is_or_not_pay;
    }

    public void setIs_or_not_pay(Integer is_or_not_pay) {
        this.is_or_not_pay = is_or_not_pay;
    }

    public BigDecimal getFixed_water() {
        return fixed_water;
    }

    public void setFixed_water(BigDecimal fixed_water) {
        this.fixed_water = fixed_water;
    }

    public BigDecimal getNetwork_fee() {
        return network_fee;
    }

    public void setNetwork_fee(BigDecimal network_fee) {
        this.network_fee = network_fee;
    }

    public BigDecimal getSanitation_fee() {
        return sanitation_fee;
    }

    public void setSanitation_fee(BigDecimal sanitation_fee) {
        this.sanitation_fee = sanitation_fee;
    }

    public BigDecimal getElse_fee() {
        return else_fee;
    }

    public void setElse_fee(BigDecimal else_fee) {
        this.else_fee = else_fee;
    }

    public String getCost_item_month() {
        return cost_item_month;
    }

    public void setCost_item_month(String cost_item_month) {
        this.cost_item_month = cost_item_month;
    }

    public BigDecimal getRent() {
        return rent;
    }

    public void setRent(BigDecimal rent) {
        this.rent = rent;
    }

    public List<MeterItem> getMetslist() {
        return metslist;
    }

    public void setMetslist(List<MeterItem> metslist) {
        this.metslist = metslist;
    }
}
