package com.xinlong.rent.dao;


import com.xinlong.rent.daomain.Bill;
import com.xinlong.rent.mapper.BillMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BillDao {

    @Autowired
    private BillMapper mapper;

    public int addBill(Bill bill) {
        return mapper.addBill(bill);
    }


    public Bill findBillById(String bill_id) {
        return mapper.findBillById(bill_id);
    }

    public int updateBill(String bill_id) {
        return mapper.updateBill(bill_id);
    }
    public  List<Bill> queryBillList( String tenement_id,String  tenant_id,int  is_or_not_pay) {
        return mapper.queryBillList(tenement_id,tenant_id,is_or_not_pay);
    }
    public  List<Bill> queryBillLists( String tenement_id,String  tenant_id) {
        return mapper.queryBillLists(tenement_id,tenant_id);
    }
    public Bill findBillTenementId(String tenement_id,String month_dt,String  tenant_id) {
        return mapper.findBillTenementId(tenement_id,month_dt,tenant_id);
    }
    public  List<Bill> queryYearBillList( String tenement_id,int  is_or_not_pay) {
        return mapper.queryYearBillList(tenement_id,is_or_not_pay);
    }

    public  List<Bill> queryYearBillListAll( String tenement_id) {
        return mapper.queryYearBillListAll(tenement_id);
    }


    public List<Bill>  getBillIsNotpay(String tenement_id,String  tenant_id,int bill_type) {
        return mapper.getBillIsNotpay(tenement_id,tenant_id,bill_type);
    }
    public List<Bill>  getBillIsNotpays(String tenement_id,String  tenant_id) {
        return mapper.getBillIsNotpays(tenement_id,tenant_id);
    }

}
