package com.xinlong.rent.dao;


import com.xinlong.rent.daomain.Asset;
import com.xinlong.rent.daomain.Relet;
import com.xinlong.rent.daomain.Tenement;

import com.xinlong.rent.mapper.TenementMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Repository
public class TenementDao {
    @Autowired
    private TenementMapper mapper;

    public int addTenement(Tenement tenement) {
        return mapper.addTenement(tenement);
    }

    public Tenement findByTenementCode(String tenement_code,String house_id) {
        return mapper.findByTenementCode(tenement_code,house_id);
    }


    public Tenement findByTenementCodeId(String house_id) {
        return mapper.findByTenementCodeId(house_id);
    }
    public List<Tenement> getTenementById(String house_id) {
        return mapper.getTenementById(house_id);
    }

    public int updateTenement(int is_or_not_rent,String tenement_id,String tenant_id) {
        return mapper.updateTenement(is_or_not_rent,tenement_id,tenant_id);}

    public List<Asset> getAsset() {  return mapper.getAsset(); }


    public int deleteTenement(int is_or_not_rent,String tenement_id) {
        return mapper.deleteTenement(is_or_not_rent,tenement_id);}


    public List<Tenement> getTenementByIsId(String house_id,int is_or_not_rent) {
        return mapper.getTenementByIsId(house_id,is_or_not_rent);
    }

    public List<Tenement> getTenementIncomeId(String house_id) {
        return mapper.getTenementIncomeId(house_id);
    }

    public Tenement getTenementByCodeId(String tenement_id) {
        return mapper.getTenementByCodeId(tenement_id);
    }


    public   List<Relet> getReletList(String house_id) {
        return mapper.getReletList(house_id);
    }


    public int getRelieve(String tenement_id) {
        return mapper.getRelieve(tenement_id);
    }

    public int getRlter(String tenement_id, String tower_no, String asset_name, String tenement_code, String tenement_area, String house_type, BigDecimal monthly_rent, Date register_date,int is_bundle,int is_fixation) {
        return mapper.getRlter(tenement_id ,tower_no ,asset_name,tenement_code,tenement_area,house_type,monthly_rent,register_date, is_bundle,is_fixation);
    }

}
