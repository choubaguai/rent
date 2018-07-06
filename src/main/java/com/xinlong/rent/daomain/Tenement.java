package com.xinlong.rent.daomain;



import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *  房屋实体类
 */
public class Tenement implements Serializable {
    private static final long serialVersionUID = 1L;

    private String tenement_id;//房屋ID，主键
    private String house_id;//房产ID，外键，对应房产主键
    private String tenant_id;//租客ID，外键，对应租客主键
    private String tower_no;//楼号
    private String tenement_code;//房屋编号
    private String tenement_area;//房屋面积
    private String asset_name;//配置名称
    private String house_type;//户型
    private BigDecimal monthly_rent;//月租金
    private Integer is_or_not_rent;//是否出租，1：是，2：否
    private Date register_date;//注册时间
    private String remark;//备注
    private Integer is_delete;//是否删除，1：未删除，2：删除（删除用户为假删除），默认1
    private Integer is_bundle;//0捆绑1未捆绑
    private Integer is_fixation;//0固定1未固定
    private BigDecimal rentToal;//收支流水总金额
    private BigDecimal rent;//交租金额

    public BigDecimal getRent() {
        return rent;
    }

    public void setRent(BigDecimal rent) {
        this.rent = rent;
    }

    public BigDecimal getRentToal() {
        return rentToal;
    }

    public void setRentToal(BigDecimal rentToal) {
        this.rentToal = rentToal;
    }

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

    public String getTenant_id() {
        return tenant_id;
    }

    public void setTenant_id(String tenant_id) {
        this.tenant_id = tenant_id;
    }

    public String getTenement_code() {
        return tenement_code;
    }

    public void setTenement_code(String tenement_code) {
        this.tenement_code = tenement_code;
    }

    public String getTenement_area() {
        return tenement_area;
    }

    public void setTenement_area(String tenement_area) {
        this.tenement_area = tenement_area;
    }

    public BigDecimal getMonthly_rent() {
        return monthly_rent;
    }

    public void setMonthly_rent(BigDecimal monthly_rent) {
        this.monthly_rent = monthly_rent;
    }

    public Integer getIs_or_not_rent() {
        return is_or_not_rent;
    }

    public void setIs_or_not_rent(Integer is_or_not_rent) {
        this.is_or_not_rent = is_or_not_rent;
    }
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    public Date getRegister_date() {
        return register_date;
    }
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public void setRegister_date(Date register_date) {
        this.register_date = register_date;
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

    public String getTower_no() {
        return tower_no;
    }

    public void setTower_no(String tower_no) {
        this.tower_no = tower_no;
    }

    public String getAsset_name() {
        return asset_name;
    }

    public void setAsset_name(String asset_name) {
        this.asset_name = asset_name;
    }

    public String getHouse_type() {
        return house_type;
    }

    public void setHouse_type(String house_type) {
        this.house_type = house_type;
    }

    public Integer getIs_bundle() {
        return is_bundle;
    }

    public void setIs_bundle(Integer is_bundle) {
        this.is_bundle = is_bundle;
    }

    public Integer getIs_fixation() {
        return is_fixation;
    }

    public void setIs_fixation(Integer is_fixation) {
        this.is_fixation = is_fixation;
    }
}
