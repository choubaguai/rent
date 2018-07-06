package com.xinlong.rent.mapper;

import com.xinlong.rent.daomain.House;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface HouseMapper {
    //添加房产
    @Insert("insert into F_RENTAL_HOUSE (HOUSE_ID,OWNER_ID,HOUSE_NAME,HOUSE_CODE,HOUSE_ADDRESS,HOUSE_TYPE,REGISTER_DATE,IS_DELETE) " +
            "values(#{house_Id},#{owner_Id},#{house_Name},#{house_Code}, #{house_Address}, #{house_Type},#{register_Date},#{is_Delete})")
    int addHouse(House house);

    //根据编号查找房产
    @Select("select * from F_RENTAL_HOUSE where HOUSE_CODE = #{house_Code} and OWNER_ID = #{owner_Id} and IS_DELETE=1")
    House findByCode(@Param("house_Code") String house_Code, @Param("owner_Id")String owner_Id );

    //根据id查找房产
    @Select("select * from F_RENTAL_HOUSE where HOUSE_ID = #{house_Id} and IS_DELETE=1")
    House findByHouseId(@Param("house_Id") String house_Id);

    //根据客户id查找房产
    @Select("select * from F_RENTAL_HOUSE where OWNER_ID = #{owner_Id}  and IS_DELETE=1")
    List<House> getHouseById(@Param("owner_Id") String owner_Id);


    //物理删除
    @Update("update F_RENTAL_HOUSE set IS_DELETE=2 where HOUSE_ID=#{house_Id}")
    int deleteHouse(@Param("house_Id") String house_Id);



    //闲置房屋0
    @Select("select count(1) total , b.house_id, b.owner_id,b.house_name, b.house_code,b.house_address, b.house_type,b.register_date, b.is_or_not_logout,b.remark, b.is_delete " +
            "  from f_rental_tenement t, f_rental_house b   where t.house_id in b.house_id and t.is_or_not_rent = 2  and t.is_delete = 1 and b.owner_id=#{owner_Id} " +
            " group by b.house_id, b.owner_id, b.house_name, b.house_code, b.house_address, b.house_type,  b.register_date,b.is_or_not_logout,b.remark, b.is_delete "  )
    List<House> getLeaveHouseList(@Param("owner_Id") String owner_Id);

    //账单管理/水电表1
    @Select("  select * from f_rental_house a where a.house_id in (select b.house_id    from F_RENTAL_TENEMENT b where b.is_or_not_rent = 1 and " +
            "  b.is_delete = 1 and  b.tenement_id in (select c.tenement_id from f_rental_tenant c where c.is_delete=1 ))  and a.owner_id = #{owner_Id} and a.is_delete = 1 " )
    List<House> getBillFormHouseList(@Param("owner_Id") String owner_Id);

    //账单管理/水电表2
    @Select(" select * from f_rental_house a where a.house_id in (select b.house_id  from F_RENTAL_TENEMENT b where b.is_or_not_rent = 1 and " +
            " b.is_delete = 1 and  b.tenement_id in (select f.tenement_id from f_rental_bill f where f.is_or_not_pay=1 and f.generated_date>=to_date(#{stDt},'yyyy/mm/dd hh24:mi:ss') " +
            " and f.generated_date<=to_date(#{endDt}, 'yyyy/mm/dd hh24:mi:ss') and f.is_delete=1 ))  and a.owner_id = #{owner_Id} and a.is_delete = 1  " )
    List<House> getFerialHouseList(@Param("owner_Id") String owner_Id,@Param("stDt") String stDt,@Param("endDt") String endDt);
}
