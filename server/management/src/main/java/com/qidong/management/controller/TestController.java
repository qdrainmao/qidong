package com.qidong.management.controller;

import com.qidong.base.BaseRequest;
import com.qidong.base.BaseResponse;
import com.qidong.base.controller.BaseController;
import com.qidong.base.dto.UserDto;
import com.qidong.management.vo.req.AddCustomerRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/test")
public class TestController extends BaseController {
    @ResponseBody
    @RequestMapping(value = "/test1/{name}", method = RequestMethod.GET, produces = "application/json")
    public String test1(@PathVariable String name){
//        LOGGER.info(str);
        return "名字：" + name;
    }

    @ResponseBody
    @RequestMapping(value = "/test2", method = RequestMethod.POST, produces = "application/json")
    public String test2(String name){
//        LOGGER.info(str);
        return "名字：" + name;
    }

    @ResponseBody
    @RequestMapping(value = "/test3", method = RequestMethod.POST, produces = "application/json")
    public UserDto test3(@RequestBody UserDto user){
        user.setId(1234);
        return user;
    }
}
