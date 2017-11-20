package com.qidong.management.mapper;


import com.qidong.base.BaseMapper;
import com.qidong.management.vo.entity.Customer;

import java.util.List;

public interface CustomerMapper extends BaseMapper<Customer> {
    List<Customer> selectAll();
}