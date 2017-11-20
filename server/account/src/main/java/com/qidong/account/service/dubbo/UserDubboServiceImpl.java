package com.qidong.account.service.dubbo;

import com.account.service.dubbo.UserDubboService;
import org.springframework.stereotype.Service;

@Service("userDubboService")
public class UserDubboServiceImpl implements UserDubboService {
    public String sendData(String name) {
        return "maojinyu";
    }
}
