[TOC]

# springboot文件上传



# FORM文件上传

## html

```
<h1>form单文件上传</h1>
<form method="post" action="/thd/uploadfiles/singleUpload" enctype="multipart/form-data">
    <input type="file" name="file" /><br>
    <input type ="text" name="keyword" placeholder="关键字"/><br>
    <input type="submit" value="单文件上传提交" />
</form>

<h1>form多文件上传</h1>
<form method="post" action="/thd/uploadfiles/multiUpload" enctype="multipart/form-data">
    <input type="file" name="file" multiple="multiple"/><br>
    <input type ="text" name="keyword" placeholder="关键字"/><br>
    <input type="submit" value="多文件上传提交" />
</form>
```

## springboot配置文件

applicatoin.yml

```
server:
  servlet:
    # 附件上传相关
    multipart:
      # 限制上传的多个文件的总大小
      max-request-size: 3M
      # 限制单个文件的最大值
      max-file-size: 1M
```

## UploadController

```
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

```



# fetch上传文件

## html

```
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Spring Boot 文件上传</title>


    <script>
function ajaxUpload(){
    var input = document.querySelector('input[id="ajaxFiles"]')

    var data = new FormData()
    console.log(input.files)
    if(!input.files || input.files.length < 1){
        alert("请选择附件");
        return ;
    }

    for(var i = 0 , j = input.files.length ; i < j ; i++){
        data.append('file',input.files[i]);
    }

    data.append('keyword', 'ajax多文件上传')

    console.log(data);

    fetch('/thd/uploadfiles/multiUpload', {
      method: 'POST',
      body: data
    })
}

    </script>
</head>
<body>

<h1>ajax多文件上传</h1>
<form method="post" action="/thd/uploadfiles/multiUpload" enctype="multipart/form-data">
    <input type="file" id="ajaxFiles" name="file" multiple="multiple"/><br>
    <input type ="text" name="keyword" id="ajaxFileKeyworld" placeholder="关键字"/><br>
    <input type="button" value="ajax多文件上传提交" onclick="ajaxUpload()"/>
</form>

</body>
</html>
```

## UploadController

与Form上传的Controller代码相同