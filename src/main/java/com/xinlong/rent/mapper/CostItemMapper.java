package com.xinlong.rent.mapper;

import com.xinlong.rent.daomain.CostItem;

import com.xinlong.rent.daomain.MeterItem;
import com.xinlong.rent.daomain.Tenement;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Repository
@Mapper
public interface CostItemMapper {
    //添加费用项
    @Insert("insert into F_COST_ITEM (COST_ITEM_ID,TENEMENT_ID," +
            "FIXED_WATER,NETWORK_FEE,SANITATION_FEE,ELSE_FEE,COST_ITEM_DATE,REMARKS) " +
            "values(#{cost_item_id},#{tenement_id}," +
            "#{fixed_water},#{network_fee},#{sanitation_fee},#{else_fee},#{cost_item_date},#{remarks})")
    int addCostItem(CostItem costItem);

    //根据房屋编号查找费用项
    @Select("select * from F_COST_ITEM where  TENEMENT_ID= #{tenement_id}")
    CostItem findCostItemById(@Param("tenement_id") String tenement_id);


    //根据房屋id修改房费用项
    @Update("update F_COST_ITEM set fixed_water=#{fixed_water},  network_fee=#{network_fee}, sanitation_fee=#{sanitation_fee},  else_fee=#{else_fee}, cost_item_date=#{cost_item_date}  " +
            ",  remarks=#{remarks}   where tenement_id=#{tenement_id} ")
    int updateCostItem(@Param("tenement_id")String tenement_id,@Param("fixed_water")String  fixed_water,
                       @Param("network_fee")String  network_fee, @Param("sanitation_fee")String sanitation_fee,
                       @Param("else_fee")String  else_fee, @Param("cost_item_date")Date cost_item_date,
                       @Param("remarks")String  remarks);


    //添加抄表项
    @Insert("insert into F_METER_ITEM (METER_ITEM_ID,TENEMENT_ID,TENANT_ID," +
            "METER_ITEM_NAME,START_CODE,END_CODE,CONSUMPTION,UNIT_PRICE,AMOUNT,METER_ITEM_DT,METER_ITEM_MONTH,REMARKS,is_bundle,is_pay) " +
            "values(#{meter_item_id},#{tenement_id},#{tenant_id},#{meter_item_name},#{start_code},#{end_code},#{consumption},#{unit_price}," +
            "#{amount},#{meter_item_dt},#{meter_item_month},#{remarks},#{is_bundle},#{is_pay})")
    int addMeterItem(MeterItem meterItem);


    //根据房屋编号查找费用项
    @Select("select * from F_METER_ITEM where  TENEMENT_ID= #{tenement_id} and  TENANT_ID = #{tenant_id} ORDER BY METER_ITEM_MONTH DESC")
    List<MeterItem> findMeterItemById(@Param("tenement_id") String tenement_id,@Param("tenant_id") String tenant_id);


    //查抄最后一次抄表
    @Select("select *  from F_METER_ITEM f where  f.meter_item_name like '%'||#{meter_item_name}||'%' and f.tenement_id= #{tenement_id}  and f.TENANT_ID = #{tenant_id}  and f.meter_item_dt=" +
            "(select max(o.meter_item_dt) from F_METER_ITEM o " +
            "where o.meter_item_name like '%'||#{meter_item_name}||'%' and o.tenement_id= #{tenement_id} and o.TENANT_ID = #{tenant_id} )")
    MeterItem findLastMeterItem(@Param("tenement_id") String tenement_id,@Param("meter_item_name") String meter_item_name,@Param("tenant_id") String tenant_id);

    //查找本月最后一些抄表项
  /*  @Select("  select * from (select meter_item_id, tenement_id, tenant_id,meter_item_name, start_code,end_code, consumption, unit_price, amount , " +
            " meter_item_dt, meter_item_month,REMARKS, row_number() over(partition by meter_item_name order by meter_item_dt desc) rn " +
            " from F_METER_ITEM  where meter_item_month = #{meter_item_month}  and tenement_id = #{tenement_id} and TENANT_ID = #{tenant_id} )  where rn = 1   ")*/
    @Select("  select * from F_METER_ITEM f  where   f.tenement_id =  #{tenement_id} " +
            " and f.TENANT_ID = #{tenant_id}  and f.is_bundle=#{is_bundle} and f.is_pay= #{is_pay} ")

    List<MeterItem> findLastMonthMeterItem(@Param("tenement_id") String tenement_id,@Param("is_bundle") int is_bundle,
                                           @Param("is_pay") int is_pay,@Param("tenant_id") String tenant_id
                                           );
    @Select("  select * from F_METER_ITEM f  where   f.tenement_id =  #{tenement_id} " +
            " and f.TENANT_ID = #{tenant_id}  and f.is_bundle=#{is_bundle} and f.is_pay= #{is_pay} and f.meter_item_name like '%'||#{meter_item_name}||'%' " +
            "  and f.bill_id = #{bill_id}")

    List<MeterItem> findLastMonthMeterItems(@Param("tenement_id") String tenement_id,@Param("is_bundle") int is_bundle,
                                           @Param("is_pay") int is_pay,@Param("tenant_id") String tenant_id,@Param("meter_item_name") String meter_item_name,
                                            @Param("bill_id") String bill_id);

    @Select("  select * from F_METER_ITEM f  where   f.tenement_id =  #{tenement_id} " +
            " and f.TENANT_ID = #{tenant_id}  and f.is_bundle=#{is_bundle} and f.is_pay= #{is_pay} and f.meter_item_name like '%'||#{meter_item_name}||'%' ")

    List<MeterItem> findLastMonthMeterItemAll(@Param("tenement_id") String tenement_id,@Param("is_bundle") int is_bundle,
                                            @Param("is_pay") int is_pay,@Param("tenant_id") String tenant_id,@Param("meter_item_name") String meter_item_name);


    @Select("select * from f_meter_item f where f.tenement_id = #{tenement_id} and f.tenant_id = #{tenant_id} and f.is_bundle = #{is_bundle} and f.is_pay=#{is_pay} and  f.bill_id=#{bill_id}")
    List<MeterItem> getMonthMeterItemList(@Param("tenement_id") String tenement_id,@Param("tenant_id") String tenant_id,@Param("is_bundle") int is_bundle,@Param("is_pay") int is_pay,@Param("bill_id") String bill_id);


    @Select("select * from f_meter_item f where f.tenement_id = #{tenement_id} and f.tenant_id = #{tenant_id} and f.is_bundle = #{is_bundle} and f.is_pay=#{is_pay} ")
    List<MeterItem> getMonthMeterItemListToatl(@Param("tenement_id") String tenement_id,@Param("tenant_id") String tenant_id,@Param("is_bundle") int is_bundle,@Param("is_pay") int is_pay);

    @Select("select sum(f.amount) from f_meter_item f where f.tenement_id = #{tenement_id} and f.tenant_id = #{tenant_id} and f.is_bundle = #{is_bundle} and f.is_pay=#{is_pay}")
    BigDecimal getSumMeterItem(@Param("tenement_id") String tenement_id,@Param("tenant_id") String tenant_id,@Param("is_bundle") int is_bundle,@Param("is_pay") int is_pay);




    @Select("select sum(f.amount) from f_meter_item f where f.tenement_id = #{tenement_id} and f.tenant_id = #{tenant_id} and f.is_bundle = #{is_bundle} and f.is_pay=#{is_pay}" +
            " and f.meter_item_name like '%'||#{meter_item_name}||'%' ")
    BigDecimal getSumMeterItems(@Param("tenement_id") String tenement_id,@Param("tenant_id") String tenant_id,@Param("is_bundle") int is_bundle,@Param("is_pay") int is_pay,@Param("meter_item_name") String meter_item_name);

    @Select("select * from f_meter_item f where f.tenement_id = #{tenement_id} and f.tenant_id = #{tenant_id} and f.is_bundle = #{is_bundle} and f.is_pay=#{is_pay}" +
            " and f.meter_item_name like '%'||#{meter_item_name}||'%' ")
    List<MeterItem> getSumMeterItemList(@Param("tenement_id") String tenement_id,@Param("tenant_id") String tenant_id,@Param("is_bundle") int is_bundle,@Param("is_pay") int is_pay,@Param("meter_item_name") String meter_item_name);


    //捆绑账单
    @Update("update f_meter_item set is_bundle=1 , bill_id=#{bill_id}  where METER_ITEM_ID=#{meter_item_id} ")

    int updateBundle(@Param("tenement_id")String tenement_id,@Param("meter_item_id")String meter_item_id,@Param("bill_id")String  bill_id );


    //账单下账
    @Update("update f_meter_item  set is_pay=1 , is_bundle=1   where  tenement_id=#{tenement_id} and tenant_id=#{tenant_id} and bill_id=#{bill_id}")
    int updateBillPay(@Param("tenement_id")String tenement_id,@Param("tenant_id")String  tenant_id,@Param("bill_id")String bill_id);

    @Select("select * from f_meter_item f where f.tenement_id = #{tenement_id} and f.tenant_id = #{tenant_id} " +
            "  and f.meter_item_dt>=to_date(#{stDt},'yyyy/mm/dd hh24:mi:ss')  and f.meter_item_dt <=to_date(#{endDt},  'yyyy/mm/dd hh24:mi:ss') order by f.meter_item_dt desc  ")
    List<MeterItem> getMeterItemList(@Param("tenement_id") String tenement_id,@Param("tenant_id") String tenant_id,@Param("is_bundle") int is_bundle,@Param("is_pay") int is_pay,
                                     @Param("stDt") String stDt,@Param("endDt") String endDt);

    //根据房屋编号查找费用项
    @Select("select * from F_METER_ITEM f where  f.meter_item_id= #{meter_item_id}")
    MeterItem getMeterItemById(@Param("meter_item_id") String meter_item_id);

}
