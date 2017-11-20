package com.qidong.base.controller;

import com.qidong.base.BaseRequest;
import com.qidong.base.dto.UserDto;
import com.qidong.base.enums.TypeEnum;
import com.qidong.base.service.RedisClusterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class BaseController {
    protected Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    @Autowired
    RedisClusterService redisClusterService;

    protected <T>void validate(BaseRequest<T> baseRequest){
        String token = baseRequest.getToken();
        UserDto userDto = redisClusterService.getRedis(TypeEnum.TYPE_USER.getName(), token, UserDto.class);
        if (userDto == null){
            LOGGER.error("用户不存在");
            throw new RuntimeException();
        }
    }
}
