package com.xinlong.rent.dao;

import com.xinlong.rent.daomain.Tenant;
import com.xinlong.rent.daomain.Tenement;
import com.xinlong.rent.mapper.TenantMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Repository
public class TenantDao {
    @Autowired
    private TenantMapper mapper;

    public int addTenant(Tenant tenant) {
        return mapper.addTenant(tenant);
    }


    public Tenant findByTenantById(String tenant_id) {
        return mapper.findByTenantById(tenant_id);
    }


    public Tenant findByTenementById(String tenement_id) {
        return mapper.findByTenementById(tenement_id);
    }

    public int updateTenant(String tenant_id, String tenant_name, String tenant_code, String id_card, Date start_date, Date end_date, String collect_date, String produce_cycle, BigDecimal rent, BigDecimal cash_pledge, int rental_num,String remark) {
        return mapper.updateTenant(tenant_id,tenant_name,tenant_code,id_card,start_date,end_date,collect_date,produce_cycle,rent,cash_pledge,rental_num,remark);
    }


}
