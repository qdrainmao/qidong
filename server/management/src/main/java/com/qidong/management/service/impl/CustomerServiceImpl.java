package com.qidong.management.service.impl;

import com.qidong.base.BaseRequest;
import com.qidong.base.service.RedisService;
import com.qidong.management.mapper.CustomerMapper;
import com.qidong.management.mapper.SeckillDao;
import com.qidong.management.mapper.TestMapper;
import com.qidong.management.service.CustomerService;
import com.qidong.management.vo.entity.Customer;
import com.qidong.management.vo.entity.Seckill;
import com.qidong.management.vo.req.AddCustomerRequest;
import com.qidong.management.vo.resp.CustomerResponse;
import com.qidong.utils.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service("customerService")
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerMapper customerMapper;

    public List<CustomerResponse> getCustomerList(BaseRequest baseRequest) {
        List<Customer> customers = customerMapper.selectAll();

//        String mjy = userDubboService.sendData("mjy");

        List<CustomerResponse> customerResponse = new ArrayList<CustomerResponse>();
        List<CustomerResponse> list = new BeanUtil(CustomerResponse.class).copyList(customers);
        return list;
    }

    public void addCustomer(BaseRequest<AddCustomerRequest> baseRequest) {
        Customer customer = new Customer();
        BeanUtil.copyProperties(baseRequest.getParam(), customer);
        customer.setBaseEntity("陆天一");
        int i = customerMapper.insertSelective(customer);
    }
}
