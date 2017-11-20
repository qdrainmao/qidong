package com.qidong.management.service;

import com.qidong.base.BaseRequest;
import com.qidong.management.vo.req.AddCustomerRequest;
import com.qidong.management.vo.resp.CustomerResponse;

import java.util.List;

public interface CustomerService {
    List<CustomerResponse> getCustomerList(BaseRequest baseRequest);

    void addCustomer(BaseRequest<AddCustomerRequest> baseRequest);
}
