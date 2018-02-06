package org.dclar.controller.c02process;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.dclar.controller.c01auth.vo.UserLoginVo;
import org.dclar.controller.c03download.vo.DownloadVo;
import org.dclar.dbservice.act.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {


    Logger log = LoggerFactory.getLogger(HomeController.class);


    @Autowired
    LogService logService;

    @RequestMapping("/home")
    public String home(Model model) {
        //log.debug(userLoginVo.getInputEmail());
        //log.debug(userLoginVo.getInputPassword());


        int record = logService.insert(null);

        Subject subject = SecurityUtils.getSubject();

        UserLoginVo vo = new UserLoginVo();
        vo.setInputEmail(subject.getPrincipal().toString());
        model.addAttribute("userLoginVo", vo);

        DownloadVo downloadVo = new DownloadVo();
        downloadVo.setUrl("blank");
        model.addAttribute("record", record);
        model.addAttribute("downloadVo", downloadVo);

        model.asMap().forEach( (k,v)-> {
            log.debug(k);
            log.debug(v.toString());
        });



        return "home";
    }

    @RequestMapping("/default")
    public String defaultPage() {

        return "default";
    }
}
