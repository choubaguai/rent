package com.xinlong.rent.service;

import com.xinlong.rent.dao.OwnerDao;
import com.xinlong.rent.daomain.Customer;
import com.xinlong.rent.daomain.Owner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OwnerService {

    @Autowired
    private OwnerDao ownerDao;

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public boolean addOwner(Owner owner) {
        int flag = ownerDao.addOwner(owner);
        if (flag > 0) {
            return true;
        } else {
            return false;
        }

    }

    public Owner findByPhone(String ownerPhone) {
        return ownerDao.findByPhone(ownerPhone);
    }


    public Owner login(String ownerPhone, String ownerPassword) {
        return ownerDao.login(ownerPhone, ownerPassword);
    }


    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public boolean addCustomer(Customer customer) {
        int flag = ownerDao.addCustomer(customer);
        if (flag > 0) {
            return true;
        } else {
            return false;
        }

    }

    public Customer findByTel(String customer_tel) {
        return ownerDao.findByTel(customer_tel);
    }

    public Customer loginCus(String customer_tel, String customer_passwrod) {
        return ownerDao.loginCus(customer_tel, customer_tel);
    }
}
