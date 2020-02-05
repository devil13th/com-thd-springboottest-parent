package com.thd.springboottest.resttemplate.controller;


import com.thd.springboottest.resttemplate.entity.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.Response;
import java.io.*;
import java.util.Date;
import java.util.List;

/**
 * @author devil13th
 *
 * 测试访问地址：http://127.0.0.1:8899/thd/requestparameter/index.html
 * 测试访问地址：http://127.0.0.1:8899/thd/requestparameter/index.html
 * 测试访问地址：http://127.0.0.1:8899/thd/requestparameter/index.html
 *
 **/
@Controller
@RequestMapping(value="/receive")
public class ReceiveController {
    Logger log = LoggerFactory.getLogger(this.getClass());

    @ResponseBody
    @RequestMapping(value="/testGet01",method=RequestMethod.GET)
    // url : http://127.0.0.1:8899/thd/receive/testGet01
    // 返回字符串
    public ResponseEntity<String> testGet01(){
        this.log.info("testGet01");
        return ResponseEntity.ok(" Hello RestTemplate ");
    }

    @ResponseBody
    @RequestMapping(value="/testGet02",method=RequestMethod.GET)
    // url : http://127.0.0.1:8899/thd/receive/testGet02
    // 返回对象
    public ResponseEntity<Person> testGet02(){
        this.log.info("testGet01");
        Person p = new Person();
        p.setName("devil13th");
        p.setAge(5);
        p.setBirthday(new Date());
        return ResponseEntity.ok(p);
    }

    @ResponseBody
    @RequestMapping(value="/testGet03",method=RequestMethod.GET)
    //直接获取url?后面对应的参数
    //url : http://127.0.0.1:8899/thd/receive/testGet03?usr=devil13th&pwd=123456
    public Person testGet03(@RequestParam String usr, @RequestParam String pwd){
        this.log.info("testGet03");
        System.out.println(usr + "," + pwd);
        Person p = new Person();
        p.setName(usr);
        return p;
    }


    @ResponseBody
    @RequestMapping(value="/testGet04/{str}",method=RequestMethod.GET)
    // 通过在url中使用{}占位符来获取参数
    //url : http://127.0.0.1:8899/thd/receive/testGet04/hello
    public ResponseEntity<Person> testGet04(@PathVariable String str ){
        this.log.info("testGet04");
        System.out.println(str);
        Person p = new Person();
        p.setBirthday(new Date());
        p.setAge(1);
        p.setName(str);
        return ResponseEntity.ok(p);
    }


    @ResponseBody
    @RequestMapping(value="/testGet05",method=RequestMethod.GET)
    // url中?后的参数直接转对象接收
    //url : http://127.0.0.1:8899/thd/receive/testGet03?age=5&name=devil13th
    public Person testGet05(Person person ){
        this.log.info("testGet05");
        System.out.println(person);
        return person;
    }


    @ResponseBody
    @RequestMapping(value="/testPost01",method=RequestMethod.POST)
    //通过Post方式提交来body内容(一般是json),可以通过@RequestBody直接将body中的json转成对象
    //注意这种方式要设置头部信息的contentType属性 headers.set('Content-Type', 'application/json');
    //url : http://127.0.0.1:8899/thd/receive/testPost01
    public ResponseEntity<Person> testPost01(@RequestBody Person person){
        this.log.info("testPost01");
        Person p = person;
        person.setName(person.getName() + " ... ");
        System.out.println(person);
        return ResponseEntity.ok(p);
    }

    @ResponseBody
    @RequestMapping(value="/testPost02",method=RequestMethod.POST)
    // 客户端以formData的方式发送
    // form 表单提交接收参数需要使用@ModelAttribute("person"),
    // 表单中的name不要加person前缀，例如name="person.id"应写成name="id"
    public Person testPost02(@ModelAttribute("person") Person person){
        this.log.info("testPost02");
        System.out.println(person);
        return person;
    }

    @ResponseBody
    @RequestMapping(value="/testPost03",method=RequestMethod.POST)
    // 表单的body中的数据是以 name=devil13th&age=5 的形式发送,而不是json格式的数据
    // 方法参数不要加@ModelAttribute
    public Person testPost03(Person person){
        this.log.info("testPost03");
        System.out.println(person);
        return person;
    }

    @ResponseBody
    @RequestMapping(value="/testUpload01",method=RequestMethod.POST)
    //单个文件上传 并获取数据
    public String testUpload01(@RequestParam("file") MultipartFile file,@ModelAttribute("person") Person person) throws IOException {
        System.out.println(person);
        System.out.println("文件大小:" + file.getSize());
        InputStream is = file.getInputStream();
        byte[] buffer = new byte[1024 * 2 ];

        OutputStream os = new FileOutputStream(new File("D://deleteme//testfile"));
        while(is.read(buffer) != -1){
            os.write(buffer);
        }
        os.close();
        is.close();

        return "SUCCESS";
    }



    @ResponseBody
    @RequestMapping(value="/testUpload02",method=RequestMethod.POST)
    //多个附件上传
    public String handleFormsUpload(HttpServletRequest request) {
        try{
            List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
            MultipartFile file = null;
            for(int i = 0; i < files.size(); ++i){
                file = files.get(i);
                if (!file.isEmpty()) {
                    byte[] bytes = file.getBytes();
                    File picture = new File("D://deleteme//"+ String.valueOf(i) + ".png");//这里指明上传文件保存的地址
                    FileOutputStream fos = new FileOutputStream(picture);
                    BufferedOutputStream bos = new BufferedOutputStream(fos);
                    bos.write(bytes);
                    bos.close();
                    fos.close();
                }
            }

            return "success";

        }catch (IOException e){
            System.out.println(e);
        }
        return "failed";
    }


    @ResponseBody
    @RequestMapping(value="/testHeader01",method=RequestMethod.GET)
    //获取请求头内容
    public String testHeader01(@RequestHeader(name="myHeader") String myHeader) throws IOException {
        String str = " Request Header - myHeadeer:" + myHeader;
        System.out.println(str);
        return str;
    }



}
