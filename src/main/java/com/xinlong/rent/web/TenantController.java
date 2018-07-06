package com.xinlong.rent.web;

import com.xinlong.rent.daomain.Tenant;
import com.xinlong.rent.daomain.Tenement;

import com.xinlong.rent.service.TenantService;
import com.xinlong.rent.service.TenementService;
import com.xinlong.rent.tools.UtilDate;
import com.xinlong.rent.tools.Utils;
import io.swagger.annotations.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Api("租客:API")
@RequestMapping("/tenant")
public class TenantController {

    private static Logger logger = LogManager.getLogger(TenementController.class.getName());


    @Autowired
    private TenementService tenementService;

    @Autowired
    private TenantService tenantService;


    /**
     * POST用RequestParam，GET用PathVariable，一个参数用PathVariable，多个参数用RequestParam
     */
    @PostMapping("/addTenant")
    @ApiOperation(value = "添加租客", notes = "根据传入相关参数建立租客档案")
    @ApiImplicitParams({

            @ApiImplicitParam(paramType = "query", name = "code", dataType = "String", required = true, value = "房屋编号"),
            @ApiImplicitParam(paramType = "query", name = "house_id", dataType = "String", required = true, value = "房产ID"),
            @ApiImplicitParam(paramType = "query", name = "tenant_name", dataType = "String", required = true, value = "租客名称"),
            @ApiImplicitParam(paramType = "query", name = "tenant_code", dataType = "String", required = true, value = "租客电话"),
            @ApiImplicitParam(paramType = "query", name = "id_card", dataType = "String", required = true, value = "租客身份证号"),
            @ApiImplicitParam(paramType = "query", name = "start_date", dataType = "String", required = true, value = "起租日期"),
            @ApiImplicitParam(paramType = "query", name = "end_date", dataType = "String", required = true, value = "到期日期"),
            @ApiImplicitParam(paramType = "query", name = "collect_date", dataType = "String", required = true, value = "收租日期"),
            @ApiImplicitParam(paramType = "query", name = "produce_cycle", dataType = "String", required = true, value = "收租周期"),
            @ApiImplicitParam(paramType = "query", name = "rent", dataType = "BigDecimal", required = true, value = "交租金额"),
            @ApiImplicitParam(paramType = "query", name = "cash_pledge", dataType = "BigDecimal", required = true, value = "押金"),
            @ApiImplicitParam(paramType = "query", name = "rental_num", dataType = "String", required = true, value = "人数"),
            @ApiImplicitParam(paramType = "query", name = "remark", dataType = "String", required = true, value = "备注")

    })
    public Map<String, Object> addHouse(@RequestParam("code") String code,
                                        @RequestParam("house_id") String house_id,
                                        @RequestParam("tenant_name") String tenant_name,
                                        @RequestParam("tenant_code") String tenant_code,
                                        @RequestParam("id_card") String id_card,
                                        @RequestParam("start_date") String start_date,
                                        @RequestParam("end_date") String end_date,
                                        @RequestParam("collect_date") String collect_date,
                                        @RequestParam("produce_cycle") String produce_cycle,
                                        @RequestParam("rent") String rent,
                                        @RequestParam("cash_pledge") String  cash_pledge,
                                        @RequestParam("rental_num") int rental_num,
                                        @RequestParam("remark") String remark
                                        ) {

        Map<String, Object> map = new HashMap<>();

        if (null == code || "".equals(code)) {
            map.put("code", 1);
            map.put("message", "房屋编号不能为空");
            return map;
        }
            Tenement tm =tenementService.findByTenementCode(code.trim(),house_id);

            if( tm!= null){
                try {

                Tenant tenant=new  Tenant();
                tenant.setTenant_id(Utils.getUUID());
                tenant.setTenement_id(tm.getTenement_id());
                tenant.setTenant_name(tenant_name);
                tenant.setTenant_code(tenant_code);
                tenant.setTenant_password(Utils.convertMD5("111111"));
                tenant.setId_card(id_card);
                tenant.setStart_date(UtilDate.strToDateLong(start_date));
                tenant.setEnd_date(UtilDate.strToDateLong(end_date));
                tenant.setCollect_date(collect_date);
                tenant.setProduce_cycle(produce_cycle);
                tenant.setRent(new BigDecimal(rent));
                tenant.setCash_pledge(new BigDecimal(cash_pledge));
                tenant.setRental_num(rental_num);
                tenant.setIs_delete(1);
                tenant.setRemark(remark);
                boolean flag = tenantService.addTenant(tenant);
                if (flag) {
                    map.put("code", 0);
                    map.put("message", "添加成功");
                } else {
                    map.put("code", 1);
                    map.put("message", "添加失败");
                }
                boolean flagt =tenementService.updateTenement(1,tm.getTenement_id(),tenant.getTenant_id());
                if (flag) {
                    map.put("code", 0);
                    map.put("message", "修改成功");
                } else {
                    map.put("code", 1);
                    map.put("message", "修改失败");
                }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else{
                map.put("code", 1);
                map.put("message", "屋号不存在");
            }
        return map;
    }




    @GetMapping("/getTenantById")
    @ApiOperation(value = "查询租客", notes = "根据租客ID查询")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "tenant_id", dataType = "String", required = true, value = "租客ID")

    })

    public Map<String, Object> findByTenementById(@RequestParam("tenant_id") String tenant_id) {
        Map<String, Object> map = new HashMap<>();

         Tenant t = tenantService.findByTenantById(tenant_id);
        if (t!= null) {
            map.put("tenant",t);
            map.put("code", 0);
            map.put("message", "查询成功");
        }else{
            map.put("code", 1);
            map.put("message", "未找到租客");
        }
        return map;
    }


    /**
     * POST用RequestParam，GET用PathVariable，一个参数用PathVariable，多个参数用RequestParam
     */
    @PostMapping("/updateTenant")
    @ApiOperation(value = "修改租客", notes = "根据传入相关参数修改租客信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "tenant_id", dataType = "String", required = true, value = "租客ID"),
            @ApiImplicitParam(paramType = "query", name = "tenant_name", dataType = "String", required = true, value = "租客名称"),
            @ApiImplicitParam(paramType = "query", name = "tenant_code", dataType = "String", required = true, value = "租客电话"),
            @ApiImplicitParam(paramType = "query", name = "id_card", dataType = "String", required = true, value = "租客身份证号"),
            @ApiImplicitParam(paramType = "query", name = "start_date", dataType = "String", required = true, value = "起租日期"),
            @ApiImplicitParam(paramType = "query", name = "end_date", dataType = "String", required = true, value = "到期日期"),
            @ApiImplicitParam(paramType = "query", name = "collect_date", dataType = "String", required = true, value = "收租日期"),
            @ApiImplicitParam(paramType = "query", name = "produce_cycle", dataType = "String", required = true, value = "收租周期"),
            @ApiImplicitParam(paramType = "query", name = "rent", dataType = "BigDecimal", required = true, value = "交租金额"),
            @ApiImplicitParam(paramType = "query", name = "cash_pledge", dataType = "BigDecimal", required = true, value = "押金"),
            @ApiImplicitParam(paramType = "query", name = "rental_num", dataType = "int", required = true, value = "人数"),
            @ApiImplicitParam(paramType = "query", name = "remark", dataType = "String", required = true, value = "备注")


    })

    public Map<String, Object> updateTenant( @RequestParam("tenant_id") String tenant_id,
                                        @RequestParam("tenant_name") String tenant_name,
                                        @RequestParam("tenant_code") String tenant_code,
                                        @RequestParam("id_card") String id_card,
                                        @RequestParam("start_date") String start_date,
                                        @RequestParam("end_date") String end_date,
                                        @RequestParam("collect_date") String collect_date,
                                        @RequestParam("produce_cycle") String produce_cycle,
                                        @RequestParam("rent") String rent,
                                        @RequestParam("cash_pledge") String  cash_pledge,
                                        @RequestParam("rental_num") int rental_num,
                                        @RequestParam("remark") String remark
                                        ) {


        Map<String, Object> map = new HashMap<>();

        if (null == tenant_id || "".equals(tenant_id)) {
            map.put("code", 1);
            map.put("message", "租客ID不能为空");
            return map;
        }
        try{
        boolean flag=tenantService.updateTenant(tenant_id,tenant_name,tenant_code,id_card,
                UtilDate.strToDateLong(start_date),UtilDate.strToDateLong(end_date),collect_date,produce_cycle,
                new BigDecimal(rent),new BigDecimal(cash_pledge),rental_num,remark);
        if (flag) {
            map.put("code", 0);
            map.put("message", "修改成功");
        } else {
            map.put("code", 1);
            map.put("message", "修改失败");
        }
     } catch (Exception e) {
        e.printStackTrace();
     }
        return map;
    }

}
