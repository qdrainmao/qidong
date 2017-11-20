package com.qidong.account.service.user.impl;

import com.qidong.account.mapper.UserMapper;
import com.qidong.account.util.TokenUtil;
import com.qidong.account.vo.entity.User;
import com.qidong.account.vo.req.LoginRequest;
import com.qidong.account.vo.req.RegisterRequest;
import com.qidong.account.service.user.UserService;
import com.qidong.account.vo.resp.LoginResponse;
import com.qidong.base.BaseRequest;
import com.qidong.base.dto.UserDto;
import com.qidong.base.enums.TypeEnum;
import com.qidong.base.service.RedisService;
import com.qidong.base.service.impl.RedisClusterServiceImpl;
import com.qidong.utils.util.BeanUtil;
import com.qidong.utils.util.Md5Util;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisService redisService;
    @Autowired
    private RedisClusterServiceImpl redisClusterService;

    public void register(BaseRequest<RegisterRequest> registerRequest) {
        //插入用户到数据库
        RegisterRequest requestParam = registerRequest.getParam();
        User user = new User();
        BeanUtil.copyProperties(requestParam, user);
        user.setPassword(Md5Util.md5(requestParam.getPassword()));
        user.setBaseEntity("陆天一");
        userMapper.insertSelective(user);
    }

    public LoginResponse login(BaseRequest<LoginRequest> loginRequest) {
        User loginUser = getLoginUser(loginRequest.getParam());
        if (loginUser != null){
            //创建token
            String token = TokenUtil.getToken();

            //token:userDto放入redis
            UserDto userDto = new UserDto();
            BeanUtil.copyProperties(loginUser, userDto);

            redisClusterService.setRedis(TypeEnum.TYPE_USER.getName(), token, userDto);
//            UserDto result = redisClusterService.getRedis(TypeEnum.TYPE_USER.getName(), token, UserDto.class);
            LoginResponse loginResponse = new LoginResponse();
            BeanUtil.copyProperties(loginUser, loginResponse);
            loginResponse.setToken(token);
            return loginResponse;
        }else {
            return null;
        }
    }

    private User getLoginUser(LoginRequest param) {
        User user = new User();
        BeanUtil.copyProperties(param, user);
        user.setPassword(Md5Util.md5(param.getPassword()));
        List<User> users = userMapper.selectBySelective(user);
        if (users.size() == 1){
            return users.get(0);
        }else {
            return null;
        }
    }

}
