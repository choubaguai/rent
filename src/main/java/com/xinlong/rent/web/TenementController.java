package com.xinlong.rent.web;


import com.xinlong.rent.daomain.*;
import com.xinlong.rent.service.*;
import com.xinlong.rent.tools.Utils;
import io.swagger.annotations.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Api("房屋:API")
@RequestMapping("/tenement")
public class TenementController {

    private static Logger logger = LogManager.getLogger(TenementController.class.getName());

    @Autowired
    private HouseService houseService;

    @Autowired
    private TenementService tenementService;
    @Autowired
    private BillService billService;

    @Autowired
    private TenantService tenantService;

    @Autowired
    private CostItemService costItemService;
    /**
     * POST用RequestParam，GET用PathVariable，一个参数用PathVariable，多个参数用RequestParam
     */
    @PostMapping("/addTenement")
    @ApiOperation(value = "添加房屋", notes = "根据用户传入的信息创建房屋")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "house_id", dataType = "String", required = true, value = "房产ID"),
            @ApiImplicitParam(paramType = "query", name = "tower_no", dataType = "String", required = true, value = "楼号"),
            @ApiImplicitParam(paramType = "query", name = "asset_name", dataType = "String", required = true, value = "资产配置"),
            @ApiImplicitParam(paramType = "query", name = "house_type", dataType = "String", required = true, value = "户型"),
            @ApiImplicitParam(paramType = "query", name = "tenement_code", dataType = "String", required = true, value = "房屋编号"),
            @ApiImplicitParam(paramType = "query", name = "tenement_area", dataType = "String", required = true, value = "房屋面积"),
            @ApiImplicitParam(paramType = "query", name = "monthly_rent", dataType = "BigDecimal", required = true, value = "月租金"),
            @ApiImplicitParam(paramType = "query", name = "is_bundle", dataType = "int", required = true, value = "是否捆绑"),
            @ApiImplicitParam(paramType = "query", name = "is_fixation", dataType = "int", required = true, value = "是否固定"),



    })
    public Map<String, Object> addTenement(@RequestParam("house_id") String house_id,
                                           @RequestParam("tower_no") String tower_no,
                                           @RequestParam("asset_name") String asset_name,
                                           @RequestParam("house_type") String house_type,
                                           @RequestParam("tenement_code") String tenement_code,
                                           @RequestParam("tenement_area") String tenement_area,
                                           @RequestParam("monthly_rent") String monthly_rent,
                                           @RequestParam("is_bundle") int is_bundle,
                                           @RequestParam("is_fixation") int is_fixation
                                         ) {

        //获取房产编号code。
        Map<String, Object> map = new HashMap<>();
        House h =houseService.findByHouseId(house_id);
        if(h!= null){
            Tenement tm =tenementService.findByTenementCode(tenement_code.trim(),house_id);
            if(tm == null){
                Tenement  tenement=new Tenement();
                tenement.setTenement_id(Utils.getUUID());
                tenement.setHouse_id(house_id);
                tenement.setTenant_id("1");
                tenement.setTower_no(tower_no);
                tenement.setTenement_code(tenement_code);
                tenement.setTenement_area(tenement_area);
                if(asset_name==null){
                    tenement.setAsset_name("无配置");
                }else{
                    tenement.setAsset_name(asset_name);
                }
                tenement.setHouse_type(house_type);
                tenement.setMonthly_rent(new BigDecimal(monthly_rent));
                tenement.setIs_or_not_rent(2);
                tenement.setRegister_date(new Date());
                tenement.setRemark("1");
                tenement.setIs_delete(1);
                tenement.setIs_bundle(is_bundle);
                tenement.setIs_fixation(is_fixation);
                boolean flag = tenementService.addTenement(tenement);
                if (flag) {
                    map.put("code", 0);
                    map.put("tenement", tenement);
                    map.put("message", "添加成功");
                } else {
                    map.put("code", 1);
                    map.put("message", "添加失败");
                }
            }else{
                map.put("code", 1);
                map.put("message", "房屋号存在");
            }
        }else {
            map.put("code", 1);
            map.put("message", "房产编号存在");
        }
        return map;
    }

    @GetMapping("/getTenementById")
    @ApiOperation(value = "查询房屋", notes = "根据房产id和状态查询房屋(租房状态:0全部1已租2未租3收租流水带总金额)")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "house_id", dataType = "String", required = true, value = "房产ID"),
            @ApiImplicitParam(paramType = "query", name = "is_or_not_rent", dataType = "int", required = true, value = "租房状态")
    })
    public Map<String, Object> getTenementById(@RequestParam("house_id") String house_id,@RequestParam("is_or_not_rent") int is_or_not_rent) {
        Map<String, Object> map = new HashMap<>();

        if(is_or_not_rent==0){
            List<Tenement> tenementlsit = tenementService.getTenementById(house_id);
            if (tenementlsit.size()>0) {
                map.clear();
                map.put("list",tenementlsit);
                map.put("code", 0);
                map.put("message", "success");
            }else{
                map.put("code", 1);
                map.put("message", "未添加房屋");
            }
        }else if(is_or_not_rent==3){
            List<Tenement> tenementlsitis=tenementService.getTenementIncomeId(house_id);
            if (tenementlsitis.size()>0) {
                map.clear();
                map.put("list",tenementlsitis);
                map.put("code", 0);
                map.put("message", "success");
            }else{
                map.put("code", 1);
                map.put("message", "未添加房屋");
            }
        }else {
            List<Tenement> tenementlsitis=tenementService.getTenementByIsId(house_id,is_or_not_rent);
            if (tenementlsitis.size()>0) {
                map.clear();
                map.put("list",tenementlsitis);
                map.put("code", 0);
                map.put("message", "success");
            }else{
                map.put("code", 1);
                map.put("message", "未添加房屋");
            }
        }
        return map;
    }

    @GetMapping("/getAsset")
    @ApiOperation(value = "查询资产", notes = "查询资产配置")
    public Map<String, Object> getAsset() {
        Map<String, Object> map = new HashMap<>();

        List<Asset> assetList = tenementService.getAsset();
        if (assetList.size()>0) {
            map.clear();
            map.put("list",assetList);
            map.put("code", 0);
            map.put("message", "success");
        }else{
            map.put("code", 1);
            map.put("message", "未发现配置");
        }

        return map;
    }


    @GetMapping("/deleteTenement")
    @ApiOperation(value = "房屋删除", notes = "根据房屋编号和房产ID删除")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "tenement_code", dataType = "String", required = true, value = "房屋编号" ),
            @ApiImplicitParam(paramType = "query", name = "house_id", dataType = "String", required = true, value = "房产ID" )
    })
    public Map<String, Object> deleteTenement(@RequestParam("tenement_code") String tenement_code,
                                              @RequestParam("house_id") String house_id) {
        Map<String, Object> map = new HashMap<>();
        Tenement tm =tenementService.findByTenementCode(tenement_code.trim(),house_id);
      if(tm!=null){
          if(tm.getIs_or_not_rent()==2){
              boolean flag = tenementService.deleteTenement(2,tm.getTenement_id());
              if (flag) {
                  map.put("code", 0);
                  map.put("message", "删除成功");
              } else {
                  map.put("code", 1);
                  map.put("message", "删除失败");
              }
          }else{
              map.put("code", 1);
              map.put("message", "已有租客");
          }
      }else{
          map.put("code", 1);
          map.put("message", "未找到房屋");
      }
        return map;
    }

    @GetMapping("/getReletList")
    @ApiOperation(value = "到期提醒列表", notes = "根据房产ID查询")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "house_id", dataType = "String", required = true, value = "房产ID")

    })
    public Map<String, Object> getReletList(@RequestParam("house_id") String house_id) {
        Map<String, Object> map = new HashMap<>();
        if (null == house_id || "".equals(house_id)) {
            map.put("code", 1);
            map.put("message", "房产ID不能为空");
            return map;
         }
            List<Relet> reletlsit = tenementService.getReletList(house_id);
            if (reletlsit.size()>0) {
                map.clear();
                map.put("reletlsit",reletlsit);
                map.put("code", 0);
                map.put("message", "success");
            }else{
                map.put("code", 1);
                map.put("message", "未发现到期房屋");
            }

        return map;
    }


    @GetMapping("/getRelieve")
    @ApiOperation(value = "解除租客", notes = "根据房屋ID")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "tenement_id", dataType = "String", required = true, value = "房屋ID")

    })
    public Map<String, Object> getRelieve(@RequestParam("tenement_id") String tenement_id) {
        Map<String, Object> map = new HashMap<>();
        if (null == tenement_id || "".equals(tenement_id)) {
            map.put("code", 1);
            map.put("message", "房屋ID不能为空");
            return map;
        }
        boolean flag = tenementService.getRelieve(tenement_id);
        if (flag) {
            map.put("code", 0);
            map.put("message", "解除成功");
        } else {
            map.put("code", 1);
            map.put("message", "解除失败");
        }
        return map;
    }

    @GetMapping("/getRlter")
    @ApiOperation(value = "修改房屋信息", notes = "根据房屋ID")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "tenement_id", dataType = "String", required = true, value = "房屋ID"),
            @ApiImplicitParam(paramType = "query", name = "tower_no", dataType = "String", required = true, value = "楼号"),
            @ApiImplicitParam(paramType = "query", name = "asset_name", dataType = "String", required = true, value = "资产配置"),
            @ApiImplicitParam(paramType = "query", name = "house_type", dataType = "String", required = true, value = "户型"),
            @ApiImplicitParam(paramType = "query", name = "tenement_code", dataType = "String", required = true, value = "房屋编号"),
            @ApiImplicitParam(paramType = "query", name = "tenement_area", dataType = "String", required = true, value = "房屋面积"),
            @ApiImplicitParam(paramType = "query", name = "monthly_rent", dataType = "BigDecimal", required = true, value = "月租金"),
            @ApiImplicitParam(paramType = "query", name = "is_bundle", dataType = "int", required = true, value = "是否捆绑"),
            @ApiImplicitParam(paramType = "query", name = "is_fixation", dataType = "int", required = true, value = "是否固定")

    })
    public Map<String, Object> getRlter(@RequestParam("tenement_id") String tenement_id,
                                          @RequestParam("tower_no") String tower_no,
                                          @RequestParam("asset_name") String asset_name,
                                          @RequestParam("house_type") String house_type,
                                          @RequestParam("tenement_code") String tenement_code,
                                          @RequestParam("tenement_area") String tenement_area,
                                          @RequestParam("monthly_rent") String monthly_rent,
                                        @RequestParam("is_bundle") int is_bundle,
                                        @RequestParam("is_fixation") int is_fixation) {
        Map<String, Object> map = new HashMap<>();
        if (null == tenement_id || "".equals(tenement_id)) {
            map.put("code", 1);
            map.put("message", "房屋ID不能为空");
            return map;
        }

        boolean flag = tenementService.getRlter(tenement_id,tower_no,asset_name,tenement_code,tenement_area,house_type,new BigDecimal(monthly_rent),new Date(),is_bundle,is_fixation);
        if (flag) {
            map.put("code", 0);
            map.put("message", "修改成功");
        } else {
            map.put("code", 1);
            map.put("message", "修改失败");
        }
        return map;
    }

    @GetMapping("/getTotal")
    @ApiOperation(value = "押金/总金额", notes = "根据房屋ID")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "tenement_id", dataType = "String", required = true, value = "房屋ID"),
            @ApiImplicitParam(paramType = "query", name = "tenant_id", dataType = "String", required = true, value = "租客ID"),
            @ApiImplicitParam(paramType = "query", name = "bill_type", dataType = "int", required = true, value = "账单类型"),


    })
    public Map<String, Object> getTotal(@RequestParam("tenement_id") String tenement_id,
                                        @RequestParam("tenant_id") String tenant_id,
                                        @RequestParam("bill_type") int bill_type) {

        Map<String, Object> map = new HashMap<>();
        if (null == tenement_id || "".equals(tenement_id)) {
            map.put("code", 1);
            map.put("message", "房屋ID不能为空");
            return map;
        }
        List<Bill> bills=billService.queryBillLists(tenement_id,tenant_id);
        Tenant tr= tenantService.findByTenantById(tenant_id);
        CostItem cr= costItemService. findCostItemById(tenement_id);
        BigDecimal wai=new BigDecimal(0);
        BigDecimal one1=  tr.getRent().add(cr.getFixed_water()).add(cr.getNetwork_fee()).add(cr.getSanitation_fee()).add(cr.getElse_fee());
        int r=wai.compareTo(new BigDecimal(0));
        if(bills.size()==0){
            if(tr!=null){
                map.put("cash_pledge", tr.getCash_pledge());
                map.put("rent",tr.getRent());
            }

            if(bill_type==0){
                List<MeterItem> meterItemList = costItemService.getMonthMeterItemListToatl(tenement_id,tenant_id,0,0);
                if(meterItemList.size()>0){
                    for(int k = 0;k < meterItemList.size(); k ++){
                        MeterItem  mi= meterItemList.get(k);
                        wai=wai.add(mi.getAmount());
                    }
                }
                if(cr!=null){
                    if(tr.getProduce_cycle().equals("1")){//每个月
                        if(wai.compareTo(new BigDecimal(0))==0){
                            map.put("total", one1.multiply(new BigDecimal(1)).add(tr.getCash_pledge()));
                        }else{
                            map.put("total",  one1.multiply(new BigDecimal(1)).add(wai).add(tr.getCash_pledge()));
                        }
                    }else if(tr.getProduce_cycle().equals("3")){
                        if(wai.compareTo(new BigDecimal(0))==0){
                            map.put("total", one1.multiply(new BigDecimal(3)).add(tr.getCash_pledge()));
                        }else{
                            map.put("total",  one1.multiply(new BigDecimal(3)).add(wai).add(tr.getCash_pledge()));
                        }

                    }else if(tr.getProduce_cycle().equals("6")){
                        if(wai.compareTo(new BigDecimal(0))==0){
                            map.put("total", one1.multiply(new BigDecimal(6)).add(tr.getCash_pledge()));
                        }else{
                            map.put("total",  one1.multiply(new BigDecimal(6)).add(wai).add(tr.getCash_pledge()));
                        }

                    }

                }
            }else if(bill_type==1){
                List<MeterItem> meterItemList = costItemService.findLastMonthMeterItemAll(tenement_id,0,0,tenant_id,"水表");
                if(meterItemList.size()>0){
                    for(int j = 0;j < meterItemList.size(); j ++){
                        MeterItem  mi= meterItemList.get(j);
                        wai=wai.add(mi.getAmount());
                    }
                }
                if(cr!=null){
                    if(tr.getProduce_cycle().equals("1")){//每个月
                        if(wai.compareTo(new BigDecimal(0))==0){
                            map.put("total", one1.multiply(new BigDecimal(1)).add(tr.getCash_pledge()));
                        }else{
                            map.put("total",  one1.multiply(new BigDecimal(1)).add(wai).add(tr.getCash_pledge()));
                        }
                    }else if(tr.getProduce_cycle().equals("3")){
                        if(wai.compareTo(new BigDecimal(0))==0){
                            map.put("total", one1.multiply(new BigDecimal(3)).add(tr.getCash_pledge()));
                        }else{
                            map.put("total",  one1.multiply(new BigDecimal(3)).add(wai).add(tr.getCash_pledge()));
                        }
                    }else if(tr.getProduce_cycle().equals("6")){
                        if(wai.compareTo(new BigDecimal(0))==0){
                            map.put("total", one1.multiply(new BigDecimal(6)).add(tr.getCash_pledge()));
                        }else{
                            map.put("total",  one1.multiply(new BigDecimal(6)).add(wai).add(tr.getCash_pledge()));
                        }

                    }

                }
            }else if(bill_type==2){
                List<MeterItem> meterItemList = costItemService.findLastMonthMeterItemAll(tenement_id,0,0,tenant_id,"电表");
                if(meterItemList.size()>0){
                    for(int j = 0;j < meterItemList.size(); j ++){
                        MeterItem  mi= meterItemList.get(j);
                        wai=wai.add(mi.getAmount());
                        map.put("total", wai);
                    }
                }

            }
                map.put("produce_cycle", tr.getProduce_cycle());
                map.put("code", 0);
                map.put("message", "获取成功");


          }else{
            if(tr!=null){
                map.put("cash_pledge", new BigDecimal(0));
                map.put("rent",tr.getRent());
            }

               if(bill_type==0){
                   List<MeterItem> meterItemList = costItemService.getMonthMeterItemListToatl(tenement_id,tenant_id,0,0);
                   if(meterItemList.size()>0){
                       for(int k = 0;k < meterItemList.size(); k ++){
                           MeterItem  mi= meterItemList.get(k);
                           wai=wai.add(mi.getAmount());
                       }
                   }
                   if(cr!=null){
                       if(tr.getProduce_cycle().equals("1")){//每个月
                           if(wai.compareTo(new BigDecimal(0))==0){
                               map.put("total", one1.multiply(new BigDecimal(1)));
                           }else{
                               map.put("total", one1.multiply(new BigDecimal(1)).add(wai));
                           }
                       }else if(tr.getProduce_cycle().equals("3")){
                           if(wai.compareTo(new BigDecimal(0))==0){
                               map.put("total", one1.multiply(new BigDecimal(3)));
                           }else{
                               map.put("total", one1.multiply(new BigDecimal(3)).add(wai));
                           }
                       }else if(tr.getProduce_cycle().equals("6")){
                           if(wai.compareTo(new BigDecimal(0))==0){
                               map.put("total", one1.multiply(new BigDecimal(6)));
                           }else{
                               map.put("total", one1.multiply(new BigDecimal(6)).add(wai));
                           }

                       }
                   }
               }else if(bill_type==1){
                   List<MeterItem> meterItemList = costItemService.findLastMonthMeterItemAll(tenement_id,0,0,tenant_id,"水表");
                   if(meterItemList.size()>0){
                       for(int j = 0;j < meterItemList.size(); j ++){
                           MeterItem  mi= meterItemList.get(j);
                           wai=wai.add(mi.getAmount());
                       }
                   }
                   if(cr!=null){
                       if(tr.getProduce_cycle().equals("1")){//每个月
                           if(wai.compareTo(new BigDecimal(0))==0){
                               map.put("total", one1.multiply(new BigDecimal(1)));
                           }else{
                               map.put("total", one1.multiply(new BigDecimal(1)).add(wai));
                           }
                       }else if(tr.getProduce_cycle().equals("3")){
                           if(wai.compareTo(new BigDecimal(0))==0){
                               map.put("total", one1.multiply(new BigDecimal(3)));
                           }else{
                               map.put("total", one1.multiply(new BigDecimal(3)).add(wai));
                           }

                       }else if(tr.getProduce_cycle().equals("6")){
                           if(wai.compareTo(new BigDecimal(0))==0){
                               map.put("total", one1.multiply(new BigDecimal(6)));
                           }else{
                               map.put("total", one1.multiply(new BigDecimal(6)).add(wai));
                           }
                       }

                   }
               }else if(bill_type==2){
                   List<MeterItem> meterItemList = costItemService.findLastMonthMeterItemAll(tenement_id,0,0,tenant_id,"电表");
                   if(meterItemList.size()>0){
                       for(int j = 0;j < meterItemList.size(); j ++){
                           MeterItem  mi= meterItemList.get(j);
                           wai=wai.add(mi.getAmount());
                           map.put("total", wai);
                       }
                   }

               }
                map.put("produce_cycle", tr.getProduce_cycle());
                map.put("code", 0);
                map.put("message", "获取成功");
            }

        return map;
    }
}
