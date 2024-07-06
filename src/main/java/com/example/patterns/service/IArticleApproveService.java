package com.example.patterns.service;


import com.example.patterns.vo.ApproveRejectReqVo;
import org.springframework.stereotype.Component;

@Component
public interface IArticleApproveService {
    void approveArticleForUser(ApproveRejectReqVo approveRejectReqVo) throws Exception;
}
