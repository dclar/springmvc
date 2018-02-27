package org.dclar.controller.c07dubbo;

import com.alibaba.dubbo.config.annotation.Reference;
import org.dclar.dubboservice.showcase.entity.User;
import org.dclar.dubboservice.showcase.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dubbo")
public class DubboTestController {

    @Reference(version="1.0.0")
    static UserService userService;

    @RequestMapping(value = "/save")
    public void save() {
        userService.save(new User());
        System.out.println("exit dubbo save method");
    }
}
