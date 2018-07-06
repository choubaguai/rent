package com.xinlong.rent.dao;


import com.xinlong.rent.daomain.CostItem;

import com.xinlong.rent.daomain.MeterItem;
import com.xinlong.rent.daomain.Tenement;
import com.xinlong.rent.mapper.CostItemMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@Repository
public class CostItemDao {

    @Autowired
    private CostItemMapper mapper;

    public int addCostItem(CostItem costItem) {
        return mapper.addCostItem(costItem);
    }

    public CostItem findCostItemById(String tenement_id) {
        return mapper.findCostItemById(tenement_id);
    }

    public int updateCostItem(String tenement_id, String fixed_water, String network_fee, String sanitation_fee, String else_fee,
                              Date cost_item_date, String remarks) {
        return mapper.updateCostItem(tenement_id, fixed_water, network_fee, sanitation_fee, else_fee, cost_item_date, remarks);
    }

    public int addMeterItem(MeterItem meterItem) {
        return mapper.addMeterItem(meterItem);
    }


    public List<MeterItem> findMeterItemById(String tenement_id,String tenant_id) {
        return mapper.findMeterItemById(tenement_id,tenant_id);
    }

    public MeterItem findLastMeterItem(String tenement_id, String meter_item_name,String tenant_id) {
        return mapper.findLastMeterItem(tenement_id, meter_item_name,tenant_id);
    }

    public List<MeterItem> findLastMonthMeterItem(String tenement_id,int is_bundle, int is_pay,String tenant_id) {
        return mapper.findLastMonthMeterItem(tenement_id, is_bundle, is_pay,tenant_id);
    }
    public List<MeterItem> findLastMonthMeterItems(String tenement_id,int is_bundle, int is_pay,String tenant_id, String meter_item_name,String bill_id) {
        return mapper.findLastMonthMeterItems(tenement_id, is_bundle, is_pay,tenant_id,meter_item_name,bill_id);
    }
    public List<MeterItem> findLastMonthMeterItemAll(String tenement_id,int is_bundle, int is_pay,String tenant_id, String meter_item_name) {
        return mapper.findLastMonthMeterItemAll(tenement_id, is_bundle, is_pay,tenant_id,meter_item_name);
    }


    public  List<MeterItem> getMonthMeterItemList(String tenement_id,String tenant_id,int is_bundle, int is_pay,String bill_id){
        return mapper.getMonthMeterItemList(tenement_id, tenant_id,is_bundle,is_pay,bill_id);
    }
    public  List<MeterItem> getMonthMeterItemListToatl(String tenement_id,String tenant_id,int is_bundle, int is_pay){
        return mapper.getMonthMeterItemListToatl(tenement_id, tenant_id,is_bundle,is_pay);
    }

    public BigDecimal getSumMeterItem(String tenement_id, String tenant_id, int is_bundle, int is_pay){
        return mapper.getSumMeterItem(tenement_id, tenant_id,is_bundle,is_pay);
    }
    public BigDecimal getSumMeterItems(String tenement_id, String tenant_id, int is_bundle, int is_pay,String meter_item_name){
        return mapper.getSumMeterItems(tenement_id, tenant_id,is_bundle,is_pay,meter_item_name);
    }

    public  List<MeterItem>  getSumMeterItemList(String tenement_id, String tenant_id, int is_bundle, int is_pay,String meter_item_name){
        return mapper.getSumMeterItemList(tenement_id, tenant_id,is_bundle,is_pay,meter_item_name);
    }


    public int updateBundle(String tenement_id,String meter_item_id,String bill_id) {
        return mapper.updateBundle(tenement_id, meter_item_id,bill_id);
    }
    public int updateBillPay(String tenement_id,String tenant_id,String bill_id) {
        return mapper.updateBillPay(tenement_id,tenant_id,bill_id);
    }
    public List<MeterItem> getMeterItemList(String tenement_id,String tenant_id,int is_bundle, int is_pay,String stDt,String endDt) {
        return mapper.getMeterItemList(tenement_id,tenant_id, is_bundle, is_pay,stDt,endDt);
    }

    public MeterItem getMeterItemById(String meter_item_id) {
        return mapper.getMeterItemById(meter_item_id);
    }

}

