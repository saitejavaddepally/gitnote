package com.example.patterns.controlller;


import com.example.patterns.service.ILoginService;
import com.example.patterns.vo.auth.TbUsmUserAccessVo;
import com.example.patterns.vo.auth.CustomUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("authenticate")
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    ILoginService loginService;


    @PostMapping("/user")
    public CustomUserDetails authenticateUser(@RequestBody TbUsmUserAccessVo tbUsmUserAccessVo){

        logger.info("Application sign up process started ..");

        return loginService.authenticateUser(tbUsmUserAccessVo);
    }
}


