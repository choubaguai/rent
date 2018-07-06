package com.xinlong.rent.web;

import com.alibaba.fastjson.JSONObject;
import com.xinlong.rent.daomain.Owner;
import com.xinlong.rent.service.OwnerService;
import com.xinlong.rent.tools.AESDecodeUtils;
import com.xinlong.rent.tools.Utils;
import io.swagger.annotations.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;


@RestController
@Api("客户:API")
@RequestMapping("/owner")
public class OwnerController {

    private static Logger logger = LogManager.getLogger(OwnerController.class.getName());

    @Autowired
    private OwnerService ownerService;

    /**
     * POST用RequestParam，GET用PathVariable
     *
     * @param phone
     * @param password
     * @return
     */
    @PostMapping("/addOwner")
    @ApiOperation(value = "注册一个用户", notes = "根据用户传入的手机号/名称/密码创建一个用户")
    @ApiImplicitParams({

            @ApiImplicitParam(paramType = "query", name = "name", dataType = "String", required = true, value = "客户姓名"),
            @ApiImplicitParam(paramType = "query", name = "phone", dataType = "String", required = true, value = "电话号码"),
            @ApiImplicitParam(paramType = "query", name = "password", dataType = "String", required = true, value = "客户密码")

    })

    public Map<String, Object> addOwner(@RequestParam("name") String name, @RequestParam("phone") String phone, @RequestParam("password") String password
    ) {
        Map<String, Object> map = new HashMap<>();

        if (null == name || "".equals(name)) {
            map.put("code", 1);
            map.put("message", "名称不能为空");
            return map;
        }
        if (null == phone || "".equals(phone)) {
            map.put("code", 1);
            map.put("message", "手机号不能为空");
            return map;
        }

        Owner owner = ownerService.findByPhone(phone);
        if (owner == null) {
            owner = new Owner();
            owner.setOwner_Id(Utils.getUUID());
            owner.setOwner_Name(name);
            owner.setOwner_Phone(phone);
            owner.setNickname(phone);
            owner.setOwner_Password(Utils.convertMD5(password));
            owner.setIs_Delete(1);
            owner.setOwner_dt(new Date());
            boolean flag = ownerService.addOwner(owner);
            if (flag) {
                map.put("code", 0);
                map.put("owner_Id", owner.getOwner_Id());
                map.put("message", "注册成功");
            } else {
                map.put("code", 1);
                map.put("message", "注册失败");
            }
        } else {
            map.put("code", 1);
            map.put("message", "手机号存在");
        }

        return map;
    }


    @PostMapping("/login")
    @ApiOperation(value = "登录", notes = "根据用户传入的手机号码和密码登录")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "phone", dataType = "String", required = true, value = "账号名"),
            @ApiImplicitParam(paramType = "query", name = "password", dataType = "String", required = true, value = "密码")

    })
    public Map<String, Object> login(@RequestParam("phone") String phone, @RequestParam("password") String password) {
        Map<String, Object> map = new HashMap<>();
        if (null == phone || "".equals(phone)) {
            map.put("code", 1);
            map.put("message", "手机号不能为空");
            return map;
        }
        if (null == password || "".equals(password)) {
            map.put("code", 1);
            map.put("message", "密码不能为空");
            return map;
        }

        Owner owner = ownerService.login(phone, Utils.convertMD5(password));
        if (owner == null) {
            map.put("code", 1);
            map.put("message", "账号或密码错误");
        } else {
            map.put("code", 0);
            map.put("message", "登陆成功");
            map.put("data", owner);
        }

        return map;
    }


    @GetMapping("/findByPhone/{phone}")
    @ApiOperation(value = "查询客户", notes = "根据手机号查询")
    @ApiParam(name = "phone", value = "手机号码", required = true)
    public Map<String, Object> findByPhone(@PathVariable("phone") String phone) {
        Map<String, Object> map = new HashMap<>();
        if (null == phone || "".equals(phone)) {
            map.put("code", 1);
            map.put("message", "手机号不能为空");
            return map;
        }
        Owner owner = ownerService.findByPhone(phone);
        if (owner != null) {
            map.put("code", 0);
            map.put("data", owner);
            map.put("message", "查询成功");
            logger.info(owner);
        } else {
            map.put("code", 1);
            map.put("message", "查询失败");
        }
        return map;
    }


    @PostMapping("/decodeUserInfo")
    @ApiOperation(value = "解密微信小程序手机号", notes = "解密微信小程序手机号")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "encrypted", dataType = "String", required = true, value = "目标密文"),
            @ApiImplicitParam(paramType = "query", name = "session_key", dataType = "String", required = true, value = " 会话ID"),
            @ApiImplicitParam(paramType = "query", name = "iv", dataType = "String", required = true, value = "加密算法的初始向量")
    })
    public Map<String, Object> decodeUserInfo(@RequestParam(value = "encrypted") String encrypted,
                                              @RequestParam(value = "session_key") String session_key,
                                              @RequestParam(value = "iv") String iv) {
        Map<String, Object> map = new HashMap<>();
        String appid = "wx5bd5bc333511d3e4";
        String secret = "07df8bbfa30ae213663238b26da05e05";
        StringBuffer url = new StringBuffer();
        url.append("https://api.weixin.qq.com/sns/jscode2session?appid=");
        url.append(appid);
        url.append("&secret=");
        url.append(secret);
        url.append("&js_code=");
        url.append(session_key);
        url.append("&grant_type=authorization_code");

        String strJsion = AESDecodeUtils.loadJSON(url.toString());
        System.out.println(strJsion);
        JSONObject jsonObject = JSONObject.parseObject(strJsion);
        String key = jsonObject.getString("session_key");
        System.out.println(key);
        try {
            String json = AESDecodeUtils.wxDecrypt(encrypted, key, iv);
            System.out.println(json);
            map.put("code",0);
            map.put("data",json);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code",1);
            map.put("data",e.getMessage());
        }
        return map;
    }
}
