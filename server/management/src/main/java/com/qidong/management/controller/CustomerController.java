package com.qidong.management.controller;

import com.qidong.base.BaseRequest;
import com.qidong.base.BaseResponse;
import com.qidong.base.controller.BaseController;
import com.qidong.management.service.CustomerService;
import com.qidong.management.vo.req.AddCustomerRequest;
import com.qidong.management.vo.resp.CustomerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController/* extends BaseController*/ {
    @Autowired
    private CustomerService customerService;
    @ResponseBody
    @RequestMapping(value = "/getCustomerList", method = RequestMethod.POST, produces = "application/json")
    public BaseResponse getCustomerList(@RequestBody BaseRequest baseRequest) {
//        validate(baseRequest);
//        LOGGER.info("getCustomerList");
        List<CustomerResponse> customerList = null;/*customerService.getCustomerList(baseRequest);*/
        return BaseResponse.success(customerList);
    }

    @ResponseBody
    @RequestMapping(value = "/addCustomer", method = RequestMethod.POST, produces = "application/json")
    public BaseResponse addCustomer(@RequestBody BaseRequest<AddCustomerRequest> baseRequest){
//        validate(baseRequest);
//        LOGGER.info("addCustomer");
//        customerService.addCustomer(baseRequest);
        return BaseResponse.success();
    }

}
