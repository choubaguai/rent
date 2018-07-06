package com.xinlong.rent.daomain;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 账单详情（费用项）实体类
 *
 * @author fukai
 */
public class Detail implements Serializable {

    private static final long serialVersionUID = 1L;

    private String detailId;//费用详情ID，主键
    private String detail;//费用项（明细）
    private BigDecimal dosage;//用量
    private BigDecimal unitPrice;//单价
    private BigDecimal totalPrices;//金额
    private String remark;//备注

    public String getDetailId() {
        return detailId;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public BigDecimal getDosage() {
        return dosage;
    }

    public void setDosage(BigDecimal dosage) {
        this.dosage = dosage;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getTotalPrices() {
        return totalPrices;
    }

    public void setTotalPrices(BigDecimal totalPrices) {
        this.totalPrices = totalPrices;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
