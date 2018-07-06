package com.xinlong.rent.service;

import com.xinlong.rent.dao.TenantDao;

import com.xinlong.rent.daomain.Tenant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class TenantService {

   @Autowired
    private TenantDao tenantDao;

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public boolean addTenant(Tenant tenant) {
        int flag = tenantDao.addTenant(tenant);
        if (flag > 0) {
            return true;
        } else {
            return false;
        }
    }


    public Tenant findByTenantById(String tenant_id) {
        return tenantDao.findByTenantById(tenant_id);
    }
    public Tenant findByTenementById(String tenement_id) {
        return tenantDao.findByTenementById(tenement_id);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public boolean updateTenant(String tenant_id, String tenant_name, String tenant_code, String id_card, Date start_date, Date end_date, String collect_date, String produce_cycle, BigDecimal rent, BigDecimal cash_pledge, int rental_num,String remark) {
        int flag = tenantDao.updateTenant(tenant_id,tenant_name,tenant_code,id_card,start_date,end_date,collect_date,produce_cycle,rent,cash_pledge,rental_num,remark);
        if (flag > 0) {
            return true;
        } else {
            return false;
        }
    }


}
