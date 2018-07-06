package com.xinlong.rent.service;

import com.xinlong.rent.dao.HouseDao;
import com.xinlong.rent.dao.OwnerDao;
import com.xinlong.rent.daomain.House;
import com.xinlong.rent.daomain.Owner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class HouseService {

    @Autowired
    private HouseDao houseDao;

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public boolean addHouse(House house) {
        int flag = houseDao.addHouse(house);
        if (flag > 0) {
            return true;
        } else {
            return false;
        }
    }
    public House findByCode(String house_Code,String owner_Id) {
        return houseDao.findByCode(house_Code,owner_Id);
    }

    public House findByHouseId(String house_Id) {
        return houseDao.findByHouseId(house_Id);
    }

    public List<House> getHouseById(String house_Code) {
        return houseDao.getHouseById(house_Code);
    }
    public boolean deleteHouse(String house_Id) {
        int flag = houseDao.deleteHouse(house_Id);
        if (flag > 0) {
            return true;
        } else {
            return false;
        }
    }
    public List<House> getLeaveHouseList(String owner_Id) {
        return houseDao.getLeaveHouseList(owner_Id);
    }
    public List<House> getBillFormHouseList(String owner_Id) {
        return houseDao.getBillFormHouseList(owner_Id);
    }
    public List<House> getFerialHouseList(String owner_Id, String stDt, String enDt) {
        return houseDao.getFerialHouseList(owner_Id,stDt,enDt);
    }


}
