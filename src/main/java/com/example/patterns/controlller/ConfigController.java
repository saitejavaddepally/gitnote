package com.example.patterns.controlller;

import com.example.patterns.service.imp.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/frontpage")
@Slf4j
public class ConfigController {
    private static final Logger logger = LoggerFactory.getLogger(LoginService.class);


    @GetMapping("/hello")
    public String get(){
        logger.info("Hello world of application .... ... ... ");
        return "This is the front page !!";
    }
}
