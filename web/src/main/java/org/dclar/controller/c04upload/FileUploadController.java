package org.dclar.controller.c04upload;

import org.dclar.controller.c04upload.vo.UploadVo;
import org.dclar.e2h.processor.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController()
@RequestMapping("/upload")
public class FileUploadController {

    Logger log = LoggerFactory.getLogger(FileUploadController.class);

    @Autowired
    Processor e2hProcessor;


    @RequestMapping(value = "/excel", method = RequestMethod.POST)
    public UploadVo submit(@RequestParam("file") MultipartFile file, ModelMap modelMap) throws Exception {

        String result = e2hProcessor.startProcessByPath(file.getInputStream(), "").getResult();

        log.debug("输出html的内容为 : {}", result);
        UploadVo uploadVo = new UploadVo();
        uploadVo.setTableContent(result);
        return uploadVo;
    }


}
