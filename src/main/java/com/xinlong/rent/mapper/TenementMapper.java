package com.xinlong.rent.mapper;



import com.xinlong.rent.daomain.Asset;
import com.xinlong.rent.daomain.Relet;
import com.xinlong.rent.daomain.Tenement;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Repository
@Mapper
public interface TenementMapper {


    //添加房屋
    @Insert("insert into F_RENTAL_TENEMENT (TENEMENT_ID,HOUSE_ID,TENANT_ID,TOWER_NO,TENEMENT_CODE,TENEMENT_AREA,ASSET_NAME,HOUSE_TYPE," +
            "MONTHLY_RENT,IS_OR_NOT_RENT,REGISTER_DATE,REMARK,IS_DELETE,is_bundle,is_fixation) " +
            "values(#{tenement_id},#{house_id},#{tenant_id},#{tower_no},#{tenement_code},#{tenement_area}, #{asset_name}, #{house_type}," +
            " #{monthly_rent}, #{is_or_not_rent},#{register_date},#{remark},#{is_delete},#{is_bundle},#{is_fixation})")

    int addTenement(Tenement tenement);

    //根据编号和房产id查找房屋
    @Select("select * from F_RENTAL_TENEMENT where TENEMENT_CODE = #{tenement_code} and HOUSE_ID= #{house_id} and IS_DELETE=1")
    Tenement findByTenementCode(@Param("tenement_code") String tenement_code, @Param("house_id") String house_id);


    //根据编号和房产id查找房屋
    @Select("select * from F_RENTAL_TENEMENT where house_id= #{house_id} and IS_DELETE=1")
    Tenement findByTenementCodeId(@Param("house_id") String house_id);

    //根据房产id查找房产list
    @Select("select * from F_RENTAL_TENEMENT a where a.HOUSE_ID = #{house_id}  and a.IS_DELETE=1 order by a.register_date desc")
    List<Tenement> getTenementById(@Param("house_id") String house_id);

    //根据房屋id修改房屋出租情况
    @Update("update F_RENTAL_TENEMENT set is_or_not_rent=#{is_or_not_rent},TENANT_ID=#{tenant_id}  where tenement_id=#{tenement_id} and IS_DELETE=1")
    int updateTenement(@Param("is_or_not_rent")int is_or_not_rent, @Param("tenement_id")String tenement_id, @Param("tenant_id")String tenant_id);


    //根据配置
    @Select("select * from F_ASSET order by asset_no   ")
    List<Asset> getAsset();

    //物理删除
    @Update("update F_RENTAL_TENEMENT set IS_DELETE=2 where IS_OR_NOT_RENT=#{is_or_not_rent} and TENEMENT_ID=#{tenement_id}")
    int deleteTenement(@Param("is_or_not_rent") int is_or_not_rent,@Param("tenement_id") String tenement_id);

    //根据房产id,出租状态查找房屋list
    @Select("select * from F_RENTAL_TENEMENT where HOUSE_ID = #{house_id} and IS_OR_NOT_RENT = #{is_or_not_rent} and IS_DELETE=1 order by register_date desc")
    List<Tenement> getTenementByIsId(@Param("house_id")String house_id, @Param("is_or_not_rent")int is_or_not_rent);


    @Select(" select  sum(b.rent) rentToal,t.tenement_id,t.house_id, t.tenant_id,  t.tenement_code,t.tenement_area , " +
            " t.monthly_rent, t.is_or_not_rent,  t.register_date,t.remark, t.is_delete,t.tower_no, t.asset_name,t.house_type, t.is_bundle, " +
            " t.is_fixation from f_rental_tenement t, f_rental_bill b where t.tenement_id in b.tenement_id  " +
            " and t.is_delete = 1 and t.house_id=  #{house_id} and b.is_or_not_pay=1   and b.is_delete=1  group by t.tenement_id, " +
            " t.house_id,t.tenant_id,t.tenement_code,t.tenement_area ,t.monthly_rent,t.is_or_not_rent,t.register_date,t.remark,t.is_delete, " +
            "   t.tower_no,  t.asset_name, t.house_type, t.is_bundle,t.is_fixation  ")
    List<Tenement> getTenementIncomeId(@Param("house_id")String house_id);

    @Select("select * from F_RENTAL_TENEMENT where TENEMENT_ID = #{tenement_id} and IS_DELETE=1")
    Tenement getTenementByCodeId(@Param("tenement_id")String tenement_id);


    @Select("select a.tenement_id,a.house_id,a.tenement_code,b.tenant_name,b.tenant_id,b.end_date from f_rental_tenement a," +
            " f_rental_tenant b where a.tenant_id=b.tenant_id  and a.house_id= #{house_id} order by b.end_date desc")
    List<Relet>  getReletList(@Param("house_id")String house_id);

    //解除关系
    @Update("update F_RENTAL_TENEMENT set tenant_id='1' ,is_or_not_rent=2 where tenement_id=#{tenement_id}")
    int getRelieve(@Param("tenement_id") String tenement_id);


    @Update("update F_RENTAL_TENEMENT set TOWER_NO=#{tower_no} ,tenement_code=#{tenement_code} , tenement_area=#{tenement_area} ,asset_name=#{asset_name} , " +
            " house_type=#{house_type} , monthly_rent=#{monthly_rent} , register_date=#{register_date} ,is_fixation=#{is_fixation} ,is_bundle=#{is_bundle} where TENEMENT_ID=#{tenement_id}  and IS_DELETE=1")
    int getRlter(@Param("tenement_id") String tenement_id,@Param("tower_no") String tower_no,@Param("asset_name") String asset_name,
                 @Param("tenement_code") String tenement_code, @Param("tenement_area") String tenement_area,
                 @Param("house_type") String house_type,@Param("monthly_rent") BigDecimal monthly_rent,
                     @Param("register_date") Date register_date, @Param("is_bundle") int is_bundle, @Param("is_fixation") int is_fixation);

}


