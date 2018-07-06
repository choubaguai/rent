package com.xinlong.rent.service;


import com.xinlong.rent.dao.TenementDao;
import com.xinlong.rent.daomain.Asset;
import com.xinlong.rent.daomain.Relet;
import com.xinlong.rent.daomain.Tenement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class TenementService {

    @Autowired
    private TenementDao tenementDao;

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public boolean addTenement(Tenement tenement) {
        int flag = tenementDao.addTenement(tenement);
        if (flag > 0) {
            return true;
        } else {
            return false;
        }
    }
    public Tenement findByTenementCode(String tenement_code,String house_id) {
        return tenementDao.findByTenementCode(tenement_code,house_id);
    }


    public Tenement findByTenementCodeId(String house_id) {
        return tenementDao.findByTenementCodeId(house_id);
    }

    public List<Tenement> getTenementById(String house_id) {
        return tenementDao.getTenementById(house_id);
    }

    public boolean updateTenement(int is_or_not_rent,String tenement_id,String tenant_id) {
        int flag = tenementDao.updateTenement(is_or_not_rent,tenement_id,tenant_id);
        if (flag > 0) {
            return true;
        } else {
            return false;
        }
    }
    public List<Asset> getAsset() {
        return tenementDao.getAsset();
    }

    public boolean deleteTenement(int is_or_not_rent,String tenement_id) {
        int flag =tenementDao.deleteTenement(is_or_not_rent,tenement_id);
        if (flag > 0) {
            return true;
        } else {
            return false;
        }
    }

    public List<Tenement> getTenementByIsId(String house_id,int is_or_not_rent) {
        return tenementDao.getTenementByIsId(house_id,is_or_not_rent);
    }
    public List<Tenement> getTenementIncomeId(String house_id) {
        return tenementDao.getTenementIncomeId(house_id);
    }
    public Tenement getTenementByCodeId(String tenement_id) {
        return tenementDao.getTenementByCodeId(tenement_id);
    }

    public   List<Relet> getReletList(String house_id) {
        return tenementDao.getReletList(house_id);
    }


    public boolean getRelieve(String tenement_id) {
        int flag =tenementDao.getRelieve(tenement_id);
        if (flag > 0) {
            return true;
        } else {
            return false;
        }
    }
    public boolean getRlter(String tenement_id, String tower_no, String asset_name, String tenement_code, String tenement_area,
                            String house_type, BigDecimal monthly_rent, Date register_date,int is_bundle,int is_fixation) {
        int flag =tenementDao.getRlter(tenement_id , tower_no,asset_name,tenement_code,tenement_area,house_type,monthly_rent,register_date,is_bundle,is_fixation);
        if (flag > 0) {
            return true;
        } else {
            return false;
        }
    }


}
