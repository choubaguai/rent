package com.xinlong.rent.daomain;

import java.io.Serializable;
/**
 * 房屋配置
 *
 * @author NAN
 */
public class Asset implements Serializable {

    private static final long serialVersionUID = 1L;

    private String asset_id ;
    private String asset_name;
    private String asset_no ;
    private String remarks;

    public String getAsset_id() {
        return asset_id;
    }

    public void setAsset_id(String asset_id) {
        this.asset_id = asset_id;
    }

    public String getAsset_name() {
        return asset_name;
    }

    public void setAsset_name(String asset_name) {
        this.asset_name = asset_name;
    }

    public String getAsset_no() {
        return asset_no;
    }

    public void setAsset_no(String asset_no) {
        this.asset_no = asset_no;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
