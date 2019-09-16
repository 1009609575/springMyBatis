package com.bdqn.controller;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.aspectj.util.FileUtil;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

/**
 *@ClassName:UploadController
 *@Description:TODO 上传控制器
 *@Author:lzq
 *@Date: 2019/9/10 9:11
 **/
@Controller
@RequestMapping("/upload")
public class UploadController {
    private static Logger log = Logger.getLogger(UploadController.class);

    /**
     * description: TODO 显示上传文件页面
     * create time: 2019/9/7 14:37
     * [multi]
     *
     * @return java.lang.String
     */
    @GetMapping(value = "/index")
    public String showUploadPage(@RequestParam(value = "multi", required = false) Boolean multi) {
        if (multi != null && multi) {
            return "course_admin/multifile";
        }
        return "course_admin/file";
    }

    /**
     * @Description:处理单文件上传
     * @param: [file]
     * @return: java.lang.String
     * @Date: 2019/07/12 9:29
     */
    @PostMapping(value ="/doUpload")
    public String doUploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            log.debug(file.getOriginalFilename());
            FileUtils.copyInputStreamToFile(file.getInputStream(), new File("C:\\temp\\test", System.currentTimeMillis() + file.getOriginalFilename()));
        }
        return "course_admin/success";
    }

    @GetMapping(value = "/doUpload2")
    public String doUploadFile2(MultipartHttpServletRequest multiRequest) throws IOException {
        Iterator<String> filesNames = multiRequest.getFileNames();
        while (filesNames.hasNext()) {
            String fileName = filesNames.next();
            MultipartFile file = multiRequest.getFile(fileName);
            if (!file.isEmpty()) {
                log.debug(file.getOriginalFilename());
                FileUtils.copyInputStreamToFile(file.getInputStream(), new File("C:\\temp\\test\\", System.currentTimeMillis() + file.getOriginalFilename()));
            }

        }

        return "course_admin/success";
    }
}


