package com.xinlong.rent.mapper;

import com.xinlong.rent.daomain.Bill;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface BillMapper {


    //生成账单
    @Insert("insert into F_RENTAL_BILL (BILL_ID,TENEMENT_ID,TENANT_ID,BILL_NAME,GENERATED_DATE,IS_OR_NOT_PAY,RENT, REMARK,IS_DELETE,MONTH_DT,bill_type,cash_pledge,bill_rent) " +
            "values(#{bill_id},#{tenement_id},#{tenant_id},#{bill_name}, #{generated_date}, #{is_or_not_pay},#{rent},#{remark},#{is_delete},#{month_dt},#{bill_type},#{cash_pledge},#{bill_rent})")
    int addBill(Bill bill);


    @Select("select * from F_RENTAL_BILL where  BILL_ID= #{bill_id} and IS_DELETE = 1")
    Bill findBillById(@Param("bill_id") String bill_id);


    @Select("select * from F_RENTAL_BILL where  tenement_id= #{tenement_id} and TENANT_ID= #{tenant_id} and month_dt=#{month_dt} and IS_DELETE = 1")
    Bill findBillTenementId(@Param("tenement_id") String tenement_id,@Param("month_dt") String month_dt,@Param("tenant_id") String  tenant_id);

    //物理删除
    @Update("update F_RENTAL_BILL set IS_OR_NOT_PAY=1 where BILL_ID=#{bill_id}")
    int updateBill(@Param("bill_id") String bill_id);

    @Select("select * from F_RENTAL_BILL where  TENEMENT_ID= #{tenement_id} and TENANT_ID= #{tenant_id} and IS_DELETE = 1 and IS_OR_NOT_PAY= #{is_or_not_pay} order by generated_date desc")
    List<Bill> queryBillList(@Param("tenement_id") String tenement_id,@Param("tenant_id") String  tenant_id,
                             @Param("is_or_not_pay") int  is_or_not_pay);

    @Select("select * from F_RENTAL_BILL where  TENEMENT_ID= #{tenement_id} and TENANT_ID= #{tenant_id} and IS_DELETE = 1  order by generated_date desc")
    List<Bill> queryBillLists(@Param("tenement_id") String tenement_id,@Param("tenant_id") String  tenant_id );


    @Select("select * from F_RENTAL_BILL where  TENEMENT_ID= #{tenement_id} and IS_OR_NOT_PAY= #{is_or_not_pay} and IS_DELETE = 1 order by generated_date desc")
    List<Bill> queryYearBillList(@Param("tenement_id") String tenement_id,@Param("is_or_not_pay") int  is_or_not_pay);

    @Select("select * from F_RENTAL_BILL where  TENEMENT_ID= #{tenement_id} and IS_DELETE = 1 order by generated_date desc")
    List<Bill> queryYearBillListAll(@Param("tenement_id") String tenement_id);

    @Select("select * from F_RENTAL_BILL where  tenement_id= #{tenement_id} and TENANT_ID= #{tenant_id} and bill_type= #{bill_type} and is_or_not_pay=2 and IS_DELETE = 1")
    List<Bill> getBillIsNotpay(@Param("tenement_id") String tenement_id,@Param("tenant_id") String  tenant_id,@Param("bill_type") int bill_type);

    @Select("select * from F_RENTAL_BILL where  tenement_id= #{tenement_id} and TENANT_ID= #{tenant_id}  and is_or_not_pay=2 and IS_DELETE = 1")
    List<Bill> getBillIsNotpays(@Param("tenement_id") String tenement_id,@Param("tenant_id") String  tenant_id);
}
