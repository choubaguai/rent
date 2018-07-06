package com.xinlong.rent.web;


import com.xinlong.rent.daomain.CostItem;
import com.xinlong.rent.daomain.MeterItem;
import com.xinlong.rent.daomain.Tenement;
import com.xinlong.rent.service.CostItemService;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Api("费用项:API")
@RequestMapping("/costItem")
public class CostItemController {



    private static Logger logger = LogManager.getLogger(CostItemController.class.getName());

    @Autowired
    private CostItemService costItemService;

    @Autowired
    private TenementService tenementService;
    /**
     * POST用RequestParam，GET用PathVariable，一个参数用PathVariable，多个参数用RequestParam
     */
    @PostMapping("/addCostItem")
    @ApiOperation(value = "添加费用项", notes = "根据房屋ID创建费用项")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "tenement_id", dataType = "String", required = true, value = "房屋ID"),
            @ApiImplicitParam(paramType = "query", name = "fixed_water", dataType = "String", required = true, value = "固定水费"),
            @ApiImplicitParam(paramType = "query", name = "network_fee", dataType = "String", required = true, value = "网费"),
            @ApiImplicitParam(paramType = "query", name = "sanitation_fee", dataType = "String", required = true, value = "卫生费"),
            @ApiImplicitParam(paramType = "query", name = "else_fee", dataType = "String", required = true, value = "其他费用"),
            @ApiImplicitParam(paramType = "query", name = "remarks", dataType = "String", required = true, value = "备注")
    })

    public Map<String, Object> addCostItem(@RequestParam("tenement_id") String tenement_id,
                                        @RequestParam("fixed_water") String fixed_water,
                                        @RequestParam("network_fee") String network_fee,
                                        @RequestParam("sanitation_fee") String sanitation_fee,
                                        @RequestParam("else_fee") String else_fee,
                                        @RequestParam("remarks") String remarks ) {
        //先去拿用户ID。
        Map<String, Object> map = new HashMap<>();
        if (null == tenement_id || "".equals(tenement_id)) {
            map.put("code", 1);
            map.put("message", "房屋编号不能为空");
            return map;
        }

        Tenement trt= tenementService.getTenementByCodeId(tenement_id);
            if(trt!=null){

                CostItem cim= costItemService.findCostItemById(tenement_id);
                if(cim==null){
                    CostItem costItem =new CostItem();
                    costItem.setCost_item_id(Utils.getUUID());
                    costItem.setTenement_id(tenement_id);
                    costItem.setFixed_water(new BigDecimal(fixed_water));
                    costItem.setNetwork_fee(new BigDecimal(network_fee));
                    costItem.setElse_fee(new BigDecimal(else_fee));
                    costItem.setSanitation_fee(new BigDecimal(sanitation_fee));
                    costItem.setCost_item_date(new Date());
                    costItem.setRemarks(remarks);
                    boolean flag =  costItemService.addCostItem(costItem);
                    if (flag) {
                        map.put("code", 0);
                        map.put("message", "添加成功");
                    } else {
                        map.put("code", 1);
                        map.put("message", "添加失败");
                    }
                }else{
                    map.put("code", 1);
                    map.put("message", "费用项存在");
                }

            }else{
                map.put("code", 1);
                map.put("message", "房屋不存在");
            }

        return map;
    }


    @GetMapping("/getCostItemById")
    @ApiOperation(value = "查询房屋费用项", notes = "根据房屋ID和月份")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "tenement_id", dataType = "String", required = true, value = "房屋ID")

    })
    public Map<String, Object> getCostItemById(@RequestParam("tenement_id") String tenement_id) {

        Map<String, Object> map = new HashMap<>();
        if (null == tenement_id || "".equals(tenement_id)) {
            map.put("code", 1);
            map.put("message", "房屋ID不能为空");
            return map;
        }
        CostItem cim= costItemService.findCostItemById(tenement_id);
        if(cim!=null){
            map.clear();
            map.put("cim",cim);
            map.put("code", 0);
            map.put("message", "success");
        }else{
            map.put("code", 1);
            map.put("message", "没有添加费用");
        }
        return map;
    }


    @PostMapping("/updateCostItem")
    @ApiOperation(value = "修改费用项", notes = "根据房屋ID修改费用项")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "tenement_id", dataType = "String", required = true, value = "房屋ID"),

    })

    public Map<String, Object> updateCostItem(@RequestParam("tenement_id") String tenement_id,
                                              @RequestParam("fixed_water") String fixed_water,
                                              @RequestParam("network_fee") String network_fee,
                                              @RequestParam("sanitation_fee") String sanitation_fee,
                                              @RequestParam("else_fee") String else_fee,
                                              @RequestParam("remarks") String remarks) {
        //先去拿用户ID。
        Map<String, Object> map = new HashMap<>();
        if (null == tenement_id || "".equals(tenement_id)) {
            map.put("code", 1);
            map.put("message", "房屋编号不能为空");
            return map;
        }

        CostItem cim= costItemService.findCostItemById(tenement_id);
        if(cim!=null){

            boolean flag =  costItemService.updateCostItem(tenement_id,fixed_water,network_fee,sanitation_fee,else_fee,new Date(),remarks);
            if (flag) {
                map.put("code", 0);
                map.put("message", "修改成功");
            } else {
                map.put("code", 1);
                map.put("message", "修改失败");
            }

        }else{
            map.put("code", 1);
            map.put("message", "房屋不存在");
        }

        return map;
    }

    /**
     * POST用RequestParam，GET用PathVariable，一个参数用PathVariable，多个参数用RequestParam
     */
    @PostMapping("/addMeterItem")
    @ApiOperation(value = "添加抄表项", notes = "根据房屋ID添加抄表项")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "tenement_id", dataType = "String", required = true, value = "房屋ID"),
            @ApiImplicitParam(paramType = "query", name = "tenant_id", dataType = "String", required = true, value = "租客ID"),
            @ApiImplicitParam(paramType = "query", name = "meter_item_name", dataType = "String", required = true, value = "抄表项名称"),
            @ApiImplicitParam(paramType = "query", name = "start_code", dataType = "String", required = true, value = "起码"),
            @ApiImplicitParam(paramType = "query", name = "end_code", dataType = "String", required = true, value = "止码"),
            @ApiImplicitParam(paramType = "query", name = "unit_price", dataType = "String", required = true, value = "单价"),

            @ApiImplicitParam(paramType = "query", name = "remarks", dataType = "String", required = true, value = "备注")

    })

    public Map<String, Object> addMeterItem(@RequestParam("tenement_id") String tenement_id,
                                           @RequestParam("tenant_id") String tenant_id,
                                           @RequestParam("meter_item_name") String meter_item_name ,
                                           @RequestParam("start_code") String start_code ,
                                           @RequestParam("end_code") String end_code ,
                                           @RequestParam("unit_price") String unit_price ,


                                           @RequestParam("remarks") String remarks ) {

        //先去拿用户ID。
        Map<String, Object> map = new HashMap<>();
        if (null == tenement_id || "".equals(tenement_id)) {
            map.put("code", 1);
            map.put("message", "房屋编号不能为空");
            return map;
        }

      //  List<MeterItem> mit= costItemService.findMeterItemMonthById(tenement_id,UtilDate.getMonths(meter_item_month));
      //  if(mit.size()==0){
            MeterItem meterItem=new MeterItem();
            meterItem.setMeter_item_id(Utils.getUUID());
            meterItem.setTenement_id(tenement_id);
            meterItem.setTenant_id(tenant_id);
            meterItem.setMeter_item_name(meter_item_name);
            meterItem.setStart_code(Integer.valueOf(start_code));
            meterItem.setEnd_code(Integer.valueOf(end_code));
            if(Integer.valueOf(start_code)==0){
                meterItem.setConsumption(Integer.valueOf(end_code));
            }else{
                if(Integer.valueOf(end_code)>Integer.valueOf(start_code)){
                    meterItem.setConsumption(Integer.valueOf(end_code)-Integer.valueOf(start_code));
                }else if(end_code.equals(start_code)){
                    meterItem.setConsumption(0);
                }
            }
            meterItem.setUnit_price(new BigDecimal(unit_price));
            meterItem.setAmount(new BigDecimal(meterItem.getConsumption()).multiply(new BigDecimal(unit_price)));
            meterItem.setMeter_item_dt(new Date());
            meterItem.setMeter_item_month(UtilDate.getDrontMonth());
            meterItem.setIs_bundle(0);
            meterItem.setIs_pay(0);
            meterItem.setRemarks(remarks);
            meterItem.setBill_id("1");
            boolean flag =  costItemService.addMeterItem(meterItem);
            if (flag) {
                map.put("code", 0);
                map.put("message", "添加成功");
            } else {
                map.put("code", 1);
                map.put("message", "添加失败");
            }

      //  }else{
       //     map.put("code", 1);
      //      map.put("message", "此月已抄表");
       // }

        return map;
    }
    @GetMapping("/getMeterItemById")
    @ApiOperation(value = "查询抄表项", notes = "根据房屋ID")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "tenement_id", dataType = "String", required = true, value = "房屋ID"),
            @ApiImplicitParam(paramType = "query", name = "tenant_id", dataType = "String", required = true, value = "租客ID"),

    })
    public Map<String, Object> getMeterItemById(@RequestParam("tenement_id") String tenement_id,@RequestParam("tenant_id") String tenant_id) {

        Map<String, Object> map = new HashMap<>();
        if (null == tenement_id || "".equals(tenement_id)) {
            map.put("code", 1);
            map.put("message", "房屋ID不能为空");
            return map;
        }
        List<MeterItem>  meterItemList= costItemService.findMeterItemById(tenement_id,tenant_id);
        if(meterItemList.size()>0){
            map.clear();
            map.put("meterItemList",meterItemList);
            map.put("code", 0);
            map.put("message", "success");
        }else{
            map.put("code", 1);
            map.put("message", "没有抄表项");
        }
        return map;
    }


      @GetMapping("/getLastMeterItem")
    @ApiOperation(value = "查询最后一次抄表项", notes = "根据房屋ID/抄表项名称")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "tenement_id", dataType = "String", required = true, value = "房屋ID"),
            @ApiImplicitParam(paramType = "query", name = "meter_item_name", dataType = "String", required = true, value = "抄表项名称"),
            @ApiImplicitParam(paramType = "query", name = "tenant_id", dataType = "String", required = true, value = "租客ID")

    })
    public Map<String, Object> getLastMeterItem(@RequestParam("tenement_id") String tenement_id,
                                                @RequestParam("meter_item_name") String meter_item_name,
                                                @RequestParam("tenant_id") String tenant_id) {

          Map<String, Object> map = new HashMap<>();
          if (null == tenement_id || "".equals(tenement_id)) {
              map.put("code", 1);
              map.put("message", "房屋ID不能为空");
              return map;
          }
          MeterItem met = costItemService.findLastMeterItem(tenement_id, meter_item_name,tenant_id);
          if (met!= null) {
              map.clear();
              map.put("meterItem", met);
              map.put("code", 0);
              map.put("message", "success");
          } else {
              map.put("code", 1);
              map.put("message", "没有抄表项");
          }
          return map;

      }


    @GetMapping("/getLastMonthMeterItem")
    @ApiOperation(value = "查询上月最后一次查表项", notes = "根据房屋ID/抄表项名称")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "tenement_id", dataType = "String", required = true, value = "房屋ID"),
            @ApiImplicitParam(paramType = "query", name = "tenant_id", dataType = "String", required = true, value = "租客ID"),
            @ApiImplicitParam(paramType = "query", name = "type", dataType = "int", required = true, value = "类型：0全部1电费2水费")


    })
    public Map<String, Object> getLastMonthMeterItem(@RequestParam("tenement_id") String tenement_id,@RequestParam("tenant_id") String tenant_id,@RequestParam("type") int type
           ) {


        Map<String, Object> map = new HashMap<>();
        if (null == tenement_id || "".equals(tenement_id)) {
            map.put("code", 1);
            map.put("message", "房屋ID不能为空");
            return map;
        }
        List<MeterItem> meterItemList =null;
        BigDecimal wai=new BigDecimal(0);
        if(type==0){
            meterItemList = costItemService.findLastMonthMeterItem(tenement_id,0,0,tenant_id );
        }else if(type==1){
            meterItemList =costItemService.findLastMonthMeterItemAll(tenement_id,0,0,tenant_id,"电表" );

            if(meterItemList.size()>0){
                for(int i = 0;i < meterItemList.size(); i ++){
                    MeterItem  mi= meterItemList.get(i);
                    wai=wai.add(mi.getAmount());
                }
            }

        }else if(type==2){
            meterItemList =costItemService.findLastMonthMeterItemAll(tenement_id,0,0,tenant_id,"水表" );
        }
        if (meterItemList.size()>0) {
            map.clear();
            map.put("meterItemList", meterItemList);
            map.put("total", wai);
            map.put("code", 0);
            map.put("message", "success");
        } else {
            map.put("code", 1);
            map.put("message", "未找到抄表项");

        }
        return map;

    }


    @GetMapping("/getMeterItemList")
    @ApiOperation(value = "查询抄表项列表", notes = "根据房屋ID/租客ID")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "tenement_id", dataType = "String", required = true, value = "房屋ID"),
            @ApiImplicitParam(paramType = "query", name = "tenant_id", dataType = "String", required = true, value = "租客ID")

    })
    public Map<String, Object> getMeterItemList(@RequestParam("tenement_id") String tenement_id,
                                                @RequestParam("tenant_id") String tenant_id) {

        Map<String, Object> map = new HashMap<>();
        if (null == tenement_id || "".equals(tenement_id)) {
            map.put("code", 1);
            map.put("message", "房屋ID不能为空");
            return map;
        }
        List<MeterItem> met = costItemService.getMeterItemList(tenement_id, tenant_id,0,0,UtilDate.getFirstDayOfMonth(),UtilDate.getEndMonth());
        if (met.size()>0) {
            map.put("meterItemlist", met);
            map.put("code", 0);
            map.put("message", "success");
        } else {
            map.put("code", 1);
            map.put("message", "没有抄表项");
        }
        return map;

    }





    @GetMapping("/getMeterItemId")
    @ApiOperation(value = "查询抄表项列表", notes = "抄表项ID")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "meter_item_id", dataType = "String", required = true, value = "抄表项ID" )
    })

        public Map<String, Object> getMeterItemId(@RequestParam("meter_item_id") String meter_item_id) {


        Map<String, Object> map = new HashMap<>();
        if (null == meter_item_id || "".equals(meter_item_id)) {
            map.put("code", 1);
            map.put("message", "抄表项ID不能为空");
            return map;
        }
        MeterItem met = costItemService.getMeterItemById(meter_item_id);
        if (met!=null) {
            map.put("met", met);
            map.put("code", 0);
            map.put("message", "获取成功");
        } else {
            map.put("code", 1);
            map.put("message", "获取失败");
        }
        return map;

    }

}
