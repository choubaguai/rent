package com.xinlong.rent.service;

import com.xinlong.rent.dao.BillDao;
import com.xinlong.rent.daomain.Bill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BillService {

    @Autowired
    private BillDao billDao;

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public boolean addBill(Bill bill) {
        int flag = billDao.addBill(bill);
        if (flag > 0) {
            return true;
        } else {
            return false;
        }
    }

    public Bill findBillById(String bill_id) {
        return billDao.findBillById(bill_id);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public boolean updateBill(String bill_id) {
        int flag = billDao.updateBill(bill_id);
        if (flag > 0) {
            return true;
        } else {
            return false;
        }
    }

    public List<Bill> queryBillList(String tenement_id,String  tenant_id,int  is_or_not_pay) {
        return billDao.queryBillList(tenement_id,tenant_id,is_or_not_pay);
    }
    public  List<Bill> queryBillLists( String tenement_id,String  tenant_id) {
        return billDao.queryBillLists(tenement_id,tenant_id);
    }
    public Bill findBillTenementId(String tenement_id,String month_dt,String  tenant_id) {
        return billDao.findBillTenementId(tenement_id,month_dt,tenant_id);
    }

    public  List<Bill> queryYearBillList(String tenement_id, int is_or_not_pay) {
        return billDao.queryYearBillList(tenement_id,is_or_not_pay);
    }

    public  List<Bill> queryYearBillListAll( String tenement_id) {
        return billDao.queryYearBillListAll(tenement_id);
    }

    public List<Bill>  getBillIsNotpay(String tenement_id,String  tenant_id,int bill_type) {
        return billDao.getBillIsNotpay(tenement_id,tenant_id,bill_type);
    }
    public List<Bill>  getBillIsNotpays(String tenement_id,String  tenant_id) {
        return billDao.getBillIsNotpays(tenement_id,tenant_id);
    }

}
