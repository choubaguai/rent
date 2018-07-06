package com.xinlong.rent.daomain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 房产实体类
 *
 * @author fukai
 */
public class House implements Serializable {

    private static final long serialVersionUID = 1L;

    private String house_Id;//房产ID，主键
    private String owner_Id;//客户ID，外键，对应客户主键
    private String house_Name;//房产名称
    private String house_Code;//房产编号
    private String house_Address;//房产地址
    private Integer house_Type;//房产类型，1：商品住宅，2：城中村住宅，默认1
    private Date register_Date;//登记日期
    private Integer is_Or_Not_Logout;//是否注销，1：是，2：否，默认1
    private Integer is_Delete;//是否删除，1：未删除，2：删除（删除用户为假删除），默认1
    private String remark;//备注
    private String total;//总条数

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getHouse_Id() {
        return house_Id;
    }

    public void setHouse_Id(String house_Id) {
        this.house_Id = house_Id;
    }

    public String getOwner_Id() {
        return owner_Id;
    }

    public void setOwner_Id(String owner_Id) {
        this.owner_Id = owner_Id;
    }

    public String getHouse_Name() {
        return house_Name;
    }

    public void setHouse_Name(String house_Name) {
        this.house_Name = house_Name;
    }

    public String getHouse_Code() {
        return house_Code;
    }

    public void setHouse_Code(String house_Code) {
        this.house_Code = house_Code;
    }

    public String getHouse_Address() {
        return house_Address;
    }

    public void setHouse_Address(String house_Address) {
        this.house_Address = house_Address;
    }

    public Integer getHouse_Type() {
        return house_Type;
    }

    public void setHouse_Type(Integer house_Type) {
        this.house_Type = house_Type;
    }
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    public Date getRegister_Date() {
        return register_Date;
    }
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public void setRegister_Date(Date register_Date) {
        this.register_Date = register_Date;
    }

    public Integer getIs_Or_Not_Logout() {
        return is_Or_Not_Logout;
    }

    public void setIs_Or_Not_Logout(Integer is_Or_Not_Logout) {
        this.is_Or_Not_Logout = is_Or_Not_Logout;
    }

    public Integer getIs_Delete() {
        return is_Delete;
    }

    public void setIs_Delete(Integer is_Delete) {
        this.is_Delete = is_Delete;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
