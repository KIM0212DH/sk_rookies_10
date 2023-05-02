package com.godcoder.log4shellserver;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    Logger log = LogManager.getLogger(HomeController.class);
    @RequestMapping("/")
    public String index(String name) {

        System.out.println("hello");
        log.debug("index! name : {}", name);
        log.info("${java:version}");
        log.info(name);
        return "index";
    }
}
