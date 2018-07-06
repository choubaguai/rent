package com.xinlong.rent.dao;


import com.xinlong.rent.daomain.House;
import com.xinlong.rent.mapper.HouseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HouseDao {

    @Autowired
    private HouseMapper mapper;

    public int addHouse(House house) {
        return mapper.addHouse(house);
    }

    public House findByCode(String house_Code,String owner_Id) {
        return mapper.findByCode(house_Code,owner_Id);
    }
    public House findByHouseId(String house_Id) {
        return mapper.findByHouseId(house_Id);
    }


    public List<House> getHouseById(String house_Code) {
        return mapper.getHouseById(house_Code);
    }

    public int deleteHouse(String house_Id) { return mapper.deleteHouse(house_Id);}

    public List<House> getLeaveHouseList(String owner_Id) {
        return mapper.getLeaveHouseList(owner_Id);
    }

    public List<House> getBillFormHouseList(String owner_Id) {
        return mapper.getBillFormHouseList(owner_Id);
    }
    public List<House> getFerialHouseList(String owner_Id, String stDt, String enDt) {
        return mapper.getFerialHouseList(owner_Id,stDt,enDt);
    }
}