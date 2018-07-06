package com.xinlong.rent.web;


import com.xinlong.rent.daomain.*;
import com.xinlong.rent.service.BillService;
import com.xinlong.rent.service.CostItemService;
import com.xinlong.rent.service.TenantService;
import com.xinlong.rent.service.TenementService;
import com.xinlong.rent.tools.UtilDate;
import com.xinlong.rent.tools.Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

@RestController
@Api("账单:API")
@RequestMapping("/bill")
public class BillController {

    private static Logger logger = LogManager.getLogger(BillController.class.getName());

    @Autowired
    private BillService billService;

    @Autowired
    private TenementService tenementService;

    @Autowired
    private TenantService tenantService;

    @Autowired
    private CostItemService costItemService;


    /**
     * POST用RequestParam，GET用PathVariable，一个参数用PathVariable，多个参数用RequestParam
     */
    @PostMapping("/addBill")
    @ApiOperation(value = "生成账单", notes = "根据传入的信息生成账单")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "tenement_id", dataType = "String", required = true, value = "房屋ID"),
            @ApiImplicitParam(paramType = "query", name = "tenant_id", dataType = "String", required = true, value = "租户ID"),
            @ApiImplicitParam(paramType = "query", name = "state", dataType = "int", required = true, value = "是否第一次0/1"),
            @ApiImplicitParam(paramType = "query", name = "bill_type", dataType = "int", required = true, value = "账单类型"),
            @ApiImplicitParam(paramType = "query", name = "remark", dataType = "String", required = true, value = "备注")


    })

    public Map<String, Object> addCostItem(@RequestParam("tenement_id") String tenement_id,
                                           @RequestParam("tenant_id") String tenant_id,
                                           @RequestParam("state") int state,
                                           @RequestParam("bill_type") int bill_type,
                                           @RequestParam("remark") String remark ) {

        Map<String, Object> map = new HashMap<>();
        if (null == tenement_id || "".equals(tenement_id)) {
            map.put("code", 1);
            map.put("message", "房屋ID不能为空");
            return map;
        }
        if (null == tenant_id || "".equals(tenant_id)) {
            map.put("code", 1);
            map.put("message", "租户ID不能为空");
            return map;
        }
        BigDecimal wai=new BigDecimal(0);
        Tenement trt= tenementService.getTenementByCodeId(tenement_id);
        if(trt!=null){
            Tenant  tr = tenantService.findByTenantById(tenant_id);
         if(tr!=null){
             CostItem costItem= costItemService.findCostItemById(tenement_id);
             if(costItem!=null){
                 /****租金+抄表项+基本费用***/
                 if(bill_type==0){
                      wai= costItemService.getSumMeterItem(tenement_id,tenant_id,0,0);
                     if(wai==null){
                         wai=new BigDecimal(0);
                     }
                         Bill bill = new Bill();
                         bill.setBill_id(Utils.getUUID());
                         bill.setTenement_id(tenement_id);
                         bill.setTenant_id(tenant_id);
                         bill.setBill_name(UtilDate.getYearMonth() + tr.getTenant_name() +trt.getTower_no()+"层"+ trt.getTenement_code() + "室账单");
                         bill.setGenerated_date(new Date());
                         bill.setIs_or_not_pay(2);
                         bill.setRemark(remark);
                         bill.setIs_delete(1);
                         bill.setMonth_dt(UtilDate.getThisMonth());
                         bill.setBill_type(0);
                         bill.setBill_rent(tr.getRent());

                         /*****每个月的房屋基本费用****/
                         BigDecimal evr = costItem.getFixed_water().add(costItem.getNetwork_fee()).add(costItem.getSanitation_fee()).add(costItem.getElse_fee());
                         /*****收租周期****/
                         BigDecimal one1 = tr.getRent().add(evr);
                         if (tr.getProduce_cycle().equals("1")) {//每个月
                             BigDecimal sne1 = one1.multiply(new BigDecimal(1));
                             if (state == 0) {
                                 bill.setRent(sne1.add(wai).add(tr.getCash_pledge()));
                                 bill.setCash_pledge(tr.getCash_pledge());
                             } else {
                                 bill.setRent(sne1.add(wai));
                                 bill.setCash_pledge(new BigDecimal(0));
                             }

                         } else if (tr.getProduce_cycle().equals("3")) {
                             BigDecimal sne1 = one1.multiply(new BigDecimal(3));
                             if (state == 0) {
                                 bill.setRent(sne1.add(wai).add(tr.getCash_pledge()));
                                 bill.setCash_pledge(tr.getCash_pledge());
                             } else {
                                 bill.setRent(sne1.add(wai));
                                 bill.setCash_pledge(new BigDecimal(0));
                             }

                         } else if (tr.getProduce_cycle().equals("6")) {
                             BigDecimal sne1 = one1.multiply(new BigDecimal(6));
                             if (state == 0) {
                                 bill.setRent(sne1.add(wai).add(tr.getCash_pledge()));
                                 bill.setCash_pledge(tr.getCash_pledge());
                             } else {
                                 bill.setRent(sne1.add(wai));
                                 bill.setCash_pledge(new BigDecimal(0));
                             }
                         }
                         boolean flag = billService.addBill(bill);
                         List<MeterItem> lst=costItemService.getMonthMeterItemListToatl(tenement_id, tenant_id, 0, 0);
                         if(lst.size()>0){
                             for(int i = 0;i < lst.size(); i ++){
                                 MeterItem  mi= lst.get(i);
                                 costItemService.updateBundle(tenement_id,  mi.getMeter_item_id(),bill.getBill_id());
                             }
                         }
                         if (flag) {
                             map.put("code", 0);
                             map.put("bill", bill);
                             map.put("message", "添加成功");
                         } else {
                             map.put("code", 1);
                             map.put("message", "添加失败");
                         }

                 }else if(bill_type==1){
                     //房屋租金+房屋基本费
                     wai = costItemService.getSumMeterItems(tenement_id, tenant_id, 0, 0,"水表");
                     if (wai == null) {
                         wai = new BigDecimal(0);
                     }
                     Bill bill=new Bill();
                     bill.setBill_id(Utils.getUUID());
                     bill.setTenement_id(tenement_id);
                     bill.setTenant_id(tenant_id);
                     bill.setBill_name(UtilDate.getYearMonth()+tr.getTenant_name()+trt.getTower_no()+"层"+ trt.getTenement_code()+"室基本账单");
                     bill.setGenerated_date(new Date());
                     bill.setIs_or_not_pay(2);
                     bill.setRemark(remark);
                     bill.setIs_delete(1);
                     bill.setMonth_dt(UtilDate.getThisMonth());
                     bill.setBill_type(1);
                     bill.setBill_rent(tr.getRent());
                     /*****每个月的房屋基本费用****/
                     BigDecimal evr=costItem.getFixed_water().add(costItem.getNetwork_fee()).add(costItem.getSanitation_fee()).add(costItem.getElse_fee());
                     /*****收租周期****/
                     BigDecimal one1=  tr.getRent().add(evr);
                     if(tr.getProduce_cycle().equals("1")){//每个月
                         if (state == 0) {
                             bill.setRent(one1.multiply(new BigDecimal(1)).add(wai).add(tr.getCash_pledge()));
                             bill.setCash_pledge(tr.getCash_pledge());
                         } else {
                             bill.setRent(one1.multiply(new BigDecimal(1)).add(wai));
                             bill.setCash_pledge(new BigDecimal(0));
                         }
                     }else if(tr.getProduce_cycle().equals("3")){
                         if (state == 0) {
                             bill.setRent(one1.multiply(new BigDecimal(3)).add(wai).add(tr.getCash_pledge()));
                             bill.setCash_pledge(tr.getCash_pledge());
                         } else {
                             bill.setRent(one1.multiply(new BigDecimal(3)).add(wai));
                             bill.setCash_pledge(new BigDecimal(0));
                         }

                     }else if(tr.getProduce_cycle().equals("6")){
                         if (state == 0) {
                             bill.setRent(one1.multiply(new BigDecimal(6)).add(wai).add(tr.getCash_pledge()));
                             bill.setCash_pledge(tr.getCash_pledge());
                         } else {
                             bill.setRent(one1.multiply(new BigDecimal(6)).add(wai));
                             bill.setCash_pledge(new BigDecimal(0));
                         }
                     }
                     boolean flag =  billService.addBill(bill);
                     List<MeterItem> lst=costItemService.getSumMeterItemList(tenement_id, tenant_id, 0, 0,"水表");
                     if(lst.size()>0){
                         for(int i = 0;i < lst.size(); i ++){
                             MeterItem  mi= lst.get(i);
                             costItemService.updateBundle(tenement_id,  mi.getMeter_item_id(),bill.getBill_id());
                         }
                     }
                     if (flag) {
                         map.put("code", 0);
                         map.put("bill", bill);
                         map.put("message", "添加成功");
                     } else {
                         map.put("code", 1);
                         map.put("message", "添加失败");
                     }
                 }else if(bill_type==2) {
                     //房屋电费账单
                      wai = costItemService.getSumMeterItems(tenement_id, tenant_id, 0, 0,"电表");
                     if (wai == null) {
                         wai = new BigDecimal(0);
                     }
                         Bill bill1 = new Bill();
                         bill1.setBill_id(Utils.getUUID());
                         bill1.setTenement_id(tenement_id);
                         bill1.setTenant_id(tenant_id);
                         bill1.setBill_name(UtilDate.getYearMonth() + tr.getTenant_name() +trt.getTower_no()+"层"+  trt.getTenement_code() + "室电费账单");
                         bill1.setGenerated_date(new Date());
                         bill1.setIs_or_not_pay(2);
                         bill1.setRemark(remark);
                         bill1.setIs_delete(1);
                         bill1.setMonth_dt(UtilDate.getThisMonth());
                         bill1.setBill_type(2);
                         bill1.setRent(wai);
                         bill1.setBill_rent(tr.getRent());
                         bill1.setCash_pledge(new BigDecimal(0));
                         boolean flag1 = billService.addBill(bill1);
                        List<MeterItem> lst=costItemService.getSumMeterItemList(tenement_id, tenant_id, 0, 0,"电表");
                        if(lst.size()>0){
                            for(int i = 0;i < lst.size(); i ++){
                                MeterItem  mi= lst.get(i);
                                costItemService.updateBundle(tenement_id,  mi.getMeter_item_id(),bill1.getBill_id());
                            }
                        }

                         if (flag1) {
                             map.put("code", 0);
                             map.put("bill1", bill1);
                             map.put("message", "电费添加成功");
                         } else {
                             map.put("code", 1);
                             map.put("message", "添加失败");
                         }
                     }

            /****判断是否捆绑：租金+抄表项+基本费用***//*

                // List<MeterItem>  mitemlist= costItemService.getMonthMeterItemList(tenement_id,tenant_id,trt.getIs_bundle(),0);

                    // BigDecimal wai=new BigDecimal(0);
                     *//*if(mitemlist.size()>0){
                         for(int i = 0;i < mitemlist.size(); i ++){
                             MeterItem  mi= mitemlist.get(i);
                             wai=wai.add(mi.getAmount());
                         }
                     }*/

             }else{
                 map.put("code", 1);
                 map.put("message", "费用项不存在");
             }

            }else{
                map.put("code", 1);
                map.put("message", "租客不存在");
            }
        }else{
            map.put("code", 1);
            map.put("message", "房屋不存在");
        }

        return map;
    }

    @GetMapping("/getBillById")
    @ApiOperation(value = "查看账单", notes = "根据账单id")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "bill_id", dataType = "String", required = true, value = "账单ID" ),
            @ApiImplicitParam(paramType = "query", name = "is_or_not_pay", dataType = "int", required = true, value = "2未交1已交" )

    })
    public Map<String, Object> getBillById(@RequestParam("bill_id") String bill_id,
                                           @RequestParam("is_or_not_pay") int is_or_not_pay) {

        Map<String, Object> map = new HashMap<>();
        if (null == bill_id || "".equals(bill_id)) {
            map.put("code", 1);
            map.put("message", "账单ID不能为空");
            return map;
        }
        Bill bl= billService.findBillById(bill_id);
        if(bl!=null){
            Tenement trt= tenementService.getTenementByCodeId(bl.getTenement_id());

            if(trt!=null){
                Tenant  tr = tenantService.findByTenantById(bl.getTenant_id());
                if(tr!=null){
                    CostItem costItem= costItemService.findCostItemById(bl.getTenement_id());
                    if(costItem!=null){
                        BillDetail bdl=new BillDetail();
                        List<MeterItem>  mitemlist =null;
                        List<MeterItem>  mlist=null;
                        List<Bill>  billList=billService.queryBillLists(bl.getTenement_id(),bl.getTenant_id());
                        if(is_or_not_pay==1){//已交
                            mitemlist=costItemService.getMonthMeterItemList(trt.getTenement_id(),tr.getTenant_id(),1,1,bl.getBill_id());
                            if(bl.getBill_type()==0){/****租金+抄表项+基本费用***/
                                if(mitemlist.size()>0){
                                    bdl.setMetslist(mitemlist);
                                }
                                bdl.setBill_name(bl.getBill_name());
                                bdl.setGenerated_date(bl.getGenerated_date());
                                bdl.setTenant_name(tr.getTenant_name());
                                bdl.setTenant_code(tr.getTenant_code());
                                bdl.setIs_or_not_pay(bl.getIs_or_not_pay());
                                bdl.setFixed_water(costItem.getFixed_water());
                                bdl.setNetwork_fee(costItem.getNetwork_fee());
                                bdl.setSanitation_fee(costItem.getSanitation_fee());
                                bdl.setElse_fee(costItem.getElse_fee());
                                bdl.setRent(bl.getRent());
                                bdl.setCost_item_month(UtilDate.getDrontMonth());
                                bdl.setBill_type(bl.getBill_type());
                                bdl.setBillRent(bl.getBill_rent());
                                bdl.setCash_pledge(bl.getCash_pledge());
                                bdl.setProduce_cycle(tr.getProduce_cycle());
                                map.put("code", 0);
                                map.put("billDetail", bdl);
                                map.put("message", "账单获取成功");
                            }else if(bl.getBill_type()==1){//房屋租金+房屋基本费
                                mlist=costItemService.findLastMonthMeterItems(trt.getTenement_id(), 1, 1,tr.getTenant_id(),"水表",bl.getBill_id());
                                if(mlist.size()>0){
                                    bdl.setMetslist(mlist);
                                }
                                bdl.setBill_name(bl.getBill_name());
                                bdl.setGenerated_date(bl.getGenerated_date());
                                bdl.setTenant_name(tr.getTenant_name());
                                bdl.setTenant_code(tr.getTenant_code());
                                bdl.setIs_or_not_pay(bl.getIs_or_not_pay());
                                bdl.setFixed_water(costItem.getFixed_water());
                                bdl.setNetwork_fee(costItem.getNetwork_fee());
                                bdl.setSanitation_fee(costItem.getSanitation_fee());
                                bdl.setElse_fee(costItem.getElse_fee());
                                bdl.setRent(bl.getRent());
                                bdl.setCost_item_month(UtilDate.getDrontMonth());
                                bdl.setBill_type(bl.getBill_type());
                                bdl.setBillRent(bl.getBill_rent());
                                bdl.setCash_pledge(bl.getCash_pledge());
                                bdl.setProduce_cycle(tr.getProduce_cycle());
                                map.put("billDetail", bdl);
                                map.put("code", 0);
                                map.put("message", "账单获取成功");
                            }else if(bl.getBill_type()==2){ //房屋电费账单
                                mlist=costItemService.findLastMonthMeterItems(trt.getTenement_id(), 1, 1,tr.getTenant_id(),"电表",bl.getBill_id());
                                if(mlist.size()>0){
                                    bdl.setMetslist(mlist);
                                }
                                bdl.setBill_name(bl.getBill_name());
                                bdl.setGenerated_date(bl.getGenerated_date());
                                bdl.setTenant_name(tr.getTenant_name());
                                bdl.setTenant_code(tr.getTenant_code());
                                bdl.setIs_or_not_pay(bl.getIs_or_not_pay());
                                bdl.setRent(bl.getRent());
                                bdl.setCost_item_month(UtilDate.getDrontMonth());
                                bdl.setBillRent(bl.getBill_rent());
                                bdl.setBill_type(bl.getBill_type());
                                map.put("billDetail", bdl);
                                map.put("code", 0);
                                map.put("message", "账单获取成功");
                            }

                        }else{
                            mitemlist=costItemService.getMonthMeterItemList(trt.getTenement_id(),tr.getTenant_id(),1,0,bl.getBill_id());
                            if(bl.getBill_type()==0){/****租金+抄表项+基本费用***/
                                if(mitemlist.size()>0){
                                    bdl.setMetslist(mitemlist);
                                }
                                bdl.setBill_name(bl.getBill_name());
                                bdl.setGenerated_date(bl.getGenerated_date());
                                bdl.setTenant_name(tr.getTenant_name());
                                bdl.setTenant_code(tr.getTenant_code());
                                bdl.setIs_or_not_pay(bl.getIs_or_not_pay());
                                bdl.setFixed_water(costItem.getFixed_water());
                                bdl.setNetwork_fee(costItem.getNetwork_fee());
                                bdl.setSanitation_fee(costItem.getSanitation_fee());
                                bdl.setElse_fee(costItem.getElse_fee());
                                bdl.setRent(bl.getRent());
                                bdl.setCost_item_month(UtilDate.getDrontMonth());
                                bdl.setBill_type(bl.getBill_type());
                                bdl.setBillRent(bl.getBill_rent());
                                bdl.setCash_pledge(bl.getCash_pledge());
                                bdl.setProduce_cycle(tr.getProduce_cycle());
                                map.put("billDetail", bdl);
                                map.put("code", 0);
                                map.put("message", "账单获取成功");
                            }else if(bl.getBill_type()==1){//房屋租金+房屋基本费
                                mlist=costItemService.findLastMonthMeterItems(trt.getTenement_id(), 1, 0,tr.getTenant_id(),"水表",bl.getBill_id());
                                if(mlist.size()>0){
                                    bdl.setMetslist(mlist);
                                }
                                bdl.setBill_name(bl.getBill_name());
                                bdl.setGenerated_date(bl.getGenerated_date());
                                bdl.setTenant_name(tr.getTenant_name());
                                bdl.setTenant_code(tr.getTenant_code());
                                bdl.setIs_or_not_pay(bl.getIs_or_not_pay());
                                bdl.setFixed_water(costItem.getFixed_water());
                                bdl.setNetwork_fee(costItem.getNetwork_fee());
                                bdl.setSanitation_fee(costItem.getSanitation_fee());
                                bdl.setElse_fee(costItem.getElse_fee());
                                bdl.setRent(bl.getRent());
                                bdl.setCost_item_month(UtilDate.getDrontMonth());
                                bdl.setBill_type(bl.getBill_type());
                                bdl.setBillRent(bl.getBill_rent());
                                bdl.setCash_pledge(bl.getCash_pledge());
                                bdl.setProduce_cycle(tr.getProduce_cycle());
                                map.put("billDetail", bdl);
                                map.put("code", 0);
                                map.put("message", "账单获取成功");


                            }else if(bl.getBill_type()==2){ //房屋电费账单
                                mlist=costItemService.findLastMonthMeterItems(trt.getTenement_id(), 1, 0,tr.getTenant_id(),"电表",bl.getBill_id());
                                if(mlist.size()>0){
                                    bdl.setMetslist(mlist);
                                }
                                bdl.setBill_name(bl.getBill_name());
                                bdl.setGenerated_date(bl.getGenerated_date());
                                bdl.setTenant_name(tr.getTenant_name());
                                bdl.setTenant_code(tr.getTenant_code());
                                bdl.setIs_or_not_pay(bl.getIs_or_not_pay());
                                bdl.setRent(bl.getRent());
                                bdl.setCost_item_month(UtilDate.getDrontMonth());
                                bdl.setBill_type(bl.getBill_type());
                                map.put("billDetail", bdl);
                                map.put("code", 0);
                                map.put("message", "账单获取成功");
                            }
                        }

                    }else{
                        map.put("code", 1);
                        map.put("message", "费用项不存在");
                    }
                }else{
                    map.put("code", 1);
                    map.put("message", "租客不存在");
                }
            }else{
                map.put("code", 1);
                map.put("message", "房屋不存在");
            }
        }else{
            map.put("code", 1);
            map.put("message", "账单不存在");
        }
        return map;
    }


    @GetMapping("/updateBill")
    @ApiOperation(value = "完成账单", notes = "根据账单ID修改账单状态")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "bill_id", dataType = "String", required = true, value = "账单ID" )
    })
    public Map<String, Object> updateBill(@RequestParam("bill_id") String bill_id) {

        Map<String, Object> map = new HashMap<>();
        if (null == bill_id || "".equals(bill_id)) {
            map.put("code", 1);
            map.put("message", "账单ID不能为空");
            return map;
        }
        Bill bl= billService.findBillById(bill_id);
        if(bl!=null){
                    boolean flag =  billService.updateBill(bill_id);
                    costItemService.updateBillPay(bl.getTenement_id(),bl.getTenant_id(),bill_id);
                    if (flag) {
                        map.put("code", 0);
                        map.put("message", "修改成功");
                    } else {
                        map.put("code", 1);
                        map.put("message", "修改失败");
                    }

        }else{
            map.put("code", 1);
            map.put("message", "账单不存在");
        }
        return map;
    }
    @GetMapping("/queryBillList")
    @ApiOperation(value = "账单列表", notes = "根据房屋ID和租客ID查询账单列表倒序")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "tenement_id", dataType = "String", required = true, value = "房屋ID" ),
            @ApiImplicitParam(paramType = "query", name = "tenant_id", dataType = "String", required = true, value = "租客ID" ),
            @ApiImplicitParam(paramType = "query", name = "is_or_not_pay", dataType = "int", required = true, value = "是否交租" )

    })
    public Map<String, Object> queryBillList(@RequestParam("tenement_id") String tenement_id,@RequestParam("tenant_id") String tenant_id,
                                             @RequestParam("is_or_not_pay") int is_or_not_pay) {

        Map<String, Object> map = new HashMap<>();
        if (null == tenement_id || "".equals(tenement_id)) {
            map.put("code", 1);
            map.put("message", "房屋ID不能为空");
            return map;
        }


       List<Bill>  bllist= billService.queryBillList(tenement_id,tenant_id,is_or_not_pay);
        if(bllist.size()>0){
                map.put("code", 0);
                map.put("message", "成功获取账单");
                map.put("bllist",bllist);
        }else {
            map.put("code", 1);
            map.put("message", "账单不存在");
        }

        return map;
    }

    @GetMapping("/findBillTenementId")
    @ApiOperation(value = "房屋查看账单", notes = "根据房屋ID和租客ID")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "tenement_id", dataType = "String", required = true, value = "房屋ID" ),
            @ApiImplicitParam(paramType = "query", name = "tenant_id", dataType = "String", required = true, value = "租客ID" )
    })
    public Map<String, Object> findBillTenementId(@RequestParam("tenement_id") String tenement_id,@RequestParam("tenant_id") String  tenant_id) {

        Map<String, Object> map = new HashMap<>();
        if (null == tenement_id || "".equals(tenement_id)) {
            map.put("code", 1);
            map.put("message", "房屋ID不能为空");
            return map;
        }
        Bill bl= billService.findBillTenementId(tenement_id,UtilDate.getThisMonth(),tenant_id);
        if(bl==null){
            map.put("code", 0);
            map.put("message", "未生成账单");
        }else{
            map.put("code", 1);
            map.put("message", "本月账单生成");
        }

        return map;
    }

    @GetMapping("/queryYearBillList")
    @ApiOperation(value = "收支流水", notes = "is_or_not_pay：0全部1已交2未交")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "tenement_id", dataType = "String", required = true, value = "房屋ID" ),
            @ApiImplicitParam(paramType = "query", name = "is_or_not_pay", dataType = "String", required = true, value = "是否交租" )
    })
    public Map<String, Object> queryYearBillList(@RequestParam("tenement_id") String tenement_id,@RequestParam("is_or_not_pay") int is_or_not_pay) {

        Map<String, Object> map = new HashMap<>();
        if (null == tenement_id || "".equals(tenement_id)) {
            map.put("code", 1);
            map.put("message", "房屋ID不能为空");
            return map;
        }
        if(is_or_not_pay==0){
            List<Bill>  yearbllist= billService.queryYearBillListAll(tenement_id);
            if(yearbllist.size()>0){
                map.put("code", 0);
                map.put("message", "获取成功");
                map.put("yearbllist",yearbllist);
            }else{
                map.put("code", 1);
                map.put("message", "账单不存在");
            }
        }else{
            List<Bill>  yearbllist= billService.queryYearBillList(tenement_id,is_or_not_pay);
            if(yearbllist.size()>0){
                map.put("code", 0);
                map.put("message", "获取成功");
                map.put("yearbllist",yearbllist);
            }else{
                map.put("code", 1);
                map.put("message", "账单不存在");
            }
        }
        return map;
    }

    @GetMapping("/getBillIsNotpay")
    @ApiOperation(value = "查看未交账单", notes = "房屋ID/租客ID")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "tenement_id", dataType = "String", required = true, value = "房屋ID" ),
            @ApiImplicitParam(paramType = "query", name = "tenant_id", dataType = "String", required = true, value = "租客ID" ),
            @ApiImplicitParam(paramType = "query", name = "bill_type", dataType = "int", required = true, value = "0捆绑1基本2电费" ),

    })
    public Map<String, Object> getBillIsNotpay(@RequestParam("tenement_id") String tenement_id,@RequestParam("tenant_id") String tenant_id,@RequestParam("bill_type") int bill_type) {

        Map<String, Object> map = new HashMap<>();
        if (null == tenement_id || "".equals(tenement_id)) {
            map.put("code", 1);
            map.put("message", "房屋ID不能为空");
            return map;
        }
        List<Bill> ist=null;
        if(bill_type==3){
            ist= billService.getBillIsNotpays(tenement_id,tenant_id);
        }else{
            ist= billService.getBillIsNotpay(tenement_id,tenant_id,bill_type);
        }

            if(ist.size()>0){
                map.put("code", 0);
                map.put("message", "获取成功");
                map.put("ist",ist);
            }else{
                map.put("code", 1);
                map.put("message", "账单不存在");

            }

        return map;
    }

}
