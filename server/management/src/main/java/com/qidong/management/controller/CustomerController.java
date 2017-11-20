package com.qidong.management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/customer")
public class CustomerController/* extends BaseController*/ {
    /*@Autowired
    private CustomerService customerService;
    @ResponseBody
    @RequestMapping(value = "/getCustomerList", method = RequestMethod.POST, produces = "application/json")
    public BaseResponse getCustomerList(@RequestBody BaseRequest baseRequest) {
//        validate(baseRequest);
//        LOGGER.info("getCustomerList");
        List<CustomerResponse> customerList = null;*//*customerService.getCustomerList(baseRequest);*//*
        return BaseResponse.success(customerList);
    }

    @ResponseBody
    @RequestMapping(value = "/addCustomer", method = RequestMethod.POST, produces = "application/json")
    public BaseResponse addCustomer(@RequestBody BaseRequest<AddCustomerRequest> baseRequest){
//        validate(baseRequest);
//        LOGGER.info("addCustomer");
//        customerService.addCustomer(baseRequest);
        return BaseResponse.success();
    }*/

}
