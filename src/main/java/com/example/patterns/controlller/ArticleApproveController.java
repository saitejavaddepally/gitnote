package com.example.patterns.controlller;


import com.example.patterns.service.IArticleApproveService;
import com.example.patterns.service.imp.ArticlePostService;
import com.example.patterns.vo.ApproveRejectReqVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/secure/")
public class ArticleApproveController {

    private static final Logger logger = LoggerFactory.getLogger(ArticleApproveController.class);

    @Autowired
    IArticleApproveService iArticleApproveService;


    @PostMapping("/approveReject")
    public void approveRejectWfwRequest(@RequestBody ApproveRejectReqVo approveRejectReqVo) throws Exception {

        logger.info("Approval process started .. {}" , approveRejectReqVo);

        iArticleApproveService.approveArticleForUser(approveRejectReqVo);

        logger.info("Approval process ended .. {}" , approveRejectReqVo);
    }
}
