package com.qidong.management.service.impl;

import com.qidong.management.service.TestService;
import org.springframework.stereotype.Service;

@Service("testService")
public class TestServiceImpl implements TestService {
    public void test() {
        System.out.println("hello");
    }
}
