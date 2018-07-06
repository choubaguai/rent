package com.xinlong.rent.mapper;

import com.xinlong.rent.daomain.Customer;
import com.xinlong.rent.daomain.Owner;
import org.apache.ibatis.annotations.Insert;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface OwnerMapper {

    //注册
    @Insert("insert into F_RENTAL_OWNER(OWNER_ID,OWNER_NAME,NICKNAME,OWNER_PHONE,OWNER_PASSWORD,IS_DELETE,OWNER_DT) values(#{owner_Id},#{owner_Name},#{nickname}, #{owner_Phone}, #{owner_Password},#{is_Delete},#{owner_dt})")
    int addOwner(Owner owner);

    //根据手机号码查找用户
    @Select("select * from F_RENTAL_OWNER where OWNER_PHONE = #{owner_Phone} and IS_DELETE=1")
    Owner findByPhone(@Param("owner_Phone") String owner_Phone);

    //登录
    @Select("select * from F_RENTAL_OWNER where OWNER_PHONE = #{owner_Phone} and OWNER_PASSWORD = #{owner_password} and IS_DELETE=1")
    Owner login(@Param("owner_Phone") String owner_Phone, @Param("owner_password") String owner_password);



    //根据手机号码查找用户
    @Select("select * from f_customer where customer_tel = #{customer_tel} and is_cancellation=1")
    Customer findByTel(@Param("customer_tel") String customer_tel);

    //注册
    @Insert("insert into f_customer (customer_id,customer_name,customer_tel,customer_passwrod,customer_number,is_cancellation,remarks,customer_dt)" +
            "  values(#{customer_id},#{customer_name},#{customer_tel}, #{customer_passwrod}, #{customer_number},#{is_cancellation},#{remarks},#{customer_dt})")
    int addCustomer(Customer customer);


    //登录
    @Select("select * from f_customer where customer_tel = #{customer_tel} and customer_passwrod = #{customer_passwrod} and is_cancellation=1")
    Customer loginCus(@Param("customer_tel") String customer_tel, @Param("customer_passwrod") String customer_passwrod);
}
