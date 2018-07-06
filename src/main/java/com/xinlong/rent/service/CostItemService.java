package com.xinlong.rent.service;


import com.xinlong.rent.dao.CostItemDao;

import com.xinlong.rent.daomain.CostItem;
import com.xinlong.rent.daomain.MeterItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class CostItemService {


    @Autowired
    private CostItemDao costItemDao;

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public boolean addCostItem(CostItem costItem) {
        int flag = costItemDao.addCostItem(costItem);
        if (flag > 0) {
            return true;
        } else {
            return false;
        }
    }

    public CostItem findCostItemById(String tenement_id) {
        return costItemDao.findCostItemById(tenement_id);
    }

    public boolean updateCostItem(String tenement_id, String fixed_water, String network_fee, String sanitation_fee,String else_fee, Date cost_item_date, String remarks) {
        int flag = costItemDao.updateCostItem(tenement_id,fixed_water,network_fee,sanitation_fee,else_fee,cost_item_date,remarks);
        if (flag > 0) {
            return true;
        } else {
            return false;
        }
    }
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public boolean  addMeterItem(MeterItem meterItem) {
        int flag = costItemDao.addMeterItem(meterItem);
        if (flag > 0) {
            return true;
        } else {
            return false;
        }
    }


    public List<MeterItem> findMeterItemById(String tenement_id,String tenant_id) {
        return costItemDao.findMeterItemById(tenement_id,tenant_id);
    }


    public MeterItem findLastMeterItem(String tenement_id,String meter_item_name,String tenant_id) {
        return costItemDao.findLastMeterItem(tenement_id,meter_item_name,tenant_id);
    }

    public List<MeterItem> findLastMonthMeterItem(String tenement_id,int is_bundle, int is_pay,String tenant_id) {
        return costItemDao.findLastMonthMeterItem(tenement_id,is_bundle,is_pay,tenant_id);
    }
    public List<MeterItem> findLastMonthMeterItems(String tenement_id,int is_bundle, int is_pay,String tenant_id, String meter_item_name,String bill_id) {
        return costItemDao.findLastMonthMeterItems(tenement_id, is_bundle, is_pay,tenant_id,meter_item_name,bill_id);
    }

    public List<MeterItem> findLastMonthMeterItemAll(String tenement_id,int is_bundle, int is_pay,String tenant_id, String meter_item_name) {
        return costItemDao.findLastMonthMeterItemAll(tenement_id, is_bundle, is_pay,tenant_id,meter_item_name);
    }
    public  List<MeterItem> getMonthMeterItemList(String tenement_id,String tenant_id,int is_bundle, int is_pay,String bill_id){
        return costItemDao.getMonthMeterItemList(tenement_id, tenant_id,is_bundle,is_pay,bill_id);
    }
    public  List<MeterItem> getMonthMeterItemListToatl(String tenement_id,String tenant_id,int is_bundle, int is_pay){
        return costItemDao.getMonthMeterItemListToatl(tenement_id, tenant_id,is_bundle,is_pay);
    }

    public BigDecimal getSumMeterItem(String tenement_id, String tenant_id, int is_bundle, int is_pay){
        return costItemDao.getSumMeterItem(tenement_id, tenant_id,is_bundle,is_pay);
    }
    public BigDecimal getSumMeterItems(String tenement_id, String tenant_id, int is_bundle, int is_pay,String meter_item_name){
        return costItemDao.getSumMeterItems(tenement_id, tenant_id,is_bundle,is_pay,meter_item_name);
    }
    public  List<MeterItem>  getSumMeterItemList(String tenement_id, String tenant_id, int is_bundle, int is_pay,String meter_item_name){
        return costItemDao.getSumMeterItemList(tenement_id, tenant_id,is_bundle,is_pay,meter_item_name);
    }
    public boolean updateBundle(String tenement_id,String meter_item_id,String bill_id) {
        int flag = costItemDao.updateBundle(tenement_id,meter_item_id,bill_id);
        if (flag > 0) {
            return true;
        } else {
            return false;
        }
    }
    public boolean updateBillPay(String tenement_id,String tenant_id,String bill_id) {
        int flag = costItemDao.updateBillPay(tenement_id,tenant_id,bill_id);
        if (flag > 0) {
            return true;
        } else {
            return false;
        }
    }
    public List<MeterItem> getMeterItemList(String tenement_id,String tenant_id,int is_bundle, int is_pay,String stDt,String endDt) {
        return costItemDao.getMeterItemList(tenement_id,tenant_id, is_bundle, is_pay,stDt,endDt);
    }

    public MeterItem getMeterItemById(String meter_item_id) {
        return costItemDao.getMeterItemById(meter_item_id);
    }


}

