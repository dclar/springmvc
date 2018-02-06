package org.dclar.controller.c01auth;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.dclar.controller.base.BaseController;
import org.dclar.controller.c01auth.vo.UserLoginVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signin")
public class LoginController extends BaseController {

    Logger log = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping("")
    public String Login() {

        return "login";
    }

    @RequestMapping("/login")
    public String doLogin(UserLoginVo userLoginVo, Model model) {

        HttpStatus STATUS = HttpStatus.OK;

        UsernamePasswordToken usernamePasswordToken =
                new UsernamePasswordToken(userLoginVo.getInputEmail(), userLoginVo.getInputPassword());

        try {

            Subject subject = SecurityUtils.getSubject();
            subject.login(usernamePasswordToken);


        } catch (UnknownAccountException uae) {

            log.warn(uae.toString());
            STATUS = HttpStatus.NOT_ACCEPTABLE;
            model.addAttribute("errmsg", "UnknownAccountException");
            return "login";

        } catch (IncorrectCredentialsException ice) {

            log.warn(ice.toString());
            STATUS = HttpStatus.NOT_ACCEPTABLE;
            model.addAttribute("errmsg", "IncorrectCredentialsException");
            return "login";

        } catch (LockedAccountException lae) {
            log.warn(lae.toString());
            STATUS = HttpStatus.FORBIDDEN;
            model.addAttribute("errmsg", "LockedAccountException");
            return "login";

        } catch (ExcessiveAttemptsException eae) {
            log.warn(eae.toString());
            STATUS = HttpStatus.UNAUTHORIZED;
            model.addAttribute("errmsg", "ExcessiveAttemptsException");
            return "login";
        } catch (AuthenticationException ae) {

            log.warn(ae.toString());
            STATUS = HttpStatus.UNAUTHORIZED;
            model.addAttribute("errmsg", "AuthenticationException");
            return "login";

        }


        log.debug(userLoginVo.getInputEmail());
        log.debug(userLoginVo.getInputPassword());


        // return "forward:../home";
        return "home";
    }
}
