package com.xinlong.rent.daomain;

import java.io.Serializable;
import java.util.Date;

/**
 * 客户实体类
 *
 * @author fukai
 */
public class Owner implements Serializable {

    private static final long serialVersionUID = 1L;

    private String owner_Id;//客户ID，主键
    private String owner_Name;//客户姓名
    private String nickname;//客户昵称
    private String owner_Phone;//客户电话
    private String owner_Password;//客户密码
    private String owner_IDCard;//客户身份证号
    private Integer is_Or_Not_Logout;//是否注销，1：是，2：否
    private Integer is_Delete;//是否删除，1：未删除，2：删除（删除用户为假删除）
    private Date owner_dt;
    private String remark;//备注


    public Date getOwner_dt() {
        return owner_dt;
    }

    public void setOwner_dt(Date owner_dt) {
        this.owner_dt = owner_dt;
    }

    public String getOwner_Id() {
        return owner_Id;
    }

    public void setOwner_Id(String owner_Id) {
        this.owner_Id = owner_Id;
    }

    public String getOwner_Name() {
        return owner_Name;
    }

    public void setOwner_Name(String owner_Name) {
        this.owner_Name = owner_Name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getOwner_Phone() {
        return owner_Phone;
    }

    public void setOwner_Phone(String owner_Phone) {
        this.owner_Phone = owner_Phone;
    }

    public String getOwner_Password() {
        return owner_Password;
    }

    public void setOwner_Password(String owner_Password) {
        this.owner_Password = owner_Password;
    }

    public String getOwner_IDCard() {
        return owner_IDCard;
    }

    public void setOwner_IDCard(String owner_IDCard) {
        this.owner_IDCard = owner_IDCard;
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


    @Override
    public String toString() {
        return "Owner{" +
                "owner_Name='" + owner_Name + '\'' +
                ", nickname='" + nickname + '\'' +
                ", owner_Phone='" + owner_Phone + '\'' +
                ", owner_Password='" + owner_Password + '\'' +
                ", owner_IDCard='" + owner_IDCard + '\'' +
                ", is_Or_Not_Logout=" + is_Or_Not_Logout +
                ", is_Delete=" + is_Delete +
                ", remark='" + remark + '\'' +
                '}';
    }
}
