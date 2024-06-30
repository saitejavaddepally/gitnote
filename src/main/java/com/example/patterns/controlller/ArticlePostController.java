package com.example.patterns.controlller;


import com.example.patterns.service.IArticlePostService;
import com.example.patterns.service.imp.LoginService;
import com.example.patterns.vo.TbWfwUserArticleVo;
import com.example.patterns.vo.auth.CustomUserDetails;
import com.example.patterns.vo.auth.TbUsmUserAccessVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/secure/workflow/article/")
public class ArticlePostController {

    private static final Logger logger = LoggerFactory.getLogger(LoginService.class);

    @Autowired
    IArticlePostService iArticlePostService;


    @PostMapping("/post-new")
    public void postArticle(@RequestBody TbWfwUserArticleVo tbWfwUserArticleVo, @AuthenticationPrincipal CustomUserDetails principal) throws Exception{

        // don't really need to check if the user is maker or checker -- just make the request

        logger.info("Current Authorities of the user are >> {}", principal.getAuthorities());
        logger.info("Article Saving process initiated ...");

        try{
            iArticlePostService.saveArticleForUser(tbWfwUserArticleVo, principal);
        } catch (Exception e){
            throw new Exception("Something went wrong, We'll fix the issue from our side ");
        }

        logger.info("Article Saving process completed ...");
        // store the details in the DB with proper username
    }

}
