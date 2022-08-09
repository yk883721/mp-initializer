package com.yk.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yk.mapper.UserMapper;
import com.yk.model.User;
import com.yk.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

}
