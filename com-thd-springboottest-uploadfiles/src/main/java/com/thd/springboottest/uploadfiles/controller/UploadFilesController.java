package com.thd.springboottest.uploadfiles.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * com.thd.springboottest.uploadfiles.controller.UploadFilesController
 *
 * @author: wanglei62
 * @DATE: 2019/11/15 10:19
 **/
@Controller
@RequestMapping("/uploadfiles")
public class UploadFilesController {

    //测试静态主页地址：
    // url : http://127.0.0.1:8899/thd/index.html

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @RequestMapping("/test")
    @ResponseBody
    // url : http://127.0.0.1:8899/thd/uploadfiles/test
    public ResponseEntity<String> test(){
        logger.info("test()");
        return ResponseEntity.ok("SUCCESS");
    }

    @RequestMapping("/index")
    @ResponseBody
    // url : http://127.0.0.1:8899/thd/uploadfiles/index
    public String index(){
        logger.info("index()");
        return "index";
    }

    /**
     * 处理单个文件上传
     * @param file
     * @param keyword
     * @return
     */
    @PostMapping("/singleUpload")
    @ResponseBody
    // url : http://127.0.0.1:8899/thd/uploadfiles/singleUpload
    public String singleUpload(@RequestParam("file") MultipartFile file,@RequestParam String keyword) {
        if (file.isEmpty()) {
            return "上传失败，请选择文件";
        }
        logger.info("关键字：" + keyword);
        String fileName = file.getOriginalFilename();
        String filePath = "D://deleteme/upload//";
        File dest = new File(filePath + fileName);
        try {
            file.transferTo(dest);
            logger.info("上传成功");
            return "上传成功";
        } catch (IOException e) {
            logger.error(e.toString(), e);
            return "上传失败!" + e.getMessage();
        }
    }

    /**
     * 处理多文件上传
     * @param request
     * @param keyword
     * @return
     */
    @PostMapping("/multiUpload")
    @ResponseBody
    // url : http://127.0.0.1:8899/thd/uploadfiles/multiUpload
    public String multiUpload(HttpServletRequest request,@RequestParam String keyword) {
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        logger.info("关键字：" + keyword);
        String filePath = "D://deleteme/upload//";
        for (int i = 0; i < files.size(); i++) {
            MultipartFile file = files.get(i);
            if (file.isEmpty()) {
                return "上传第" + (i++) + "个文件失败";
            }
            String fileName = file.getOriginalFilename();

            File dest = new File(filePath + fileName);
            try {
                file.transferTo(dest);
                logger.info("上传[" + fileName + "]文件成功");
            } catch (IOException e) {
                logger.error(e.toString(), e);
                return "上传[" + fileName + "]文件失败" + e.getMessage();
            }
        }

        return "上传成功";

    }
}
