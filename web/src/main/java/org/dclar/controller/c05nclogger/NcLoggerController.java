package org.dclar.controller.c05nclogger;

import org.dclar.nclogger.entity.ProcessEntity;
import org.dclar.nclogger.util.MyUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/log")
public class NcLoggerController {


    @RequestMapping(value = "/write")
    public void log(ProcessEntity processEntity) throws Exception {

        System.out.println(processEntity);
        MyUtil.log(processEntity);
    }

}
