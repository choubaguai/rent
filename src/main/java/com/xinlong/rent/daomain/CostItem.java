package com.xinlong.rent.daomain;

import com.fasterxml.jackson.annotation.JsonFormat;
import oracle.sql.DATE;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 费用实体
 */

public class CostItem implements Serializable {

    private String  cost_item_id;       //费用ID
    private String  tenement_id;         //房屋ID
    private BigDecimal  fixed_water; //固定水费
    private BigDecimal  network_fee;  //网费
    private BigDecimal  sanitation_fee ; //卫生费
    private BigDecimal else_fee ;  //其他费用
    private Date cost_item_date ;  //费用生成时间
    private String  remarks;  //备注

    public String getCost_item_id() {
        return cost_item_id;
    }

    public void setCost_item_id(String cost_item_id) {
        this.cost_item_id = cost_item_id;
    }

    public String getTenement_id() {
        return tenement_id;
    }

    public void setTenement_id(String tenement_id) {
        this.tenement_id = tenement_id;
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
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    public Date getCost_item_date() {
        return cost_item_date;
    }
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public void setCost_item_date(Date cost_item_date) {
        this.cost_item_date = cost_item_date;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
