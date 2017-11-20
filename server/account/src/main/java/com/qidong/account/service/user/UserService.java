package com.qidong.account.service.user;

import com.qidong.account.vo.req.LoginRequest;
import com.qidong.account.vo.req.RegisterRequest;
import com.qidong.account.vo.resp.LoginResponse;
import com.qidong.account.vo.resp.RegisterResponse;
import com.qidong.base.BaseRequest;

public interface UserService {
    void register(BaseRequest<RegisterRequest> registerRequest);

    LoginResponse login(BaseRequest<LoginRequest> loginRequest);
}
