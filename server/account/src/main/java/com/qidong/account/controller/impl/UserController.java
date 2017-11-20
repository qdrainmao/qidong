package com.qidong.account.controller.impl;

import com.qidong.account.vo.req.LoginRequest;
import com.qidong.account.vo.req.RegisterRequest;
import com.qidong.account.service.user.UserService;
import com.qidong.account.vo.resp.LoginResponse;
import com.qidong.base.BaseRequest;
import com.qidong.base.BaseResponse;
import com.qidong.base.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController{
    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json")
    public BaseResponse login(HttpServletRequest request, @RequestBody BaseRequest<LoginRequest> loginRequest){
        LOGGER.info("login");
        LoginResponse loginResponse = userService.login(loginRequest);
        if (loginResponse != null){
            return BaseResponse.success(loginResponse);
        }else {
            return BaseResponse.fail("用户不存在");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = "application/json")
    public BaseResponse register(HttpServletRequest request, @RequestBody BaseRequest<RegisterRequest> registerRequest){
        LOGGER.info("register");
        userService.register(registerRequest);
        return BaseResponse.success();
    }
}
