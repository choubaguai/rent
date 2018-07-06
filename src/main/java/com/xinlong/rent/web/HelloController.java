package com.xinlong.rent.web;

import com.xinlong.rent.daomain.Bill;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@ApiIgnore
public class HelloController {

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @GetMapping("/hello")
    public String index(){
        return "hello world";
    }

    @GetMapping("/getList")
    public Map getList(){
        Map map = new HashMap();
        map.put("state",0);
        map.put("message","成功获取数据");
       // map.put("data", new Bill("1","2","3","4","5","6", new Date(), new BigDecimal(123456789)));
        return map;
    }
}
