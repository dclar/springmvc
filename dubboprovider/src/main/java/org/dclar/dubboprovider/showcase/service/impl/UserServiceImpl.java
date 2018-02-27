package org.dclar.dubboprovider.showcase.service.impl;

import org.dclar.dubboservice.showcase.entity.User;
import org.dclar.dubboservice.showcase.service.UserService;

@com.alibaba.dubbo.config.annotation.Service(version="1.0.0")
public class UserServiceImpl implements UserService {

    @Override
    public void save(User user) {
        System.out.println("UserServiceImpl is saved");
    }
}
