package com.xinlong.rent.mapper;

import com.xinlong.rent.daomain.Tenant;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;

@Repository
@Mapper
public interface TenantMapper {

    //添加租客信息
    @Insert("insert into F_RENTAL_TENANT (TENANT_ID,TENEMENT_ID,TENANT_NAME,TENANT_CODE,TENANT_PASSWORD,ID_CARD,START_DATE," +
            "END_DATE,COLLECT_DATE,PRODUCE_CYCLE,RENT,CASH_PLEDGE,RENTAL_NUM,IS_DELETE,REMARK) " +
            " values(#{tenant_id},#{tenement_id},#{tenant_name},#{tenant_code},#{tenant_password}, #{id_card}," +
            " #{start_date},#{end_date},#{collect_date},#{produce_cycle},#{rent}" +
            ",#{cash_pledge},#{rental_num},#{is_delete},#{remark})")
    int addTenant(Tenant tenant);




    //根据房屋编号查找租客
    @Select("select * from F_RENTAL_TENANT where  TENANT_ID= #{tenant_id} and IS_DELETE=1")
    Tenant findByTenantById(@Param("tenant_id")String tenant_id);


    //根据房屋编号查找租客
    @Select("select * from F_RENTAL_TENANT where  tenement_id= #{tenement_id} and IS_DELETE=1")
    Tenant findByTenementById(@Param("tenement_id")String tenement_id);

    //修改租客信息
    @Update("update F_RENTAL_TENANT set TENANT_NAME=#{tenant_name},TENANT_CODE=#{tenant_code}," +
            "ID_CARD=#{id_card},START_DATE=#{start_date},END_DATE=#{end_date}," +
            "COLLECT_DATE=#{collect_date},PRODUCE_CYCLE=#{produce_cycle},RENT=#{rent}," +
            "CASH_PLEDGE=#{cash_pledge},RENTAL_NUM=#{rental_num},REMARK=#{remark}  where TENANT_ID=#{tenant_id}")
    int updateTenant(@Param("tenant_id") String tenant_id, @Param("tenant_name") String tenant_name, @Param("tenant_code") String tenant_code,
                     @Param("id_card") String id_card, @Param("start_date") Date start_date,
                     @Param("end_date") Date end_date, @Param("collect_date") String collect_date,
                     @Param("produce_cycle") String produce_cycle, @Param("rent") BigDecimal rent,
                     @Param("cash_pledge") BigDecimal cash_pledge, @Param("rental_num") int rental_num,
                     @Param("remark") String remark);


}
