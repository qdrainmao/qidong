package com.qidong.account.mapper;

import com.qidong.account.vo.entity.User;
import com.qidong.base.BaseMapper;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {
    List<User> selectBySelective(User user);
}