package com.thd.springboottest.rabbitmq.integration;

import com.thd.springboottest.rabbitmq.integration.confirm.ConfirmProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/rabbitmq/integration")
public class IntegrationController {
    @Autowired
    private ConfirmProducer producer;
    @ResponseBody
    @RequestMapping("/productConfirm")
    // url : http://127.0.0.1:8899/thd/rabbitmq/integration/productConfirm
    public ResponseEntity productConfirm(){
        producer.send2();
        return ResponseEntity.ok("SUCCESS");
    }
}
