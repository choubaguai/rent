package com.xinlong.rent.web;

import com.xinlong.rent.daomain.House;
import com.xinlong.rent.daomain.Owner;
import com.xinlong.rent.daomain.Tenement;
import com.xinlong.rent.service.HouseService;
import com.xinlong.rent.service.OwnerService;
import com.xinlong.rent.service.TenementService;
import com.xinlong.rent.tools.UtilDate;
import com.xinlong.rent.tools.Utils;
import io.swagger.annotations.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Api("房产:API")
@RequestMapping("/house")
public class HouseController {

    private static Logger logger = LogManager.getLogger(HouseController.class.getName());

    @Autowired
    private HouseService houseService;

    @Autowired
    private OwnerService ownerService;

    @Autowired
    private TenementService tenementService;


    /**
     * POST用RequestParam，GET用PathVariable，一个参数用PathVariable，多个参数用RequestParam
     */
    @PostMapping("/addHouse")
    @ApiOperation(value = "添加房产", notes = "根据用户传入的信息创建一个房产")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "phone", dataType = "String", required = true, value = "手机号码"),
            @ApiImplicitParam(paramType = "query", name = "code", dataType = "String", required = true, value = "房产编号"),
            @ApiImplicitParam(paramType = "query", name = "houseName", dataType = "String", required = true, value = "房产名称"),
            @ApiImplicitParam(paramType = "query", name = "houseAddress", dataType = "String", required = true, value = "房产地址"),
            @ApiImplicitParam(paramType = "query", name = "houseType", dataType = "int", required = true, value = "房产类型")
    })
    public Map<String, Object> addHouse(@RequestParam("phone") String phone,
                                        @RequestParam("code") String code,
                                        @RequestParam("houseName") String houseName,
                                        @RequestParam("houseAddress") String houseAddress,
                                        @RequestParam("houseType") int houseType) {
        //先去拿用户ID。
        Map<String, Object> map = new HashMap<>();
        if (null == phone || "".equals(phone)) {
            map.put("code", 1);
            map.put("message", "手机号不能为空");
            return map;
        }
        if (null == code || "".equals(code)) {
            map.put("code", 1);
            map.put("message", "房产编号不能为空");
            return map;
        }
        Owner owner = ownerService.findByPhone(phone);
        House h =houseService.findByCode(code,owner.getOwner_Id());
        if(h == null){
            House house = new House();
            house.setHouse_Id(Utils.getUUID());
            house.setOwner_Id(owner.getOwner_Id());
            house.setHouse_Code(code);
            house.setHouse_Name(houseName);
            house.setHouse_Address(houseAddress);
            house.setRegister_Date(new Date());
            house.setHouse_Type(houseType);
            house.setIs_Delete(1);
            boolean flag = houseService.addHouse(house);
            if (flag) {
                map.put("code", 0);
                map.put("message", "添加成功");
            } else {
                map.put("code", 1);
                map.put("message", "添加失败");
            }
        }else {
            map.put("code", 1);
            map.put("message", "房产编号存在");
        }
        return map;
    }


    @GetMapping("/getHouseById/{phone}")
    @ApiOperation(value = "查询房产", notes = "根据手机号查询房产")
    @ApiParam(name = "phone", value = "手机号码", required = true)
    public Map<String, Object> getHouseById(@PathVariable("phone") String phone) {
        Map<String, Object> map = new HashMap<>();
        if (null == phone || "".equals(phone)) {
            map.put("code", 1);
            map.put("message", "手机号不能为空");
            return map;
        }
        Owner owner = ownerService.findByPhone(phone);
        if (owner != null) {
            List<House> listHouse = houseService.getHouseById(owner.getOwner_Id());
            if (listHouse.size()>0) {
                    map.put("code", 0);
                    map.put("data", owner);
                    map.put("list",listHouse);
                    map.put("message", "success");
            }else{
                map.put("code", 1);
                map.put("message", "未添加房产");
            }
        } else {
            map.put("code", 1);
            map.put("message", "用户不存在");
        }
        return map;
    }

    @GetMapping("/deleteHouse")
    @ApiOperation(value = "房产删除", notes = "根据房产ID删除")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "house_id", dataType = "String", required = true, value = "房产ID" )
    })
    public Map<String, Object> deleteHouse(@RequestParam("house_id") String house_id) {


        //首先查询房屋
        Map<String, Object> map = new HashMap<>();
        Tenement tm =tenementService.findByTenementCodeId(house_id);
        if(tm == null){
        //查询房产
            House hs=houseService.findByHouseId(house_id);
            if(hs!=null){
                boolean flag =houseService.deleteHouse(house_id);
                if (flag) {
                    map.put("code", 0);
                    map.put("message", "删除成功");
                } else {
                    map.put("code", 1);
                    map.put("message", "删除失败");
                }
            }else{
                map.put("code", 1);
                map.put("message", "房产不存在");
            }
        }else{
            map.put("code", 1);
            map.put("message", "存在房屋信息");
        }

        return map;
    }


    @GetMapping("/getLeaveHouseList")
    @ApiOperation(value = "主页模块", notes = "根据客户ID type：0闲置1账单/水电2收支")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "owner_Id", dataType = "String", required = true, value = "客户ID"),
            @ApiImplicitParam(paramType = "query", name = "type", dataType = "int", required = true, value = "模块类型")

    })
    public Map<String, Object> getLeaveHouseList(@RequestParam("owner_Id") String owner_Id,@RequestParam("type") int type) {
        Map<String, Object> map = new HashMap<>();
        if (null == owner_Id || "".equals(owner_Id)) {
            map.put("code", 1);
            map.put("message", "客户ID不能为空");
            return map;
        }
        List<House> houseList=null;
        if(type==0){
            houseList= houseService.getLeaveHouseList(owner_Id);
        }else if(type==1){
            houseList= houseService.getBillFormHouseList(owner_Id);
        }else if(type==2){
            houseList= houseService.getFerialHouseList(owner_Id, UtilDate.getFirstDayOfMonth(),UtilDate.getEndMonth());
        }

        if(houseList.size()>0){
            map.put("houseList",houseList);
            map.put("code", 0);
            map.put("message", "获取成功");
        }else{
            map.put("code", 1);
            map.put("message", "没有数据");
        }
        return map;
    }

}
