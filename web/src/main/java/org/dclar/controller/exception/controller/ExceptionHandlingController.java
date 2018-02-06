package org.dclar.controller.exception.controller;

import org.dclar.controller.exception.ex.InvalidCookieException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionHandlingController {


    @ExceptionHandler({InvalidCookieException.class})
    public String cookieError(Model model) {

        ModelAndView mav = new ModelAndView();

        //mav.setViewName("error");
        //mav.addObject("errmsg", "cookieError");
        model.addAttribute("errmsg", "error");
        return "home";


    }

}
