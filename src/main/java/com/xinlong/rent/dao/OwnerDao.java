package com.xinlong.rent.dao;

import com.xinlong.rent.daomain.Customer;
import com.xinlong.rent.daomain.Owner;
import com.xinlong.rent.mapper.OwnerMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class OwnerDao {

    @Autowired
    private OwnerMapper mapper;

    public int addOwner(Owner owner) {
        return mapper.addOwner(owner);
    }

    public Owner findByPhone(String ownerPhone) {
        return mapper.findByPhone(ownerPhone);
    }

    public Owner login(String ownerPhone, String ownerPassword) {
        return mapper.login(ownerPhone, ownerPassword);
    }


    public int addCustomer(Customer customer) {
        return mapper.addCustomer(customer);
    }

    public Customer findByTel(String customer_tel) {
        return mapper.findByTel(customer_tel);
    }

    public Customer loginCus(String customer_tel, String customer_passwrod) {
        return mapper.loginCus(customer_tel, customer_tel);
    }

}
